package com.andela.mrm.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.andela.mrm.R;
import com.andela.mrm.room_booking.room_information.RoomInformation;

import java.util.List;

/**
 * The type Room amenities adapter.
 */
public class RoomAmenitiesAdapter extends RecyclerView.Adapter<RoomAmenitiesAdapter.ViewHolder> {

    private final List<RoomInformation> roomAmenitesList;
    private final Context mContext;
    private List<String> roomItem;

    /**
     * Instantiates a new Room amenities adapter.
     *
     * @param roomAmenitesList the room amenites list
     * @param context          the context
     */
    public RoomAmenitiesAdapter(List<RoomInformation> roomAmenitesList, Context context) {
        this.roomAmenitesList = roomAmenitesList;
        this.mContext = context;
    }

    @NonNull
    @Override
    public RoomAmenitiesAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent,
                                                              int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.partial_item_amenities, parent, false);

        return new ViewHolder(view);
    }


    /**
     * The type View holder.
     */
    static class ViewHolder extends RecyclerView.ViewHolder {
        /**
         * The Room item.
         */
        TextView roomItem, /**
         * The Room item quantity.
         */
        roomItemQuantity;

        /**
         * Instantiates a new View holder.
         *
         * @param itemView the item view
         */
        ViewHolder(View itemView) {
            super(itemView);
            roomItem = itemView.findViewById(R.id.item_name);
            roomItemQuantity = itemView.findViewById(R.id.item_quantity_number);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RoomAmenitiesAdapter.ViewHolder holder, int position) {
        final String roomItem = roomAmenitesList.get(position).getSingleRoomItem();
        final int roomQuantity = roomAmenitesList.get(position).getRoomItemQuantity();

        holder.roomItem.setText(roomItem);
        holder.roomItemQuantity.setText(String.valueOf(roomQuantity));
    }

    @Override
    public int getItemCount() {
        return roomAmenitesList.size();
    }
}
