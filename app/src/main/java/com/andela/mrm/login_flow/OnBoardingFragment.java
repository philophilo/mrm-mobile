package com.andela.mrm.login_flow;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.text.Html;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.andela.mrm.R;
import com.andela.mrm.adapter.ImageSliderAdapter;

/**
 * A {@link Fragment} subclass.
 * Controls the ViewPager slide functionality
 */
public class OnBoardingFragment extends Fragment {
    private LinearLayout mDotLayout;
    ViewPager mViewPager;
    ImageSliderAdapter mImageSliderAdapter;
    TextView[] mDots;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        mImageSliderAdapter = new ImageSliderAdapter();

        mViewPager = new ViewPager(getActivity());
        mViewPager = container.findViewById(R.id.view_pager_layout);

        mDotLayout = container.findViewById(R.id.dot_layout);

        mViewPager.setAdapter(mImageSliderAdapter);

        addDotsIndicators(0);
        mViewPager.addOnPageChangeListener(viewListener);

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_on_boarding, container, false);
    }

    /**
     * Adds dot indicators to ViewPager and regulates their appearance.
     *
     * @param position - current view position of ViewPager
     */
    void addDotsIndicators(int position) {
        mDots = new TextView[3];
        mDotLayout.removeAllViews();

        for (int i = 0; i < mDots.length; i++) {
            mDots[i] = new TextView(getActivity().getApplicationContext());
            mDots[i].setText(Html.fromHtml("&#8226;"));
            mDots[i].setTextSize(70);
            mDots[i].setLetterSpacing(0.2f);
            mDots[i].setTextColor(getResources().getColor(R.color.colorWhite));

            mDotLayout.addView(mDots[i]);
        }

        DisplayMetrics metrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(metrics);
        int heightPixels = metrics.heightPixels;

        if (mDots.length > 0) {
            if (heightPixels / metrics.density >= 720) {
                mDots[position].setTextSize(25);
                mDots[position].setPadding(22, 12, 22, -50);
            } else {
                mDots[position].setTextSize(20);
                mDots[position].setPadding(12, 14, 12, -34);
            }
            mDots[position].setBackgroundResource(R.drawable.ic_slide_button);
        }
    }

    ViewPager.OnPageChangeListener viewListener = new ViewPager.OnPageChangeListener() {

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            // This method is intentionally empty. Nothing is needed here for now.
        }

        @Override
        public void onPageSelected(int position) {

            addDotsIndicators(position);
        }

        @Override
        public void onPageScrollStateChanged(int state) {
            // This method is intentionally empty. Nothing is needed here for now.
        }
    };

}
