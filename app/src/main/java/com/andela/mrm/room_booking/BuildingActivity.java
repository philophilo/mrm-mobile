package com.andela.mrm.room_booking;

import android.support.v7.app.AppCompatActivity;
import android.support.v4.app.FragmentManager;
import android.os.Bundle;

import com.andela.mrm.R;

/**
 * Building Activity Class.
 */
public class BuildingActivity extends AppCompatActivity {
    FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_building);

        fragmentManager = getSupportFragmentManager();
            if (findViewById(R.id.frame_layout_container) != null) {
                fragmentManager
                        .beginTransaction()
                        .add(R.id.frame_layout_container, new BuildingFragment())
                        .commit();
                   }
    }
}




