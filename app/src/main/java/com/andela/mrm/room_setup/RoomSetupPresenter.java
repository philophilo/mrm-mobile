package com.andela.mrm.room_setup;

import com.andela.mrm.AllLocationsQuery;
import com.andela.mrm.room_setup.data.SetupPage;

import java.util.List;

/**
 * The type Room setup presenter.
 */
class RoomSetupPresenter implements RoomSetupContract.ActionsListener {

    /**
     * The constant NETWORK_UNAVAILABLE_TEXT.
     */
    public static final String NETWORK_UNAVAILABLE_TEXT = "No network found";
    /**
     * The constant SERVER_ERROR_TEXT.
     */
    public static final String SERVER_ERROR_TEXT = "Could not retrieve data";
    /**
     * The M view.
     */
    final RoomSetupContract.View mView;
    /**
     * The M data.
     */
    final RoomSetupContract.Data mData;


    /**
     * Instantiates a new Room setup presenter.
     *
     * @param view the view
     * @param data the data
     */
    RoomSetupPresenter(RoomSetupContract.View view, RoomSetupContract.Data data) {
        mView = view;
        mData = data;
    }

    @Override
    public void loadSetupData() {
        final SetupPage currentSetupPage = mData.getCurrentSetupPage();

        if (currentSetupPage == SetupPage.COUNTRY) {
            mView.showProgressDialog(true);
        }

        mData.getRoomSetupData(new RoomSetupContract.Data.Callback<List<AllLocationsQuery
                .AllLocation>>() {
            @Override
            public void onDataLoadedSuccess(List<AllLocationsQuery.AllLocation> data) {
                mView.showProgressDialog(false);
                mView.showCurrentSetupPageTitle(currentSetupPage.getPageHeaderText());
                // TODO: extract empty selection set text into variables or into current setup page
                switch (currentSetupPage) {
                    case COUNTRY:
                        if (data.isEmpty()) {
                            mView.showEmptySelectionSetText("Locations list is empty");
                            return;
                        }
                        mView.showLocations(data);
                        break;
                    case BUILDING:
                        List<AllLocationsQuery.Block> buildings =
                                data.get(mData.getSelectedCountryIndex()).blocks();
                        if (buildings.isEmpty()) {
                            mView.showEmptySelectionSetText(
                                    "There are no buildings under this country");
                            return;
                        }
                        mView.showBuildings(buildings);
                        break;
                    case FLOOR:
                        List<AllLocationsQuery.Floor> floors = data
                                .get(mData.getSelectedCountryIndex())
                                .blocks()
                                .get(mData.getSelectedBuildingIndex()).floors();
                        if (floors.isEmpty()) {
                            mView.showEmptySelectionSetText("There are no floors in this building");
                            return;
                        }
                        mView.showFloors(floors);
                        break;
                    case ROOM:
                        List<AllLocationsQuery.Room> rooms = data
                                .get(mData.getSelectedCountryIndex())
                                .blocks()
                                .get(mData.getSelectedBuildingIndex())
                                .floors().get(mData.getSelectedFloorIndex()).rooms();
                        if (rooms.isEmpty()) {
                            mView.showEmptySelectionSetText("There are no rooms on this floor");
                            return;
                        }
                        mView.showRooms(rooms);
                        break;
                    default:
                        break;
                }
            }

            @Override
            public void onDataLoadedFailure() {
                mView.showProgressDialog(false);
                if (!mView.networkIsAvailable()) {
                    mView.showNetworkErrorSnack(NETWORK_UNAVAILABLE_TEXT);
                } else {
                    mView.showNetworkErrorSnack(SERVER_ERROR_TEXT);
                }

            }
        }, mView.getContext());
    }

    @Override
    public void openNextSetupUi(int selectedItemPosition) {
        SetupPage currentSetupPage = mData.getCurrentSetupPage();
        switch (currentSetupPage) {
            case COUNTRY:
                mData.setCurrentSetupPage(SetupPage.BUILDING);
                mData.saveSelectedCountryIndex(selectedItemPosition);
                mView.showNextSetupUi();
                break;
            case BUILDING:
                mData.setCurrentSetupPage(SetupPage.FLOOR);
                mData.saveSelectedBuildingIndex(selectedItemPosition);
                mView.showNextSetupUi();
                break;
            case FLOOR:
                mData.setCurrentSetupPage(SetupPage.ROOM);
                mData.saveSelectedFloorIndex(selectedItemPosition);
                mView.showNextSetupUi();
                break;
            case ROOM:
                mView.showNextActivity();
                break;
            default:
                break;
        }
    }

    @Override
    public boolean onBackPressed() {
        SetupPage currentSetupPage = mData.getCurrentSetupPage();
        if (currentSetupPage == SetupPage.COUNTRY) {
            // go back to the previous activity
            return false;
        } else {
            // go back to the previous setup page
            mData.setCurrentSetupPage(SetupPage.values()[currentSetupPage.ordinal() - 1]);
            mView.showNextSetupUi();
            return true;
        }
    }
}
