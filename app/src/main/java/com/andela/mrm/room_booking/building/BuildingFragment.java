package com.andela.mrm.room_booking.building;

/**
 * Created by baron on 06/04/2018.
 */

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.andela.mrm.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class BuildingFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
         return inflater
                .inflate(R.layout.fragment_building, container, false);
    }
}

