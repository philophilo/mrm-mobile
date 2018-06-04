package com.andela.mrm.room_setup;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.andela.mrm.AllLocationsQuery;
import com.andela.mrm.R;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;

/**
 * The type Room setup adapter.
 */
public class RoomSetupAdapter extends RecyclerView.Adapter<RoomSetupAdapter.ViewHolder> {

    private final RoomSetupFragment.ItemListener mItemListener;

    private List<AllLocationsQuery.AllLocation> mLocations;
    private List<AllLocationsQuery.Block> mBlocks;
    private List<AllLocationsQuery.Floor> mFloors;
    private List<AllLocationsQuery.Room> mRooms;

    /**
     * Instantiates a new Room setup adapter.
     *
     * @param listener the listener
     */
    public RoomSetupAdapter(RoomSetupFragment.ItemListener listener) {
        mItemListener = listener;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if (mLocations != null) {
            holder.bind(mLocations.get(position));
            return;
        }

        if (mBlocks != null) {
            holder.bind(mBlocks.get(position));
            return;
        }

        if (mFloors != null) {
            holder.bind(mFloors.get(position));
            return;
        }

        if (mRooms != null) {
            holder.bind(mRooms.get(position));
            return;
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_room_setup_selection, parent, false);
        return new ViewHolder(view, mItemListener);
    }

    @Override
    public int getItemCount() {
        if (mLocations != null) {
            return mLocations.size();
        }
        if (mBlocks != null) {
            return mBlocks.size();
        }
        if (mFloors != null) {
            return mFloors.size();
        }
        if (mRooms != null) {
            return mRooms.size();
        }
        return 0;
    }

    /**
     * Sets locations.
     *
     * @param locations the locations
     */
    public void setLocations(List<AllLocationsQuery.AllLocation> locations) {
        mLocations = locations;
        notifyDataSetChanged();
    }


    /**
     * Sets blocks.
     *
     * @param blocks the blocks
     */
    public void setBlocks(List<AllLocationsQuery.Block> blocks) {
        mBlocks = blocks;
        notifyDataSetChanged();
    }

    /**
     * Sets floors.
     *
     * @param floors the floors
     */
    public void setFloors(List<AllLocationsQuery.Floor> floors) {
        mFloors = floors;
        notifyDataSetChanged();
    }

    /**
     * Sets rooms.
     *
     * @param rooms the rooms
     */
    public void setRooms(List<AllLocationsQuery.Room> rooms) {
        mRooms = rooms;
        notifyDataSetChanged();
    }

    /**
     * The type View holder.
     */
    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private final Button mItemSelectionButton;
        private final TextView mItemSelectionText;
        private final ImageButton mItemSelectionImageBtn;
        private final RoomSetupFragment.ItemListener mItemListener;

        /**
         * Instantiates a new View holder.
         *
         * @param itemView the item view
         * @param listener the listener
         */
        public ViewHolder(View itemView, RoomSetupFragment.ItemListener listener) {
            super(itemView);
            mItemListener = listener;
            mItemSelectionButton = itemView.findViewById(R.id.button_setup_selection_item_image);
            mItemSelectionText = itemView.findViewById(R.id.text_setup_selection_item_text);
            mItemSelectionImageBtn = itemView.findViewById(R.id.imagebutton_setup_selection_item);

            mItemSelectionButton.setOnClickListener(this);
            mItemSelectionImageBtn.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            mItemListener.onItemClick(getAdapterPosition());
        }

        /**
         * Show location set data.
         *
         * @param location the location
         */
        public void bind(AllLocationsQuery.AllLocation location) {
            Glide.with(itemView.getContext())
                    .load(location.imageUrl())
                    .apply(new RequestOptions()
                            .placeholder(R.drawable.flag_placeholder)
                    )
                    .into(mItemSelectionImageBtn);
            mItemSelectionText.setText(location.name());
            mItemSelectionButton.setVisibility(View.INVISIBLE);
        }

        /**
         * Show room set data.
         *
         * @param block the block
         */
        public void bind(AllLocationsQuery.Block block) {
            mItemSelectionButton.setText(block.name());
        }

        /**
         * Show floor set data.
         *
         * @param floor the floor
         */
        public void bind(AllLocationsQuery.Floor floor) {
            mItemSelectionButton.setText(floor.name());
        }

        /**
         * Show room set data.
         *
         * @param room the room
         */
        public void bind(AllLocationsQuery.Room room) {
            mItemSelectionButton.setText(room.name());
        }
    }
}
