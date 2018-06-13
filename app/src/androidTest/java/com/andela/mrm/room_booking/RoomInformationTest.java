package com.andela.mrm.room_booking;

import android.os.SystemClock;
import android.support.test.rule.ActivityTestRule;
import android.view.View;

import com.andela.mrm.R;
import com.andela.mrm.room_booking.room_information.RoomInformationActivity;

import org.hamcrest.Matcher;
import org.junit.Rule;
import org.junit.Test;

 import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.hasDescendant;
import static android.support.test.espresso.matcher.ViewMatchers.isCompletelyDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.isDescendantOfA;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static junit.framework.Assert.assertTrue;
import static org.hamcrest.core.AllOf.allOf;

/**
 * The type Room information test.
 */
public class RoomInformationTest {

    /**
     * The Activity test rule.
     */
    @Rule
    public ActivityTestRule<RoomInformationActivity> activityTestRule =
            new ActivityTestRule<>(RoomInformationActivity.class);


    /**
     * Room information activity is displed.
     */
    @Test
    public void RoomInformationActivityIsDispled() {
        onView(withId(R.id.layout_close_event_schedule))
                .check(matches(isDisplayed()));
        onView(withId(R.id.room_name_text))
                .check(matches(isDisplayed()));
        onView(withId(R.id.floor_location_text))
                .check(matches(isDisplayed()));
        onView(withId(R.id.appbar))
                .check(matches(isDisplayed()));
    }

    /**
     * Similar rooms tab click.
     */
    @Test
    public void similarRoomsTabClick() {
        Matcher<View> matcher = allOf(withText("Similar Rooms"),
                isDescendantOfA(withId(R.id.tabs)));
        onView(matcher).perform(click());
        SystemClock.sleep(800);
        onView(withId(R.id.similar_room_recycler_view))
                .check(matches(isCompletelyDisplayed()));
        onView(withId(R.id.similar_room_recycler_view))
                .check(matches(hasDescendant(withId(R.id.similar_room_card))));
    }

    /**
     * Room amenities tab click.
     */
    @Test
    public void roomAmenitiesTabClick() {
        Matcher<View> matcher = allOf(withText("Room Amenities"),
                isDescendantOfA(withId(R.id.tabs)));
        onView(matcher).perform(click());
        SystemClock.sleep(800);
        onView(withId(R.id.room_amenities_recycler_view))
                .check(matches(isCompletelyDisplayed()));
        onView(withId(R.id.room_amenities_recycler_view))
                .check(matches(hasDescendant(withId(R.id.room_amenities_card))));
    }

    /**
     * Close button finishes activity.
     */
    @Test
    public void closeButtonFinishesActivity() {
        onView(withId(R.id.layout_close_event_schedule))
                .perform(click());
        assertTrue(activityTestRule.getActivity().isDestroyed());
    }

}
