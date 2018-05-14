package com.andela.mrm.room_booking.room_availability.views;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import com.andela.mrm.R;
import com.andela.mrm.adapter.EventScheduleAdapter;
import com.andela.mrm.room_booking.room_availability.models.CalendarEvent;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

/**
 * The type Event schedule activity.
 */
public class EventScheduleActivity extends Activity {

    /**
     * The onCreate Method.
     * @param savedInstanceState savedInstance.
     */
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_schedule);

        String eventsInString = getIntent()
                .getStringExtra(RoomAvailabilityActivity.EVENTS_IN_STRING);

        LinearLayout closeSchedule = findViewById(R.id.layout_close_event_schedule);
        if (closeSchedule != null) {
            closeSchedule.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });
        }

        if (eventsInString != null) {
            Type listType = new TypeToken<List<CalendarEvent>>() { }.getType();
            List<CalendarEvent> events = new Gson().fromJson(eventsInString, listType);
            Log.e("gson to list", new Gson().toJson(events.get(0)));

            EventScheduleAdapter eventScheduleAdapter = new EventScheduleAdapter(events);
            RecyclerView eventScheduleRecyclerView = findViewById(R.id.layout_event_recycler_view);
            RecyclerView.LayoutManager eventScheduleLayoutManager =
                    new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
            eventScheduleRecyclerView.setLayoutManager(eventScheduleLayoutManager);
            eventScheduleRecyclerView.setAdapter(eventScheduleAdapter);

        }

    }
}
