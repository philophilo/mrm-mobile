package com.andela.mrm.room_booking.floor;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.andela.mrm.R;

/**
 * Floor selection activity.
 */
public class FloorSelectionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_floor_selection);

        FloorSelectionFragment floorSelectionFragment = new FloorSelectionFragment();

        FragmentManager fragmentManager = getSupportFragmentManager();
        if (findViewById(R.id.floor_layout_container) != null) {
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.add(R.id.floor_layout_container, floorSelectionFragment);
            fragmentTransaction.commit();
        }
    }
}
