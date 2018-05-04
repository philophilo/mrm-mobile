package com.andela.mrm.room_booking.building;

/**
 * Created by baron on 06/04/2018.
 */

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.andela.mrm.R;
import com.andela.mrm.adapter.BuildingAdapter;

/**
 * A simple {@link Fragment} subclass.
 */
public class BuildingFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        int buildingSize = Building.initializeBuildings().size();
        int numCols = 3;

        View view = inflater
                .inflate(R.layout.fragment_building, container, false);
        GridView gridView = view.findViewById(R.id.building_grid_view);
        BuildingAdapter buildingAdapter = new BuildingAdapter(getContext(),
                Building.initializeBuildings());

        if (buildingSize < 3) {
            numCols = buildingSize;
        }
        gridView.setNumColumns(numCols);
        gridView.setAdapter(buildingAdapter);
        return view;
    }
}
