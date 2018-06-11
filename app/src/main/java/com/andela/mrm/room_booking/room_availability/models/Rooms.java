package com.andela.mrm.room_booking.room_availability.models;

import java.util.ArrayList;
import java.util.List;


/**
 * The type Rooms.
 */
public class Rooms {
    private final String name, location;
    private final int capacity;
    private final List<String> amenities;

    /**
     * Instantiates a new Rooms.
     *
     * @param name      the name
     * @param location  the location
     * @param amenities the amenities
     * @param capacity  the capacity
     */
    public Rooms(String name, String location, List<String> amenities, int capacity) {
        this.name = name;
        this.location = location;
        this.amenities = amenities;
        this.capacity = capacity;
    }

    /**
     * Initialize rooms list.
     *
     * @return the list
     */
    public static List<Rooms> initializeRooms() {
        List<Rooms> allRooms = new ArrayList();
        List<String> amenitites = new ArrayList<>();

        amenitites.add("Apple TV (1)");
        amenitites.add("Jabra speaker (2)");
        amenitites.add("Headphone (4)");
        amenitites.add("Projector(1)");

        allRooms.add(new Rooms("Ol karia", "Block A, First Floor", amenitites, 23));
        allRooms.add(new Rooms("Awkaaba", "Gold Coast, First Floor", amenitites, 10));
        allRooms.add(new Rooms("Chaley", "Gold Coast, First Floor Basement", amenitites, 5));
        allRooms.add(new Rooms("Friend zone", "Big Apple, Fourth Floor", amenitites, 6));
        return allRooms;
    }

    /**
     * Gets name.
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Gets location.
     *
     * @return the location
     */
    public String getLocation() {
        return location;
    }

    /**
     * Gets capacity.
     *
     * @return the capacity
     */
    public int getCapacity() {
        return capacity;
    }

    /**
     * Gets amenities.
     *
     * @return the amenities
     */
    public List<String> getAmenities() {
        return amenities;
    }
}
