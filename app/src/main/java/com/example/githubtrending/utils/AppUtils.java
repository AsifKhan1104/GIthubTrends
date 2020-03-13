package com.example.githubtrending.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

public class AppUtils {
    public static boolean isConnected(Context context) {
        NetworkInfo connection = ((ConnectivityManager) context.getSystemService("connectivity")).getActiveNetworkInfo();
        if (connection == null || !connection.isConnectedOrConnecting()) {
            return false;
        }
        return true;
    }

    public static void showToast(Context context, String msg) {
        Toast.makeText(context, msg, 0).show();
    }
}
