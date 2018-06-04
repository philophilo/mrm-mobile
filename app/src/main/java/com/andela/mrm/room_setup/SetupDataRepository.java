package com.andela.mrm.room_setup;

import android.content.Context;

import com.andela.mrm.AllLocationsQuery;
import com.andela.mrm.room_setup.data.SetupPage;
import com.andela.mrm.service.MyApolloClient;
import com.apollographql.apollo.ApolloCall;
import com.apollographql.apollo.api.Response;
import com.apollographql.apollo.exception.ApolloException;

import java.util.List;

import javax.annotation.Nonnull;

/**
 * The type Setup data repository.
 */
public class SetupDataRepository implements RoomSetupContract.Data {

    private final MyApolloClient mApolloClient;

    /**
     * Instantiates a new Setup data repository.
     *
     * @param apolloClient the apollo client
     */
    public SetupDataRepository(MyApolloClient apolloClient) {
        mApolloClient = apolloClient;
    }

    @Override
    public void getRoomSetupData(final Callback<List<AllLocationsQuery.AllLocation>> callback,
                                 Context context) {
        mApolloClient
                .getMyApolloClient(context)
                .query(AllLocationsQuery.builder().build())
                .enqueue(new ApolloCall.Callback<AllLocationsQuery.Data>() {
                    @Override
                    public void onResponse(@Nonnull Response<AllLocationsQuery.Data> response) {
                        callback.onDataLoadedSuccess(response.data().allLocations());
                    }

                    @Override
                    public void onFailure(@Nonnull ApolloException e) {
                        callback.onDataLoadedFailure();
                    }
                });
    }

    @Override
    public SetupPage getCurrentSetupPage() {
        return mApolloClient.getCurrentSetupPage();
    }

    @Override
    public void setCurrentSetupPage(SetupPage page) {
        mApolloClient.setCurrentSetupPage(page);
    }

    @Override
    public int getSelectedCountryIndex() {
        return mApolloClient.getSelectedCountryIndex();
    }

    @Override
    public void saveSelectedCountryIndex(int index) {
        mApolloClient.setSelectedCountryIndex(index);
    }

    @Override
    public int getSelectedBuildingIndex() {
        return mApolloClient.getSelectedBuildingIndex();
    }

    @Override
    public void saveSelectedBuildingIndex(int index) {
        mApolloClient.setSelectedBuildingIndex(index);
    }

    @Override
    public int getSelectedFloorIndex() {
        return mApolloClient.getSelectedFloorIndex();
    }

    @Override
    public void saveSelectedFloorIndex(int index) {
        mApolloClient.setSelectedFloorIndex(index);
    }
}
