package com.andela.mrm.room_availability;

import android.Manifest;
import android.accounts.AccountManager;
import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.LinearLayout;

import com.andela.mrm.R;
import com.andela.mrm.find_rooms.FindRoomActivity;
import com.andela.mrm.room_events.EventScheduleActivity;
import com.andela.mrm.room_information.RoomInformationActivity;
import com.andela.mrm.util.GooglePlayService;
import com.andela.mrm.util.NetworkConnectivityChecker;
import com.andela.mrm.widget.TimeLineScheduleView;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential;
import com.google.api.client.googleapis.extensions.android.gms.auth.GooglePlayServicesAvailabilityIOException;
import com.google.api.client.googleapis.extensions.android.gms.auth.UserRecoverableAuthIOException;
import com.google.api.client.util.ExponentialBackOff;
import com.google.api.services.calendar.CalendarScopes;
import com.google.api.services.calendar.model.Event;

import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;

/**
 * The type Room availability activity.
 */
public class RoomAvailabilityActivity extends AppCompatActivity implements
        CountDownTimerFragment.IOnTextChangeListener, IGoogleCalenderCallListener,
        MeetingRoomDetailFragment.IOnStartCountDown, EasyPermissions.PermissionCallbacks {

    public static final String EVENTS_IN_STRING = "eventsInString";
    public static final String PREF_ACCOUNT_NAME = "accountName";
    public static final int REQUEST_PERMISSION_GET_ACCOUNTS = 1003;
    static final int REQUEST_ACCOUNT_PICKER = 1000;
    static final int REQUEST_AUTHORIZATION = 1001;
    static final int REQUEST_GOOGLE_PLAY_SERVICES = 1002;
    private static final String[] SCOPES = {CalendarScopes.CALENDAR_READONLY};

    public List<Event> items;
    @BindView(R.id.layout_schedule)
    LinearLayout roomSchedule;
    @BindView(R.id.layout_room_info)
    LinearLayout roomInformation;
    @BindView(R.id.layout_find_room)
    LinearLayout findRoomLayout;
    @BindView(R.id.view_time_line_strip)
    TimeLineScheduleView timeLineStrip;
    @BindView(R.id.layout_room_availability_parent)
    ConstraintLayout roomAvailabilityParentLayout;

    GoogleAccountCredential mCredential;
    GooglePlayService playService;
    private FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room_availability);
        ButterKnife.bind(this);
        setRoomScheduleOnClickListener(null);
        setRoomInformationListener();
        setFindRoomLayoutListener();
        playService = new GooglePlayService(GoogleApiAvailability.getInstance());
        setUpFragments(); // Sets up inflatable fragments(countdown timer and details fragments)
        // Initialize credentials and service object.
        mCredential = GoogleAccountCredential.usingOAuth2(
                getApplicationContext(), Arrays.asList(SCOPES))
                .setBackOff(new ExponentialBackOff());
        getResultsFromApi();
    }

    @Override
    public void onTimeChange(int minutes) {
        MeetingRoomDetailFragment meetingRoomDetailFragment = (MeetingRoomDetailFragment)
                getSupportFragmentManager().findFragmentById(R.id.frame_room_availability_details);
        if (meetingRoomDetailFragment != null) {
            meetingRoomDetailFragment.updateMinute(minutes);
        }
    }

    @Override
    public void onCountDownComplete() {
        MeetingRoomDetailFragment meetingRoomDetailFragment = (MeetingRoomDetailFragment)
                getSupportFragmentManager().findFragmentById(R.id.frame_room_availability_details);
        if (meetingRoomDetailFragment != null) {
            meetingRoomDetailFragment.displayCheckInScreen();
        }
    }

    @Override
    public void startCountDown(long timeInMilliSeconds) {
        CountDownTimerFragment countDownTimerFragment = (CountDownTimerFragment)
                getSupportFragmentManager()
                        .findFragmentById(R.id.frame_room_availability_countdown_timer);
        if (countDownTimerFragment != null) {
            countDownTimerFragment.stopTimer();
            countDownTimerFragment.startTimer(timeInMilliSeconds);
            countDownTimerFragment.setTimeRemainingText("Time Remaining");
        }
    }

    /**
     * On meeting ended.
     */
    @Override
    public void onMeetingEnded() {
        CountDownTimerFragment countDownTimerFragment = (CountDownTimerFragment)
                getSupportFragmentManager()
                        .findFragmentById(R.id.frame_room_availability_countdown_timer);
        if (countDownTimerFragment != null) {
            countDownTimerFragment.setTimeRemainingText("Time Till Next Meeting");
            countDownTimerFragment.stopTimer();
            countDownTimerFragment.startTimer(8000);
        }
    }

    /**
     * Attempt to call the API, after verifying that all the preconditions are
     * satisfied. The preconditions are: Google Play Services installed, an
     * account was selected and the device currently has online access. If any
     * of the preconditions are not satisfied, the app will prompt the user as
     * appropriate.
     */
    public void getResultsFromApi() {
        if (!playService.isGooglePlayServicesAvailable(getApplicationContext())) {
            playService.acquireGooglePlayServices(getApplicationContext(), this);
        } else if (mCredential.getSelectedAccountName() == null) {
            chooseAccount();
        } else if (!isDeviceOnline()) {
            Snackbar
                    .make(roomAvailabilityParentLayout, "No Network Found",
                            Snackbar.LENGTH_INDEFINITE)
                    .setAction("RETRY", v -> getResultsFromApi())
                    .show();
        } else {
            new MakeGoogleCalendarCallPresenter(mCredential, this).execute();
        }
    }

    /**
     * Attempts to set the account used with the API credentials. If an account
     * name was previously saved it will use that one; otherwise an account
     * picker dialog will be shown to the user. Note that the setting the
     * account to use with the credentials object requires the app to have the
     * GET_ACCOUNTS permission, which is requested here if it is not already
     * present. The AfterPermissionGranted annotation indicates that this
     * function will be rerun automatically whenever the GET_ACCOUNTS permission
     * is granted.
     */
    @AfterPermissionGranted(REQUEST_PERMISSION_GET_ACCOUNTS)
    private void chooseAccount() {
        if (EasyPermissions.hasPermissions(
                this, Manifest.permission.GET_ACCOUNTS)) {
            String accountName = PreferenceManager
                    .getDefaultSharedPreferences(this)
                    .getString(PREF_ACCOUNT_NAME, null);
            if (accountName != null) {
                mCredential.setSelectedAccountName(accountName);
                getResultsFromApi();
            } else {
                startActivityForResult(//Start a dialog from which the user can choose an account
                        mCredential.newChooseAccountIntent(),
                        REQUEST_ACCOUNT_PICKER);
            }
        } else {
            EasyPermissions.requestPermissions(//Request the GET_ACCOUNTS permission via user dialog
                    this,
                    "This app needs to access your Google account (via Contacts).",
                    REQUEST_PERMISSION_GET_ACCOUNTS,
                    Manifest.permission.GET_ACCOUNTS);
        }
    }

    /**
     * Called when an activity launched here (specifically, AccountPicker
     * and authorization) exits, giving you the requestCode you started it with,
     * the resultCode it returned, and any additional data from it.
     *
     * @param requestCode - code indicating which activity result is incoming.
     * @param resultCode  - code indicating the result of the incoming activity result.
     * @param data        -   Intent (containing result data) returned by incoming activity result.
     */
    @Override
    protected void onActivityResult(
            int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case REQUEST_GOOGLE_PLAY_SERVICES:
                if (resultCode != RESULT_OK) {
                    Log.v("GooglePlay services", "This app requires Google "
                            + "Play Services."
                            + " Please install Google Play Services on "
                            + "your device and relaunch this app.");
                } else {
                    getResultsFromApi();
                }
                break;
            case REQUEST_ACCOUNT_PICKER:
                resultCodeEqualsResultOkDataNotNull(resultCode, data);
                break;
            case REQUEST_AUTHORIZATION:
                if (resultCode == RESULT_OK) {
                    getResultsFromApi();
                }
                break;
            default:
                //intentionally left blank
        }
    }

    /**
     * Extracted Method that deals with ResultCode equaling resultOk and Data not null.
     *
     * @param resultCode integer value of the result code
     * @param data       data recieved via intent
     */
    public void resultCodeEqualsResultOkDataNotNull(int resultCode, Intent data) {
        if (resultCode == RESULT_OK && data != null
                && data.getExtras() != null) {
            String accountName =
                    data.getStringExtra(AccountManager.KEY_ACCOUNT_NAME);
            if (accountName != null) {
                PreferenceManager
                        .getDefaultSharedPreferences(this)
                        .edit()
                        .putString(PREF_ACCOUNT_NAME, accountName)
                        .apply();
                mCredential.setSelectedAccountName(accountName);
                getResultsFromApi();
            }
        }
    }

    /**
     * Respond to requests for permissions at runtime for API 23 and above.
     *
     * @param requestCode  The request code passed in
     *                     requestPermissions(android.app.Activity, String, int, String[])
     * @param permissions  The requested permissions. Never null.
     * @param grantResults The grant results for the corresponding permissions
     *                     which is either PERMISSION_GRANTED or PERMISSION_DENIED. Never null.
     */
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(
                requestCode, permissions, grantResults, this);
    }

    /**
     * Callback for when a permission is granted using the EasyPermissions library.
     *
     * @param requestCode The request code associated with the requested permission
     * @param list        The requested permission list. Never null.
     */
    @Override
    public void onPermissionsGranted(int requestCode, List<String> list) {
        // Do nothing.
    }

    /**
     * Callback for when a permission is denied using the EasyPermissions library.
     *
     * @param requestCode - The request code associated with the requested permission
     * @param list        -    The requested permission list. Never null.
     */
    @Override
    public void onPermissionsDenied(int requestCode, List<String> list) {
        // Do nothing.
    }

    /**
     * Checks whether the device currently has a network connection.
     *
     * @return true if the device has a network connection, false otherwise.
     */
    private boolean isDeviceOnline() {
        return NetworkConnectivityChecker.isDeviceOnline(getApplicationContext());
    }

    /**
     * Sets up inflatable fragments(countdown timer and details fragments).
     */
    private void setUpFragments() {
        fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .add(R.id.frame_room_availability_details, new MeetingRoomDetailFragment())
                .add(R.id.frame_room_availability_countdown_timer, new CountDownTimerFragment())
                .commit();
    }

    /**
     * Activates room information button.
     */
    private void setRoomInformationListener() {
        roomInformation.setOnClickListener(v -> {
            // TODO: replace roomId with Id of current room
            Intent intent = RoomInformationActivity.newIntent(
                    RoomAvailabilityActivity.this, 3);
            startActivity(intent);
        });
    }

    /**
     * Activates onClickListener on both room schedule button and timeline strip.
     *
     * @param eventsInString - list of events in strings
     */
    void activateRoomScheduleOnClickListener(@Nullable final String eventsInString) {
        if (eventsInString == null) {
            Snackbar.make(roomAvailabilityParentLayout,
                    "Initializing, please wait...", Snackbar.LENGTH_SHORT)
                    .show();
        } else {
            Intent intent = new Intent(RoomAvailabilityActivity.this,
                    EventScheduleActivity.class);
            intent.putExtra(EVENTS_IN_STRING, eventsInString);
            Log.e("Data in sender", eventsInString);
            startActivity(intent);
        }
    }

    /**
     * Sets the onClick Listener.
     *
     * @param eventsInString String.
     */
    public void setRoomScheduleOnClickListener(@Nullable final String eventsInString) {
        roomSchedule.setOnClickListener(v -> activateRoomScheduleOnClickListener(eventsInString));
        timeLineStrip.setOnClickListener(v -> activateRoomScheduleOnClickListener(eventsInString));
    }

    /**
     * sets findRoomLayout Listener.
     */
    public void setFindRoomLayoutListener() {
        findRoomLayout.setOnClickListener(v -> {
            Intent intent = new Intent(RoomAvailabilityActivity.this,
                    FindRoomActivity.class);
            startActivity(intent);
        });
    }

    @Override
    public void onSuccess(String itemsInString) {
        setRoomScheduleOnClickListener(itemsInString);
    }

    @Override
    public void onCancelled(Exception mLastError) {
        if (mLastError != null) {
            if (mLastError instanceof GooglePlayServicesAvailabilityIOException) {
                playService.showGooglePlayServicesAvailabilityErrorDialog(
                        ((GooglePlayServicesAvailabilityIOException) mLastError)
                                .getConnectionStatusCode(),
                        RoomAvailabilityActivity.this);
            } else if (mLastError instanceof UserRecoverableAuthIOException) {
                startActivityForResult(
                        ((UserRecoverableAuthIOException) mLastError).getIntent(),
                        RoomAvailabilityActivity.REQUEST_AUTHORIZATION);
            } else {
                Log.v("This error occurred: ", "" + mLastError.getMessage());
            }
        } else {
            Log.v("Cancelled", "Request cancelled.");
        }
    }
}
