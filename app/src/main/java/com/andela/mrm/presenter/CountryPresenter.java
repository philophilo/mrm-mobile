package com.andela.mrm.presenter;

import android.util.Log;

import com.andela.mrm.AllLocationsQuery;
import com.andela.mrm.room_booking.country.CountryFragment;
import com.andela.mrm.service.MyApolloClient;
import com.apollographql.apollo.ApolloCall;
import com.apollographql.apollo.api.Response;
import com.apollographql.apollo.exception.ApolloException;

import java.util.List;

import javax.annotation.Nonnull;

/**
 * CountryPresenter class.
 */

public class CountryPresenter {
    public CountryFragment view;

    /**
     * CountryPresenter class constructor method.
     *
     * @param view - fragment view context
     */
    public CountryPresenter(CountryFragment view) {

        this.view = view;
    }

    /**
     * Interface binding/connecting the CountryPresenter class to its CountryFragment view class.
     */
    public interface CountryView {
        /**
         * Abstract method for displaying list of countries gotten from api.
         *
         * @param mData - list of all data from graphQL api endpoint
         */
        void displayCountries(List<AllLocationsQuery.AllLocation> mData);

        /**
         * Abstract method called for dismissing progress dialog if present.
         */
        void dismissDialog();
    }

    /**
     * Class method for querying graphQL api endpoint through the apollo client.
     */
    public void getAllLocations() {
        MyApolloClient.getMyApolloClient().query(
                AllLocationsQuery.builder().build()
        ).enqueue(new ApolloCall.Callback<AllLocationsQuery.Data>() {
            @Override
            public void onResponse(@Nonnull Response<AllLocationsQuery.Data> response) {

                view.displayCountries(response.data().allLocations());
            }

            @Override
            public void onFailure(@Nonnull ApolloException e) {
                view.dismissDialog();
                Log.e("ERROR OCCURRED", e.getMessage());
            }
        });

    }
}