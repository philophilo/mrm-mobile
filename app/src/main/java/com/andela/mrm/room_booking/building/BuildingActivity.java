package com.andela.mrm.room_booking.building;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import com.andela.mrm.R;

/**
 * Building Activity Class.
 */
public class BuildingActivity extends AppCompatActivity {
    FragmentManager fragmentManager;
    public static final String ARG_COUNTRY_ID = "countryID";
    public int argCountryId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_building);

        Bundle args = new Bundle();
        args.putSerializable(ARG_COUNTRY_ID, getValue());
        BuildingFragment fragment = new BuildingFragment();
        fragment.setArguments(args);

        fragmentManager = getSupportFragmentManager();
            if (findViewById(R.id.frame_layout_container) != null) {
                fragmentManager
                        .beginTransaction()
                        .add(R.id.frame_layout_container, fragment)
                        .commit();
                   }
    }

    /**
     * gets value of position of selected country.
     * @return - string
     */
    public String getValue() {
        //RECEIVE DATA
        Intent i = this.getIntent();
        int countryID = i.getExtras().getInt("countryID");
        argCountryId = countryID;
        return String.valueOf(countryID);
    }
}




