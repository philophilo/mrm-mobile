package com.andela.mrm.room_setup;

import android.content.Context;

import com.andela.mrm.AllLocationsQuery;
import com.andela.mrm.room_setup.data.SetupPage;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.List;

/**
 * The type Room setup presenter test.
 */
@RunWith(MockitoJUnitRunner.class)
public class RoomSetupPresenterTest {

    @Mock
    private RoomSetupContract.Data mRoomSetupData;

    @Mock
    private RoomSetupContract.View mRoomSetupView;

    @Captor
    private ArgumentCaptor<RoomSetupContract.Data.Callback<List<AllLocationsQuery.AllLocation>>>
            mCallbackArgumentCaptor;

    @Captor
    private ArgumentCaptor<Context> mContextArgumentCaptor;

    private RoomSetupPresenter mRoomSetupPresenter;

    /**
     * Sets up.
     */
    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        mRoomSetupPresenter = new RoomSetupPresenter(mRoomSetupView, mRoomSetupData);
    }

    /**
     * Tear down.
     */
    @After
    public void tearDown() {
    }

    /**
     * Load setup data.
     */
    @Test
    public void loadSetupData() {
        mRoomSetupPresenter.loadSetupData();
        Mockito.verify(mRoomSetupData).getCurrentSetupPage();
    }

    /**
     * Load setup data calls view show progress dialog with countries setup page.
     */
    @Test
    public void loadSetupData_Calls_View_ShowProgressDialog_With_COUNTRIES_Setup_Page() {
        Mockito.when(mRoomSetupData.getCurrentSetupPage())
                .thenReturn(SetupPage.COUNTRY);
        mRoomSetupPresenter.loadSetupData();
        Mockito.verify(mRoomSetupView).showProgressDialog(true);
    }


    /**
     * Load setup data calls expected view methods on failed data load with no network.
     */
    @Test
    public void loadSetupData_Calls_Expected_View_Methods_On_Failed_Data_Load_With_No_Network() {
        // stubs
        Mockito.when(mRoomSetupView.networkIsAvailable()).thenReturn(false);


        mRoomSetupPresenter.loadSetupData();
        Mockito.verify(mRoomSetupData).getRoomSetupData(mCallbackArgumentCaptor.capture(),
                mContextArgumentCaptor.capture());
        mCallbackArgumentCaptor.getValue().onDataLoadedFailure();

        Mockito.verify(mRoomSetupView).showProgressDialog(false);
        Mockito.verify(mRoomSetupView)
                .showNetworkErrorSnack(RoomSetupPresenter.NETWORK_UNAVAILABLE_TEXT);

    }

    /**
     * Load setup data calls expected view methods on failed data load with network.
     */
    @Test
    public void loadSetupDataCallsExpectedViewMethodsOnFailedDataLoadWithNetwork() {
        // stubs
        Mockito.when(mRoomSetupView.networkIsAvailable()).thenReturn(true);


        mRoomSetupPresenter.loadSetupData();
        Mockito.verify(mRoomSetupData).getRoomSetupData(mCallbackArgumentCaptor.capture(),
                mContextArgumentCaptor.capture());
        mCallbackArgumentCaptor.getValue().onDataLoadedFailure();

        Mockito.verify(mRoomSetupView).showProgressDialog(false);
        Mockito.verify(mRoomSetupView)
                .showNetworkErrorSnack(RoomSetupPresenter.SERVER_ERROR_TEXT);

    }


    /**
     * Sets the expected setup page with buildings as current setup page.
     */
    @Test
    public void setsTheExpectedSetupPageWithBuildingsAsCurrentSetupPage() {
        // stubs
        final int selectedItemPosition = 1;
        Mockito.when(mRoomSetupData.getCurrentSetupPage())
                .thenReturn(SetupPage.BUILDING);

        mRoomSetupPresenter.openNextSetupUi(selectedItemPosition);

        Mockito.verify(mRoomSetupData).setCurrentSetupPage(SetupPage.FLOOR);
        Mockito.verify(mRoomSetupView).showNextSetupUi();
    }

    /**
     * Sets the expected setup page with floors as current setup page.
     */
    @Test
    public void setsTheExpectedSetupPageWithFloorsAsCurrentSetupPage() {
        // stubs
        final int selectedItemPosition = 1;
        Mockito.when(mRoomSetupData.getCurrentSetupPage())
                .thenReturn(SetupPage.FLOOR);

        mRoomSetupPresenter.openNextSetupUi(selectedItemPosition);

        Mockito.verify(mRoomSetupData).setCurrentSetupPage(SetupPage.ROOM);
        Mockito.verify(mRoomSetupView).showNextSetupUi();
    }

    /**
     * Open next setup ui calls show next activity with rooms as current page.
     */
    @Test
    public void openNextSetupUI_Calls_ShowNextActivity_WithRoomsAs_CurrentPage() {
        // stubs
        final int selectedItemPosition = 1;
        Mockito.when(mRoomSetupData.getCurrentSetupPage())
                .thenReturn(SetupPage.ROOM);

        mRoomSetupPresenter.openNextSetupUi(selectedItemPosition);
        Mockito.verify(mRoomSetupView).showNextActivity();
    }
}
