package com.andela.mrm.room_booking.floor;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by isioyemohammed on 12/04/2018.
 * mrm-mobile
 */

public class Floor {
    private final String floorNumber;

    /**
     * Floor instantiation.
     * @param floorNumber floor number
     */
    public Floor(String floorNumber) {
        this.floorNumber = floorNumber;
    }

    /**
     * Static method for floor initialization.
     * @return allFloors
     */
    public static List<Floor> initializeFloor() {
        List<Floor> allFloors = new ArrayList<>();

        allFloors.add(new Floor("1st Floor"));
        allFloors.add(new Floor("2nd Floor"));
        allFloors.add(new Floor("3rd Floor"));
        allFloors.add(new Floor("4th Floor"));
        allFloors.add(new Floor("5th Floor"));

        return allFloors;
    }

    /**
     * Getter method for floor number.
     * @return floor number
     */
    public String getFloorNumber() {
        return this.floorNumber;
    }
}
