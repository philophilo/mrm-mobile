package com.andela.mrm.room_booking.room_information;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.andela.mrm.R;

/**
 * The type Room information activity.
 */
public class RoomInformationActivity extends AppCompatActivity {

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private ImageView closeButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room_information);

        viewPager = findViewById(R.id.container);
        closeButton = findViewById(R.id.layout_close_event_schedule);
        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        ViewPagerAdapter viewPagerAdapter =
                new ViewPagerAdapter(this, getSupportFragmentManager());

        viewPager.setAdapter(viewPagerAdapter);

        tabLayout = findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

    }

    /**
     * The type View pager adapter.
     */
    public static class ViewPagerAdapter extends FragmentPagerAdapter {

        private final Context mContext;

        /**
         * Instantiates a new View pager adapter.
         *
         * @param context the context
         * @param fm      the fm
         */
        public ViewPagerAdapter(Context context, FragmentManager fm) {
            super(fm);
            mContext = context;
        }

        @Override
        public Fragment getItem(int position) {
            if (position == 0) {
                return new RoomAmenitiesFragment();
            } else if (position == 1) {
                return new SimilarRoomFragment();
            }
            return null;
        }

        @Override
        public int getCount() {
            return 2;
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return mContext.getString(R.string.room_amenities);
                case 1:
                    return mContext.getString(R.string.similar_rooms);
                default:
                    return null;
            }
        }
    }

}



