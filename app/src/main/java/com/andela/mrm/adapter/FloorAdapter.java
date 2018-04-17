package com.andela.mrm.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;

import com.andela.mrm.R;
import com.andela.mrm.room_booking.floor.Floor;

import java.util.List;

/**
 * Created by isioyemohammed on 12/04/2018.
 * mrm-mobile
 */

public class FloorAdapter extends BaseAdapter {

    private final Context context;
    private final List<Floor> floors;

    /**
     * Floor adapter constructor.
     * @param context - Instance of the current context
     * @param floors - List of floors
     */
    public FloorAdapter(Context context, List<Floor> floors) {
        this.context = context;
        this.floors = floors;
    }
    @Override
    public int getCount() {
        return floors.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        Floor floor = floors.get(position);

        if (view == null) {
            LayoutInflater layoutInflater = LayoutInflater.from(context);
            view = layoutInflater.inflate(R.layout.partial_floor_button, null);
        }
        Button button = view.findViewById(R.id.floor_button);
        button.setText(floor.getFloorNumber());
        return view;
    }

}
