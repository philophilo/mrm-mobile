package com.andela.mrm.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * The type Network connectivity checker.
 */
public final class NetworkConnectivityChecker {
    /**
     * Private constructor for NetworkConnectivityChecker class.
     */
    private NetworkConnectivityChecker() {
     // Private constructor
    }

    /**
     * Is device online boolean.
     *
     * @param context the context
     * @return the boolean
     */
    public static Boolean isDeviceOnline(Context context) {
        ConnectivityManager connMgr =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        assert connMgr != null;
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnected();
    }
}
