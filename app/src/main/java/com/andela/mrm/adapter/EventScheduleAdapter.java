package com.andela.mrm.adapter;

import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.andela.mrm.R;
import com.andela.mrm.room_booking.room_availability.models.CalendarEvent;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

/**
 * Created by andeladeveloper on 27/04/2018.
 */
public class EventScheduleAdapter extends RecyclerView.Adapter<EventScheduleAdapter.ViewHolder> {
    /**
     * The Calendar events.
     */
    public final List<CalendarEvent> calendarEvents;

    /**
     * Instantiates a new Event schedule adapter.
     *
     * @param events the events
     */
    public EventScheduleAdapter(List<CalendarEvent> events) {
        this.calendarEvents = events;
    }

    /**
     * Called when RecyclerView needs a new {@link ViewHolder} of the given type to represent
     * an item.
     * <p>
     * This new ViewHolder should be constructed with a new View that can represent the items
     * of the given type. You can either create a new View manually or inflate it from an XML
     * layout file.
     * <p>
     * The new ViewHolder will be used to display items of the adapter using
     * {@link #onBindViewHolder(ViewHolder, int, List)}. Since it will be re-used to display
     * different items in the data set, it is a good idea to cache references to sub views of
     * the View to avoid unnecessary {@link View#findViewById(int)} calls.
     *
     * @param parent   The ViewGroup into which the new View will be added after it is bound to
     *                 an adapter position.
     * @param viewType The view type of the new View.
     * @return A new ViewHolder that holds a View of the given view type.
     * @see #getItemViewType(int)
     * @see #onBindViewHolder(ViewHolder, int)
     */
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ConstraintLayout constraintLayout = (ConstraintLayout)
                LayoutInflater.from(parent.getContext())
                .inflate(R.layout.partial_event_schedule_view, parent, false);
        return new ViewHolder(constraintLayout);
    }

    /**
     * Called by RecyclerView to display the data at the specified position. This method should
     * update the contents of the {@link ViewHolder#itemView} to reflect the item at the given
     * position.
     * <p>
     * Note that unlike {@link ListView}, RecyclerView will not call this method
     * again if the position of the item changes in the data set unless the item itself is
     * invalidated or the new position cannot be determined. For this reason, you should only
     * use the <code>position</code> parameter while acquiring the related data item inside
     * this method and should not keep a copy of it. If you need the position of an item later
     * on (e.g. in a click listener), use {@link ViewHolder#getAdapterPosition()} which will
     * have the updated adapter position.
     * <p>
     * Override {@link #onBindViewHolder(ViewHolder, int, List)} instead if Adapter can
     * handle efficient partial bind.
     *
     * @param holder   The ViewHolder which should be updated to represent the contents of the
     *                 item at the given position in the data set.
     * @param position The position of the item within the adapter's data set.
     */
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Log.e("Position", position + "");

        holder.setValue(position);
    }

    /**
     * Returns the total number of items in the data set held by the adapter.
     *
     * @return The total number of items in this adapter.
     */
    @Override
    public int getItemCount() {
        return calendarEvents.size();
    }

    /**
     * The type View holder.
     */
    public class ViewHolder extends RecyclerView.ViewHolder {

        /**
         * The Event title.
         */
        public TextView eventTitle, /**
         * The Start time.
         */
        startTime, /**
         * The Duration.
         */
        duration;

        /**
         * Instantiates a new View holder.
         *
         * @param itemView the item view
         */
        public ViewHolder(View itemView) {
            super(itemView);
            eventTitle = itemView.findViewById(R.id.text_event_title);
            startTime = itemView.findViewById(R.id.text_meeting_time);
            duration = itemView.findViewById(R.id.text_event_duration);
        }

        /**
         * Sets value.
         *
         * @param position the position
         */
        public void setValue(int position) {
            Long end = calendarEvents.get(position).getEndTime();
            Long start  = calendarEvents.get(position).getStartTime();

            Long diff = end - start;
            eventTitle.setText(calendarEvents.get(position).getSummary());
            startTime.setText(formatTime(start, "GMT+1", false));
            duration.setText(formatTime(diff, "GMT+1", true) + " hrs");

        }

        /**
         * Format time string.
         *
         * @param timeValue  the time value
         * @param timeZone   the time zone
         * @param isTimeDiff the is time diff
         * @return the string
         */
        public String formatTime(Long timeValue, String timeZone, Boolean isTimeDiff) {
            DateFormat format;
            Date time = new Date(timeValue);
            if (isTimeDiff) {
                format = new SimpleDateFormat("h:mm", Locale.getDefault());
            } else {
                format = new SimpleDateFormat("h:mm a ", Locale.getDefault());
            }
            format.setTimeZone(TimeZone.getTimeZone(timeZone));
            return format.format(time);
        }

    }
}
