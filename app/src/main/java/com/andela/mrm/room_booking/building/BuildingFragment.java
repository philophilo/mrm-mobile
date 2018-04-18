package com.andela.mrm.room_booking.building;

/**
 * Created by baron on 06/04/2018.
 */

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.andela.mrm.R;
import com.andela.mrm.room_booking.floor.FloorSelectionActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class BuildingFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
         View view = inflater
                .inflate(R.layout.fragment_building, container, false);
        ImageButton imageButton = view.findViewById(R.id.buildingButton);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), FloorSelectionActivity.class);
                startActivity(intent);
            }
        });
         return view;
    }
}

