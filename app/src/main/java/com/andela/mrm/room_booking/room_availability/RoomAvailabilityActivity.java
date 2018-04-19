package com.andela.mrm.room_booking.room_availability;

import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.andela.mrm.R;
import com.andela.mrm.room_booking.meeting_room.MeetingRoomFragment;

/**
 * The type Room availability activity.
 */
public class RoomAvailabilityActivity extends AppCompatActivity implements
        CountDownTimerFragment.IOnTextChangeListener, MeetingRoomDetailFragment.IOnStartCountDown {

    private FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room_availability);

        fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .add(R.id.frame_room_availability_details, new MeetingRoomDetailFragment())
                .add(R.id.frame_room_availability_time_line, new TimeLineFragment())
                .add(R.id.frame_room_availability_countdown_timer, new CountDownTimerFragment())
                .commit();
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
}
