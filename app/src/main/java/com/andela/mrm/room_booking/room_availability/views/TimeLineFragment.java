package com.andela.mrm.room_booking.room_availability.views;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.andela.mrm.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class TimeLineFragment extends Fragment {


    /**
     * Instantiates a new Time line fragment.
     */
    public TimeLineFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_time_line, container, false);
    }

}
