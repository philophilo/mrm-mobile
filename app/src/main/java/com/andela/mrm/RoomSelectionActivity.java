package com.andela.mrm;

import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

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
