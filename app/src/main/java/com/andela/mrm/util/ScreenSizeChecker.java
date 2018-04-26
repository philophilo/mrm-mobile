package com.andela.mrm.util;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.WindowManager;

/**
 * ScreenSizeChecker class.
 */
public final class ScreenSizeChecker {

    /**
     * private constructor.
     * prevent instantiation
     */
    private ScreenSizeChecker() {

    }

    /**
     * Checks for the screen size in inches for an android device (7 or 10 inch).
     *
     * @param context  - the current application window context
     * @return boolean - true or false if screen size is seven or ten inch respectively
     */
    public static boolean getScreenSize(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        assert wm != null;

        DisplayMetrics dm = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(dm);
        double x = Math.pow(dm.widthPixels / dm.xdpi, 2);
        double y = Math.pow(dm.heightPixels / dm.ydpi, 2);
        int screenInches = (int) Math.ceil(Math.sqrt(x + y));
        return screenInches == 7;
    }
}
