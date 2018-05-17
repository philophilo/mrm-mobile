package com.andela.mrm.room_booking.room_availability.models;


import android.support.annotation.Nullable;

/**
 * Created by andeladeveloper on 01/05/2018.
 */
public class CalendarEvent {

    private final String summary;
    private final Long startTime;
    private final Long endTime;

    /**
     * Instantiates a new Calendar event.
     *
     * @param summary   the summary
     * @param startTime the start time
     * @param endTime   the end time
     */
    public CalendarEvent(String summary, @Nullable Long startTime, @Nullable Long endTime) {
        this.summary = summary;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    /**
     * Gets summary.
     *
     * @return the summary
     */
    public String getSummary() {
        return this.summary;
    }

    /**
     * Gets start time.
     *
     * @return the start time
     */
    public Long getStartTime() {
        return this.startTime;
    }

    /**
     * Gets end time.
     *
     * @return the end time
     */
    public Long getEndTime() {
        return endTime;
    }
}
