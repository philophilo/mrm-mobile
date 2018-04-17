package com.andela.mrm.room_booking.meeting_room;

import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.andela.mrm.R;

/**
 * The type Room selection activity.
 */
public class RoomSelectionActivity extends AppCompatActivity {

    private FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room_selection);

        fragmentManager = getSupportFragmentManager();
        if (findViewById(R.id.frame_layout_container) != null) {
            fragmentManager
                    .beginTransaction()
                    .add(R.id.frame_layout_container, new MeetingRoomFragment())
                    .commit();
        }
    }

}
