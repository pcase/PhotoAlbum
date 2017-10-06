package com.azurehorsecreations.photoalbum.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * NetworkConnectivityChecker
 * Checks network connectivity.
 * Static class isConnected returns true if a network connection is detected.
 */

public class NetworkConnectivityChecker {
    public static boolean isConnected(Context context) {
        ConnectivityManager connMgr = (ConnectivityManager)
                context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnected();
    }
}
