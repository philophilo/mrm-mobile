package com.andela.mrm.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.andela.mrm.R;

/**
 * ImageSliderAdapter class.
 * Adds the three onBoarding views
 *
 */

public class ImageSliderAdapter extends PagerAdapter {

    private final int[] slideImages = {
            R.drawable.ic_meeting, R.drawable.ic_room_status, R.drawable.ic_rating
    };

    private final String[] slideHeaders = {
            "EASIER MEETINGS", "MEETING ROOM STATUS", "GIVE FEEDBACK"
    };

    private final String[] slideDesc = {
            "Meetings are now even more fun with the MRM App",
            "View meeting rooms status across the whole office space",
            "Give feedback on meeting rooms right from the meeting room without stepping out"
    };

    @Override
    public int getCount() {
        return slideHeaders.length;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {

        return view.equals(object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        container.getContext();
        LayoutInflater layoutInflater = (LayoutInflater) container.getContext()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        assert layoutInflater != null;

        View view = layoutInflater
                .inflate(R.layout.fragment_on_boarding, container, false);

        ImageView slideImage = view.findViewById(R.id.image_slide);
        TextView slideHeading = view.findViewById(R.id.text_header);
        TextView slideDescription = view.findViewById(R.id.text_description);

        slideImage.setImageResource(slideImages[position]);
        slideHeading.setText(slideHeaders[position]);
        slideDescription.setText(slideDesc[position]);

        container.addView(view);

        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }
}
