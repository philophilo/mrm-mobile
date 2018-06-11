package com.andela.mrm.room_booking.room_information;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.andela.mrm.R;
import com.andela.mrm.adapter.RoomAmenitiesAdapter;

/**
 * A simple {@link Fragment} subclass.
 */
public class RoomAmenitiesFragment extends Fragment {


    private RecyclerView mRecyclerView;
    /**
     * The Layout manager.
     */
    RecyclerView.LayoutManager layoutManager;


    /**
     * Instantiates a new Room amenities fragment.
     */
    public RoomAmenitiesFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater
                .inflate(R.layout.fragment_room_amenities, container, false);
        mRecyclerView = view.findViewById(R.id.room_amenities_recycler_view);
        mRecyclerView.hasFixedSize();
        layoutManager = new GridLayoutManager(getContext(), 4);
        mRecyclerView.setLayoutManager(layoutManager);
        RecyclerView.Adapter adapter = new RoomAmenitiesAdapter(
                RoomInformation.setRooms(), getContext());
        mRecyclerView.setAdapter(adapter);
        return view;
    }
}
