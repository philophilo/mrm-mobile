package com.andela.mrm.util;

import android.support.test.espresso.IdlingResource;
import android.support.test.espresso.idling.CountingIdlingResource;

/**
 * EspressoIdlingResouce class.
 */
public final class EspressoIdlingResource {
    private static final String RESOURCE = "GLOBAL";

    private static CountingIdlingResource mCountingIdlingResource =
            new CountingIdlingResource(RESOURCE);

    /**
     * private constructor.
     * prevents instantiation
     */
    private EspressoIdlingResource() {
        // cannot be instantiated
    }

    /**
     * Increment counter.
     */
    public static void increment() {
        mCountingIdlingResource.increment();
    }

    /**
     * Decrement counter.
     */
    public static void decrement() {
        mCountingIdlingResource.decrement();
    }

    /**
     * Get instance of IdlingResource.
     *
     * @return - instance of the IdlingResource
     */
    public static IdlingResource getIdlingResource() {
        return mCountingIdlingResource;
    }
}

