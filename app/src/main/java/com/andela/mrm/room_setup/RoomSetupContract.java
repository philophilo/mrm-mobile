package com.andela.mrm.room_setup;

import android.content.Context;

import com.andela.mrm.AllLocationsQuery;
import com.andela.mrm.room_setup.data.SetupPage;

import java.util.List;

/**
 * The interface Room setup contract.
 */
public interface RoomSetupContract {
    /**
     * The interface Actions listener.
     */
    interface ActionsListener {
        /**
         * Load setup data.
         */
        void loadSetupData();

        /**
         * Open next setup ui.
         *
         * @param selectedItemPosition the selected item position
         */
        void openNextSetupUi(int selectedItemPosition);

        /**
         * On back pressed boolean.
         *
         * @return the boolean
         */
        boolean onBackPressed();
    }

    /**
     * The interface View.
     */
    interface View {
        /**
         * Show progress dialog.
         *
         * @param isLoadingData the is loading data
         */
        void showProgressDialog(boolean isLoadingData);

        /**
         * Show locations.
         *
         * @param locations the locations
         */
        void showLocations(List<AllLocationsQuery.AllLocation> locations);

        /**
         * Show buildings.
         *
         * @param blocks the blocks
         */
        void showBuildings(List<AllLocationsQuery.Block> blocks);

        /**
         * Show floors.
         *
         * @param floors the floors
         */
        void showFloors(List<AllLocationsQuery.Floor> floors);

        /**
         * Show rooms.
         *
         * @param rooms the rooms
         */
        void showRooms(List<AllLocationsQuery.Room> rooms);

        /**
         * Show current setup page title.
         *
         * @param title the title
         */
        void showCurrentSetupPageTitle(String title);

        /**
         * Show network error snack.
         *
         * @param errorMessage the error message
         */
        void showNetworkErrorSnack(String errorMessage);

        /**
         * Show next setup ui.
         */
        void showNextSetupUi();

        /**
         * Show next activity.
         */
        void showNextActivity();

        /**
         * Network is available boolean.
         *
         * @return the boolean
         */
        boolean networkIsAvailable();

        /**
         * Show empty selection set text.
         *
         * @param text the text
         */
        void showEmptySelectionSetText(String text);

        /**
         * Gets context.
         *
         * @return the context
         */
        Context getContext();
    }

    /**
     * The interface Data.
     */
    interface Data {

        /**
         * Gets room setup data.
         *
         * @param callback the callback
         * @param context  the context
         */
        void getRoomSetupData(Callback<List<AllLocationsQuery.AllLocation>> callback,
                              Context context);

        /**
         * Gets current setup page.
         *
         * @return the current setup page
         */
        SetupPage getCurrentSetupPage();

        /**
         * Sets current setup page.
         *
         * @param page the page
         */
        void setCurrentSetupPage(SetupPage page);

        /**
         * Gets selected country index.
         *
         * @return the selected country index
         */
        int getSelectedCountryIndex();

        /**
         * Save selected country index.
         *
         * @param index the index
         */
        void saveSelectedCountryIndex(int index);

        /**
         * Gets selected building index.
         *
         * @return the selected building index
         */
        int getSelectedBuildingIndex();

        /**
         * Save selected building index.
         *
         * @param index the index
         */
        void saveSelectedBuildingIndex(int index);

        /**
         * Gets selected floor index.
         *
         * @return the selected floor index
         */
        int getSelectedFloorIndex();

        /**
         * Save selected floor index.
         *
         * @param index the index
         */
        void saveSelectedFloorIndex(int index);


        /**
         * The interface Callback.
         *
         * @param <T> the type parameter
         */
        interface Callback<T> {
            /**
             * On data loaded success.
             *
             * @param data the data
             */
            void onDataLoadedSuccess(T data);

            /**
             * On data loaded failure.
             */
            void onDataLoadedFailure();
        }
    }
}



