package com.andela.mrm.room_booking.building;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by baron on 20/04/2018.
 */

public class Building {

    private final String buildingName;

    /**
     * Instantiates a new buildings.
     *
     * @param buildingName the meeting room name
     */
    public Building(String buildingName) {
        this.buildingName = buildingName;
    }

    /**
     * Static method for building initialization.
     * @return allBuildings
     */
    public static List<Building> initializeBuildings() {
        List<Building> allBuildings = new ArrayList<>();

        allBuildings.add(new Building("EPIC Tower"));
        allBuildings.add(new Building("Cambodia"));
        allBuildings.add(new Building("High Tower"));

        return allBuildings;
    }

    /**
     * Gets building name.
     *
     * @return the building name
     */
    public String getbuildingName() {
        return this.buildingName;
    }
}
