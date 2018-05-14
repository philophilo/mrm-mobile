package com.andela.mrm.presenter;

import android.os.AsyncTask;
import android.util.Log;

import com.andela.mrm.room_booking.room_availability.models.CalendarEvent;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.DateTime;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.Events;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * An asynchronous task that handles the Google Calendar API call.
 * Placing the API calls in their own task ensures the UI stays responsive.
 */
public class MakeGoogleCalendarCallPresenter extends AsyncTask<Void, Void, List<Event>> {
    private final com.google.api.services.calendar.Calendar mService;
    private Exception mLastError = null;
    private final IMakeRequestTaskListener makeRequestTaskListener;

    /**
     * Instantiates a new Make request task.
     *
     * @param credential              the credential
     * @param makeRequestTaskListener the make request task listener
     */
    public MakeGoogleCalendarCallPresenter(GoogleAccountCredential credential,
                                           IMakeRequestTaskListener makeRequestTaskListener) {
        this.makeRequestTaskListener = makeRequestTaskListener;
        HttpTransport transport = AndroidHttp.newCompatibleTransport();
        JsonFactory jsonFactory = JacksonFactory.getDefaultInstance();
        mService = new com.google.api.services.calendar.Calendar.Builder(
                transport, jsonFactory, credential)
                .setApplicationName("Google Calendar API Android Quickstart")
                .build();
    }

    /**
     * The interface Make request task listener.
     */
    public interface IMakeRequestTaskListener {
        /**
         * On success.
         *
         * @param itemsInString the items in string
         */
        void onSuccess(String itemsInString);

        /**
         * On cancelled.
         *
         * @param mLastError the m last error
         */
        void onCancelled(Exception mLastError);
    }

    /**
     * Background task to call Google Calendar API.
     *
     * @param params no parameters needed for this task.
     */
    @Override
    protected List<Event> doInBackground(Void... params) {
        try {
            return getDataFromApi();
        } catch (Exception e) {
            mLastError = e;
            cancel(true);
            return null;
        }
    }

    /**
     * Fetch a list of the next 10 events from the primary calendar.
     *
     * @return List of Strings describing returned events.
     * @throws IOException ioException
     */
    private List<Event> getDataFromApi() throws IOException {
        // List the next 10 events from the primary calendar.
        List<Event> items;
        List<CalendarEvent> calendarEvents = new ArrayList<>();
        DateTime now = new DateTime(System.currentTimeMillis());
        Events events = mService.events().list("primary")
                .setMaxResults(10)
                .setTimeMin(now)
                .setOrderBy("startTime")
                .setSingleEvents(true)
                .execute();
        items = events.getItems();

        for (Event e : items) {
            DateTime start = e.getStart().getDateTime();
            DateTime end = e.getEnd().getDateTime();

            if (start == null) {
                start = e.getStart().getDate();
            }

            if (end == null) {
                end = e.getEnd().getDate();
            }

            calendarEvents.add(new CalendarEvent(e.getSummary(),
                    start.getValue(),
                    end.getValue()));
        }

        String listItemInGson = new Gson().toJson(calendarEvents);
        if (makeRequestTaskListener != null) {
            makeRequestTaskListener.onSuccess(listItemInGson);
        }
        return items;
    }


    @Override
    protected void onPreExecute() {
        Log.v("Loading", "Loading now...");
    }

    @Override
    protected void onPostExecute(List<Event> output) {
        if (output == null || output.isEmpty()) {
            Log.v("No results", "No results returned.");
        } else {
            for (int i = 0; i < output.size(); i++) {
                Log.v("output", "" + output.get(i).getId());
            }
        }
    }

    @Override
    protected void onCancelled() {
        if (makeRequestTaskListener != null) {
            makeRequestTaskListener.onCancelled(mLastError);
        }
    }
}
