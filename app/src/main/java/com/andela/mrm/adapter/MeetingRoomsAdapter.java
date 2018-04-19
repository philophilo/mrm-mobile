package com.andela.mrm.adapter;


import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;

import com.andela.mrm.R;
import com.andela.mrm.room_booking.meeting_room.MeetingRoom;
import com.andela.mrm.room_booking.room_availability.RoomAvailabilityActivity;

import java.util.List;

/**
 * The type Meeting rooms adapter.
 */
public class MeetingRoomsAdapter extends BaseAdapter {

    private final Context context;
    private final List<MeetingRoom> meetingRooms;


    /**
     * Instantiates a new Meeting rooms adapter.
     *
     * @param context      the context
     * @param meetingRooms the meeting rooms
     */
    public MeetingRoomsAdapter(Context context, List<MeetingRoom> meetingRooms) {
        this.context = context;
        this.meetingRooms = meetingRooms;
    }

    /**
     * How many items are in the data set represented by this Adapter.
     *
     * @return Count of items.
     */
    @Override
    public int getCount() {
        return meetingRooms.size();
    }

    /**
     * Get the data item associated with the specified position in the data set.
     *
     * @param position Position of the item whose data we want within the adapter's
     *                 data set.
     * @return The data at the specified position.
     */
    @Override
    public Object getItem(int position) {
        return null;
    }

    /**
     * Get the row id associated with the specified position in the list.
     *
     * @param position The position of the item within the adapter's data set whose row id we want.
     * @return The id of the item at the specified position.
     */
    @Override
    public long getItemId(int position) {
        return 0;
    }

    /**
     * Get a View that displays the data at the specified position in the data set. You can either
     * create a View manually or inflate it from an XML layout file. When the View is inflated, the
     * parent View (GridView, ListView...) will apply default layout parameters unless you use
     * {@link LayoutInflater#inflate(int, ViewGroup, boolean)}
     * to specify a root view and to prevent attachment to the root.
     *
     * @param position    The position of the item within the adapter's data set of the item whose
     *                    view we want.
     * @param convertView The old view to reuse, if possible. Note: You should check that this view
     *                    is non-null and of an appropriate type before using. If it is not possible
     *                    to convert this view to display the correct data, this method can create a
     *                    new view.
     *                    Heterogeneous lists can specify their number of view types, so that this
     *                    View is
     *                    always of the right type (see {@link #getViewTypeCount()} and
     *                    {@link #getItemViewType(int)}).
     * @param parent      The parent that this view will eventually be attached to
     * @return A View corresponding to the data at the specified position.
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        MeetingRoom meetingRoom = meetingRooms.get(position);
        if (convertView == null) {
            LayoutInflater layoutInflater = LayoutInflater.from(context);
            convertView = layoutInflater.inflate(R.layout.partial_meeting_room_button, null);
        }

        final Button button = convertView.findViewById(R.id.meeting_room_button);

        button.setText(meetingRoom.getmeetingRoomName().toString());

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), RoomAvailabilityActivity.class);
                v.getContext().startActivity(intent);
            }
        });
        return convertView;
    }
}
