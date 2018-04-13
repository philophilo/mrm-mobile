package com.andela.mrm.room_booking;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.andela.mrm.adapter.MeetingRoomsAdapter;
import com.andela.mrm.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class MeetingRoomFragment extends Fragment {
    /**
     * Instantiates a new Meeting room fragment.
     */
    public MeetingRoomFragment() {
        // This constructor is intentionally empty. Nothing special is needed here.
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_meeting_room, container, false);

        GridView gridView = view.findViewById(R.id.meeting_rooms_grid_view);
        MeetingRoomsAdapter meetingRoomsAdapter = new MeetingRoomsAdapter(getContext(),
                MeetingRoom.initializeMeetingRooms());
        gridView.setAdapter(meetingRoomsAdapter);

        return view;
    }

}
