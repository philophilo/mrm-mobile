package com.andela.mrm.contract;

import com.andela.mrm.AllLocationsQuery;

import java.util.List;

/**
 * Created by chike on 16/05/2018.
 */

public interface RoomBookingContract {
    /**
     * Interface binding/connecting the RoomBookingPresenter class to its Fragments view class.
     */
    interface CountryContract {
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

        /**
         * Abstract method called for Displaying snackbar notification.
         * for poor or no network connections
         *
         * @param isNetworkAvailable - boolean value which determines if there is network or not
         */
        void displaySnackBar(Boolean isNetworkAvailable);
    }

    /**
     * Interface binding/connecting the RoomBookingPresenter class to its building view class.
     */
    interface BuildingView {
        /**
         *
         * @param mBlock - number of buildings/blocks
         */
        void displayBuildings(List<AllLocationsQuery.Block> mBlock);
    }
}
