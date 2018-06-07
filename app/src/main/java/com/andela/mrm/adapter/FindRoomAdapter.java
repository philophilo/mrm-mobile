package com.andela.mrm.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.andela.mrm.R;
import com.andela.mrm.room_booking.room_availability.models.Rooms;

import java.util.List;

/**
 * The type Find room adapter.
 */
public class FindRoomAdapter extends RecyclerView.Adapter<FindRoomAdapter.ViewHolder> {
    /**
     * The Rooms.
     */
    List<Rooms> rooms;
    /**
     * The Context.
     */
    Context context;

    /**
     * Instantiates a new Find room adapter.
     *
     * @param rooms   the amenities
     * @param context the context
     */
    public FindRoomAdapter(List<Rooms> rooms, Context context) {
        this.rooms = rooms;
        this.context = context;
    }

    @NonNull
    @Override
    public FindRoomAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        CardView cardView = (CardView) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.partial_meeting_room_details, parent, false);
        return new FindRoomAdapter.ViewHolder(cardView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.setValues(position);
    }


    @Override
    public int getItemCount() {
        return rooms.size();
    }

    /**
     * The type View holder.
     */
    public class ViewHolder extends RecyclerView.ViewHolder {
        /**
         * The Capacity.
         */
        TextView capacity, /**
         * The Room name.
         */
        roomName, /**
         * The Room location.
         */
        roomLocation;
        /**
         * The Amenities recycler view.
         */
        RecyclerView amenitiesRecyclerView;

        /**
         * Instantiates a new View holder.
         *
         * @param itemView the item view
         */
        public ViewHolder(View itemView) {
            super(itemView);
            capacity = itemView.findViewById(R.id.text_room_capacity);
            roomLocation = itemView.findViewById(R.id.text_meeting_room_location);
            roomName = itemView.findViewById(R.id.text_meeting_room_name);
            amenitiesRecyclerView = itemView.findViewById(R.id.recycler_view_room_amenities);
        }

        /**
         * Sets values.
         *
         * @param position the position
         */
        public void setValues(int position) {
            capacity.setText(String.valueOf(rooms.get(position).getCapacity()));
            roomLocation.setText(rooms.get(position).getLocation());
            roomName.setText(rooms.get(position).getName());
            RecyclerView.LayoutManager layoutManager = new GridLayoutManager(context, 2);
            amenitiesRecyclerView
                    .setAdapter(new FindRoomAmenitiesAdapter(rooms.get(position).getAmenities()));
            amenitiesRecyclerView.setLayoutManager(layoutManager);
        }
    }
}
