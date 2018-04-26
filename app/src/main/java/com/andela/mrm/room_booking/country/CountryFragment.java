package com.andela.mrm.room_booking.country;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.andela.mrm.AllLocationsQuery;
import com.andela.mrm.R;
import com.andela.mrm.presenter.CountryPresenter;
import com.andela.mrm.room_booking.building.BuildingActivity;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;

/**
 * Created by andeladeveloper on 06/04/2018.
 */

public class CountryFragment extends Fragment implements CountryPresenter.CountryView {
    CountryPresenter countryPresenter = new CountryPresenter(this);

    ProgressDialog progressDialog;

    public final int[] buttonIDs = {R.id.btn_kenya, R.id.btn_nigeria, R.id.btn_uganda};
    public final int[] textIDs = {R.id.text_kenya, R.id.text_nigeria, R.id.text_uganda};

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        queryApi();

        View view = inflater.inflate(R.layout.fragment_country, container, false);
        ImageButton imageButton = view.findViewById(R.id.btn_nigeria);

        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), BuildingActivity.class);
                startActivity(intent);
            }
        });

        return view;
    }

    /**
     * Queries graphQL api for list of endpoint data.
     */
    public void queryApi() {
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Loading... Please wait!!!");
        progressDialog.isIndeterminate();
        progressDialog.show();

        countryPresenter.getAllLocations();
    }

    @Override
    public void displayCountries(final List<AllLocationsQuery.AllLocation> mData) {

        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < mData.size(); i++) {
                    ImageButton countryButton;
                    TextView countryText;
                    countryButton = getView().findViewById(buttonIDs[i]);
                    countryText = getView().findViewById(textIDs[i]);

                    Glide.with(countryButton.getContext())
                            .load(mData.get(i).imageUrl())
                            .apply(new RequestOptions()
                                    .placeholder(R.drawable.flag_placeholder)
                            )
                            .into(countryButton);

                    countryText.setText(mData.get(i).name());
                }
            }
        });
        dismissDialog();
    }

    /**
     * Dismisses/closes any running progress dialogs after success or failed api queries.
     */
    @Override
    public void dismissDialog() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }
}
