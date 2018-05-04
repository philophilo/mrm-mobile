package com.andela.mrm.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;

import com.andela.mrm.R;
import com.andela.mrm.room_booking.building.Building;
import com.andela.mrm.room_booking.floor.FloorSelectionActivity;

import java.util.List;

/**
 * Created by baron on 20/04/2018.
 */

public class BuildingAdapter extends BaseAdapter {

    public final Context context;
    private final List<Building> buildings;

    /**
     * Building adapter constructor.
     * @param context - Instance of the current context
     * @param buildings - List of buildings
     */
    public BuildingAdapter(Context context, List<Building> buildings) {
        this.context = context;
        this.buildings = buildings;
    }

    @Override
    public int getCount() {
        return buildings.size();
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
    public View getView(int position, View view, ViewGroup parent) {
        Building building = buildings.get(position);

        if (view == null) {
            LayoutInflater layoutInflater = LayoutInflater.from(context);
            view = layoutInflater.inflate(R.layout.partial_building_button, null);
        }

        Button button = view.findViewById(R.id.building_button);
        button.setText(building.getbuildingName());
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, FloorSelectionActivity.class);
                context.startActivity(intent);
            }
        });
        return view;
    }
}
