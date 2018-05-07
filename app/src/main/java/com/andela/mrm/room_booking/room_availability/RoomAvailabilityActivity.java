package com.andela.mrm.room_booking.room_availability;

import android.Manifest;
import android.accounts.AccountManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.andela.mrm.R;
import com.andela.mrm.room_booking.meeting_room.MeetingRoomFragment;
import com.andela.mrm.util.GooglePlayService;
import com.andela.mrm.util.NetworkConnectivityChecker;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential;
import com.google.api.client.googleapis.extensions.android.gms.auth.GooglePlayServicesAvailabilityIOException;
import com.google.api.client.googleapis.extensions.android.gms.auth.UserRecoverableAuthIOException;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.DateTime;
import com.google.api.client.util.ExponentialBackOff;
import com.google.api.services.calendar.CalendarScopes;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.Events;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;

/**
 * The type Room availability activity.
 */
public class RoomAvailabilityActivity extends AppCompatActivity implements
        CountDownTimerFragment.IOnTextChangeListener,
        MeetingRoomDetailFragment.IOnStartCountDown, EasyPermissions.PermissionCallbacks {

    private FragmentManager fragmentManager;
    /**
     * The Constraint layout.
     */
    ConstraintLayout constraintLayout;

    /**
     * The M credential.
     */
    GoogleAccountCredential mCredential;


    /**
     * The Request account picker.
     */
    static final int REQUEST_ACCOUNT_PICKER = 1000;
    /**
     * The Request authorization.
     */
    static final int REQUEST_AUTHORIZATION = 1001;
    /**
     * The Request google play services.
     */
    static final int REQUEST_GOOGLE_PLAY_SERVICES = 1002;
    /**
     * The Request permission get accounts.
     */
    static final int REQUEST_PERMISSION_GET_ACCOUNTS = 1003;

    /**
     * The Play service.
     */
    GooglePlayService playService;

    private static final String BUTTON_TEXT = "Call Google Calendar API";
    private static final String PREF_ACCOUNT_NAME = "accountName";
    private static final String[] SCOPES = {CalendarScopes.CALENDAR_READONLY};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room_availability);

        playService = new GooglePlayService();
        fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .add(R.id.frame_room_availability_details, new MeetingRoomDetailFragment())
                .add(R.id.frame_room_availability_time_line, new TimeLineFragment())
                .add(R.id.frame_room_availability_countdown_timer, new CountDownTimerFragment())
                .commit();

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
        meetingRoomDetailFragment.updateMinute(minutes);
    }

    @Override
    public void onCountDownComplete() {
        MeetingRoomDetailFragment meetingRoomDetailFragment = (MeetingRoomDetailFragment)
                getSupportFragmentManager().findFragmentById(R.id.frame_room_availability_details);
        if (meetingRoomDetailFragment != null) {
            meetingRoomDetailFragment.displayCheckInScreen();
        } else {
            MeetingRoomFragment meetingRoomFragment = new MeetingRoomFragment();
            FragmentManager fragmentTransaction = getSupportFragmentManager();
            fragmentTransaction.beginTransaction()
                    .add(R.id.frame_room_availability_details, meetingRoomFragment).commit();
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
            constraintLayout = findViewById(R.id.layout_room_availability);
            Snackbar.make(constraintLayout, "No Network Found", Snackbar.LENGTH_INDEFINITE)
                    .setAction("RETRY", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            getResultsFromApi();
                        }
                    })
                    .show();
        } else {
            new MakeRequestTask(mCredential).execute();
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
            String accountName = getPreferences(Context.MODE_PRIVATE)
                    .getString(PREF_ACCOUNT_NAME, null);
            if (accountName != null) {
                mCredential.setSelectedAccountName(accountName);
                getResultsFromApi();
            } else {
                // Start a dialog from which the user can choose an account
                startActivityForResult(
                        mCredential.newChooseAccountIntent(),
                        REQUEST_ACCOUNT_PICKER);
            }
        } else {
            // Request the GET_ACCOUNTS permission via a user dialog
            EasyPermissions.requestPermissions(
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
     * @param requestCode code indicating which activity result is incoming.
     * @param resultCode  code indicating the result of the incoming
     *                    activity result.
     * @param data        Intent (containing result data) returned by incoming
     *                    activity result.
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
                if (resultCode == RESULT_OK && data != null
                        && data.getExtras() != null) {
                    String accountName =
                            data.getStringExtra(AccountManager.KEY_ACCOUNT_NAME);
                    if (accountName != null) {
                        SharedPreferences settings =
                                getPreferences(Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = settings.edit();
                        editor.putString(PREF_ACCOUNT_NAME, accountName);
                        editor.apply();
                        mCredential.setSelectedAccountName(accountName);
                        getResultsFromApi();
                    }
                }
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
     * Callback for when a permission is granted using the EasyPermissions
     * library.
     *
     * @param requestCode The request code associated with the requested
     *                    permission
     * @param list        The requested permission list. Never null.
     */
    @Override
    public void onPermissionsGranted(int requestCode, List<String> list) {
        // Do nothing.
    }

    /**
     * Callback for when a permission is denied using the EasyPermissions
     * library.
     *
     * @param requestCode The request code associated with the requested
     *                    permission
     * @param list        The requested permission list. Never null.
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
     * An asynchronous task that handles the Google Calendar API call.
     * Placing the API calls in their own task ensures the UI stays responsive.
     */
    private class MakeRequestTask extends AsyncTask<Void, Void, List<String>> {
        private final com.google.api.services.calendar.Calendar mService;
        private Exception mLastError = null;

        /**
         * Instantiates a new Make request task.
         *
         * @param credential the credential
         */
        MakeRequestTask(GoogleAccountCredential credential) {
            HttpTransport transport = AndroidHttp.newCompatibleTransport();
            JsonFactory jsonFactory = JacksonFactory.getDefaultInstance();
            mService = new com.google.api.services.calendar.Calendar.Builder(
                    transport, jsonFactory, credential)
                    .setApplicationName("Google Calendar API Android Quickstart")
                    .build();
        }

        /**
         * Background task to call Google Calendar API.
         *
         * @param params no parameters needed for this task.
         */
        @Override
        protected List<String> doInBackground(Void... params) {
            try {
                return getDataFromApi();
            } catch (Exception e) {
                mLastError = e;
                cancel(true);
                return null;
            }
        }

        /**
         * Fetch a list of the next 10 events from the primary calendar.
         *
         * @return List of Strings describing returned events.
         * @throws IOException ioException
         */
        private List<String> getDataFromApi() throws IOException {
            // List the next 10 events from the primary calendar.
            DateTime now = new DateTime(System.currentTimeMillis());
            List<String> eventStrings = new ArrayList<String>();
            Events events = mService.events().list("primary")
                    .setMaxResults(10)
                    .setTimeMin(now)
                    .setOrderBy("startTime")
                    .setSingleEvents(true)
                    .execute();
            List<Event> items = events.getItems();

            for (Event event : items) {
                DateTime start = event.getStart().getDateTime();
                if (start == null) {
                    // All-day events don't have start times, so just use
                    // the start date.
                    start = event.getStart().getDate();
                }
                eventStrings.add(
                        String.format("%s (%s)", event.getSummary(), start));
            }
            return eventStrings;
        }


        @Override
        protected void onPreExecute() {
            Log.v("Loading", "Loading now...");
        }

        @Override
        protected void onPostExecute(List<String> output) {
            if (output == null || output.isEmpty()) {
                Log.v("No results", "No results returned.");
            } else {
                output.add(0, "Data retrieved using the Google Calendar API:");
                for (int i = 0; i < output.size(); i++) {
                    Log.v("Output", "" + output.get(i));
                }
            }
        }

        @Override
        protected void onCancelled() {
            if (mLastError != null) {
                if (mLastError instanceof GooglePlayServicesAvailabilityIOException) {
                    playService.showGooglePlayServicesAvailabilityErrorDialog(
                            ((GooglePlayServicesAvailabilityIOException) mLastError)
                                    .getConnectionStatusCode(), RoomAvailabilityActivity.this);
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
}
