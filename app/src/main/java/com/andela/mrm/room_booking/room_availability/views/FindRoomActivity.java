package com.andela.mrm.room_booking.room_availability.views;

import android.app.Activity;
import android.app.ListActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.andela.mrm.R;
import com.andela.mrm.adapter.DropdownFilterAdapter;
import com.andela.mrm.adapter.FindRoomAdapter;
import com.andela.mrm.adapter.SelectedFiltersAdapter;
import com.andela.mrm.room_booking.room_availability.models.Rooms;

import java.util.ArrayList;
import java.util.List;

/**
 * The type Find room activity.
 */
public class FindRoomActivity extends AppCompatActivity {
    private ImageView closeActivity;
    /**
     * The Find room recycler view.
     */
    RecyclerView findRoomRecyclerView;
    /**
     * The Room.
     */
    Rooms room;
    private ImageView filterAvailability, filterLocation, filterCapacity, filterAmenities;
    private RecyclerView availabilityFilterDropdown, locationFilterDropdown, capacityFilterDropdown,
    amenitiesFilterDropdown, selectedFiltersDisplay;
    /**
     * The Availabilty options.
     */
    final List<String> availabiltyOptions = new ArrayList<>();
    /**
     * The Location options.
     */
    final List<String> locationOptions = new ArrayList<>();
    /**
     * The Capacity options.
     */
    final List<String> capacityOptions = new ArrayList<>();
    /**
     * The Amenities option.
     */
    final List<String> amenitiesOption = new ArrayList<>();
    final List<String> filters = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_room);

        availabiltyOptions.add("Available");
        availabiltyOptions.add("Unavailable");

        locationOptions.add("Block A, First Floor");
        locationOptions.add("Gold Coast, First Floor");
        locationOptions.add("Big Apple, Fourth Floor");
        locationOptions.add("Naija, Third Floor");

        capacityOptions.add("5 participants");
        capacityOptions.add("10 participants");
        capacityOptions.add("15 participants");
        capacityOptions.add("20 participants");

        amenitiesOption.add("Apple TV");
        amenitiesOption.add("Jabra speaker");
        amenitiesOption.add("Headphones");
        amenitiesOption.add("Projector");

        filters.add("Available");
        filters.add("Block A, First Floor");
        filters.add("10 participants");
        filters.add("Headphones");

        closeActivity = findViewById(R.id.close_find_room);
        findRoomRecyclerView = findViewById(R.id.recycler_view_filter_result);
        availabilityFilterDropdown = findViewById(R.id.filter_dropdown_availability);
        locationFilterDropdown = findViewById(R.id.filter_dropdown_location);
        capacityFilterDropdown = findViewById(R.id.filter_dropdown_capacity);
        amenitiesFilterDropdown = findViewById(R.id.filter_dropdown_amenities);

        setOnClickListenerForAvailabilityFilter();
        setOnClickListenerForLocationFilter();
        setOnClickListenerForCapacityFilter();
        setOnClickListenerForAmenititesFilter();
        updateFilters(filters);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this,
                LinearLayoutManager.HORIZONTAL, false);
        findRoomRecyclerView.setAdapter(new FindRoomAdapter(room.initializeRooms(), this));
        findRoomRecyclerView.setLayoutManager(layoutManager);

        closeActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    /**
     * Sets on click listener for availability filter.
     */
    public void setOnClickListenerForAvailabilityFilter() {
        filterAvailability = findViewById(R.id.dropdown_availability_filter);
        RecyclerView.LayoutManager availabilityFilterLayoutManager =
                new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        availabilityFilterDropdown.setLayoutManager(availabilityFilterLayoutManager);
        availabilityFilterDropdown.setAdapter(new DropdownFilterAdapter(availabiltyOptions));

        filterAvailability.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (availabilityFilterDropdown.getVisibility() == View.GONE) {
                    availabilityFilterDropdown.setVisibility(View.VISIBLE);
                } else {
                    availabilityFilterDropdown.setVisibility(View.GONE);
                }
            }
        });
    }

    /**
     * Sets on click listener for location filter.
     */
    public void setOnClickListenerForLocationFilter() {
        RecyclerView.LayoutManager locationFilterLayoutManager =
                new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        locationFilterDropdown.setLayoutManager(locationFilterLayoutManager);
        locationFilterDropdown.setAdapter(new DropdownFilterAdapter(locationOptions));

        filterLocation = findViewById(R.id.dropdown_location_filter);
        filterLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (locationFilterDropdown.getVisibility() == View.GONE) {
                    locationFilterDropdown.setVisibility(View.VISIBLE);
                } else {
                    locationFilterDropdown.setVisibility(View.GONE);
                }
            }
        });
    }

    /**
     * Sets on click listener for capacity filter.
     */
    public void setOnClickListenerForCapacityFilter() {
        RecyclerView.LayoutManager capacityFilterLayoutManager =
                new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        capacityFilterDropdown.setLayoutManager(capacityFilterLayoutManager);
        capacityFilterDropdown.setAdapter(new DropdownFilterAdapter(capacityOptions));

        filterCapacity = findViewById(R.id.dropdown_capacity_filter);
        filterCapacity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (capacityFilterDropdown.getVisibility() == View.GONE) {
                    capacityFilterDropdown.setVisibility(View.VISIBLE);
                } else {
                    capacityFilterDropdown.setVisibility(View.GONE);
                }
            }
        });
    }

    /**
     * Sets on click listener for amenitites filter.
     */
    public void setOnClickListenerForAmenititesFilter() {
        RecyclerView.LayoutManager amenitiesFilterLayoutManager =
                new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        amenitiesFilterDropdown.setLayoutManager(amenitiesFilterLayoutManager);
        amenitiesFilterDropdown.setAdapter(new DropdownFilterAdapter(amenitiesOption));

        filterAmenities = findViewById(R.id.dropdown_amenities_filter);
        filterAmenities.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (amenitiesFilterDropdown.getVisibility() == View.GONE) {
                    amenitiesFilterDropdown.setVisibility(View.VISIBLE);
                } else {
                    amenitiesFilterDropdown.setVisibility(View.GONE);
                }
            }
        });
    }

    /**
     * Update filters.
     *
     * @param filtersList the filters list
     */
    public void updateFilters(List<String> filtersList) {
        if (selectedFiltersDisplay == null) {
            selectedFiltersDisplay = findViewById(R.id.layout_filters_display);
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this,
                    LinearLayoutManager.HORIZONTAL, false);
            selectedFiltersDisplay.setAdapter(new SelectedFiltersAdapter(filtersList));
            selectedFiltersDisplay.setLayoutManager(layoutManager);
        }
    }
}
