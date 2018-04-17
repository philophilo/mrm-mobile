package com.andela.mrm.room_booking.floor;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.andela.mrm.R;
import com.andela.mrm.adapter.FloorAdapter;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 */
public class FloorSelectionFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view  = inflater.inflate(R.layout.fragment_floor_selection, container,
                false);
        GridView gridView = view.findViewById(R.id.floor_grid_view);
        FloorAdapter floorAdapter = new FloorAdapter(getContext(),
                Floor.initializeFloor());
        gridView.setAdapter(floorAdapter);
        return view;
    }
}
