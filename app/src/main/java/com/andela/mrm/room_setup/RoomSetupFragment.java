package com.andela.mrm.room_setup;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.andela.mrm.AllLocationsQuery;
import com.andela.mrm.R;
import com.andela.mrm.room_booking.room_availability.views.RoomAvailabilityActivity;
import com.andela.mrm.room_setup.util.IOnBackPressed;
import com.andela.mrm.service.MyApolloClient;
import com.andela.mrm.util.NetworkConnectivityChecker;

import java.util.List;

/**
 * The type Room setup fragment.
 */
public class RoomSetupFragment extends Fragment implements RoomSetupContract.View, IOnBackPressed {


    private static final int GV_PLACEHOLDER_SPAN_COUNT = 1;
    /**
     * Contract's Action Listener.
     * Implemented by a presenter.
     */
    RoomSetupContract.ActionsListener mListener;
    /**
     * The M item listener.
     */
    ItemListener mItemListener = new ItemListener() {
        @Override
        public void onItemClick(int clickedItemPosition) {
            mListener.openNextSetupUi(clickedItemPosition);
        }
    };
    /**
     * The M progress dialog.
     */
    ProgressDialog mProgressDialog;
    /**
     * The M recycler view.
     */
    RecyclerView mRecyclerView;
    /**
     * The M room setup adapter.
     */
    RoomSetupAdapter mRoomSetupAdapter;
    /**
     * The M grid layout manager.
     */
    GridLayoutManager mGridLayoutManager;
    /**
     * The M selection set title.
     */
    TextView mSelectionSetTitle;
    /**
     * The M empty selection set text.
     */
    TextView mEmptySelectionSetText;
    private Callbacks mCallbacks;

    /**
     * New instance room setup fragment.
     *
     * @return the room setup fragment
     */
    public static RoomSetupFragment newInstance() {
        Bundle args = new Bundle();
        RoomSetupFragment fragment = new RoomSetupFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mCallbacks = (Callbacks) context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mProgressDialog = new ProgressDialog(getContext());
        mProgressDialog.setMessage("Loading... Please wait!!!");
        mProgressDialog.isIndeterminate();

        mRoomSetupAdapter = new RoomSetupAdapter(mItemListener);
        mGridLayoutManager = new GridLayoutManager(getContext(), GV_PLACEHOLDER_SPAN_COUNT);

        mListener = new RoomSetupPresenter(this, new SetupDataRepository(MyApolloClient.INSTANCE));
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_room_setup, container, false);

        mSelectionSetTitle = view.findViewById(R.id.text_room_setup_selection_set_title);
        mEmptySelectionSetText = view.findViewById(R.id.text_empty_selection_set);

        mRecyclerView = view.findViewById(R.id.recyclerview_setup_selection_list);
        mRecyclerView.setLayoutManager(mGridLayoutManager);
        mRecyclerView.setAdapter(mRoomSetupAdapter);

        mListener.loadSetupData();

        return view;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mCallbacks = null;
    }

    @Override
    public void showProgressDialog(final boolean isLoadingData) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (isLoadingData) {
                    mProgressDialog.show();
                    mRecyclerView.setVisibility(View.GONE);
                } else {
                    mProgressDialog.dismiss();
                    mRecyclerView.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    @Override
    public void showLocations(final List<AllLocationsQuery.AllLocation> locations) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mGridLayoutManager.setSpanCount(locations.size());
                mRoomSetupAdapter.setLocations(locations);
            }
        });
    }

    @Override
    public void showBuildings(final List<AllLocationsQuery.Block> blocks) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mGridLayoutManager.setSpanCount(blocks.size());
                mRoomSetupAdapter.setBlocks(blocks);
            }
        });
    }

    @Override
    public void showFloors(final List<AllLocationsQuery.Floor> floors) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mGridLayoutManager.setSpanCount(floors.size());
                mRoomSetupAdapter.setFloors(floors);
            }
        });
    }

    @Override
    public void showRooms(final List<AllLocationsQuery.Room> rooms) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mGridLayoutManager.setSpanCount(rooms.size());
                mRoomSetupAdapter.setRooms(rooms);
            }
        });
    }

    @Override
    public void showCurrentSetupPageTitle(final String title) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mSelectionSetTitle.setText(title);
            }
        });
    }

    @Override
    public void showNetworkErrorSnack(String errorMessage) {
        Snackbar.make(getView(), errorMessage, Snackbar.LENGTH_INDEFINITE)
                .setAction(R.string.snackbar_retry, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mListener.loadSetupData();
                    }
                })
                .show();
    }


    @Override
    public boolean networkIsAvailable() {
        return NetworkConnectivityChecker.isDeviceOnline(getContext());
    }

    @Override
    public void showEmptySelectionSetText(final String text) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mEmptySelectionSetText.setText(text);
                mEmptySelectionSetText.setVisibility(View.VISIBLE);
                mRecyclerView.setVisibility(View.GONE);
            }
        });
    }

    @Override
    public void showNextSetupUi() {
        mCallbacks.onClickSetupItem();
    }

    @Override
    public void showNextActivity() {
        Intent intent = new Intent(getContext(), RoomAvailabilityActivity.class);
        startActivity(intent);
    }

    @Override
    public boolean onBackPressed() {
        return mListener.onBackPressed();
    }

    /**
     * Required interface for hosting activity.
     * Hosting activity must implement this. Used in controlling what the activity does.
     */
    public interface Callbacks {
        /**
         * On click setup item.
         */
        void onClickSetupItem();
    }

    /**
     * The interface Item listener.
     * Instance of this listener is used in an adapter as a callback when an item is clicked.
     * Implementation and initialization resides in this Fragment.
     */
    public interface ItemListener {
        /**
         * On item click.
         *
         * @param clickedItemPosition the clicked item position
         */
        void onItemClick(int clickedItemPosition);
    }
}
