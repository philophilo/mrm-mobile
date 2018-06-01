package com.andela.mrm.room_booking.meeting_room;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import com.andela.mrm.R;

/**
 * The type Room selection activity.
 */
public class RoomSelectionActivity extends AppCompatActivity {
    String countryID;
    String buildingID;
    String floorID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room_selection);

        getValues();
        Bundle args = new Bundle();
        args.putString("countryID", countryID);
        args.putString("buildingID", buildingID);
        args.putString("floorID", floorID);

        MeetingRoomFragment meetingRoomFragment = new MeetingRoomFragment();
        meetingRoomFragment.setArguments(args);

        FragmentManager fragmentManager = getSupportFragmentManager();
        if (findViewById(R.id.frame_layout_container) != null) {
            fragmentManager
                    .beginTransaction()
                    .add(R.id.frame_layout_container, meetingRoomFragment)
                    .commit();
        }
    }

    /**
     * retrieve and store intent values.
     */
    public void getValues() {
        Intent intent = this.getIntent();
        countryID = intent.getExtras().getString("countryID");
        buildingID = intent.getExtras().getString("buildingID");
        floorID = intent.getExtras().getString("floorID");
    }

}
