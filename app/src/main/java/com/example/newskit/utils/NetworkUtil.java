package com.example.newskit.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkCapabilities;
import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;

public class NetworkUtil {

    @RequiresApi(api = Build.VERSION_CODES.M)
    public static boolean isNetworkConnected(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkCapabilities networkCapabilities = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            networkCapabilities = cm.getNetworkCapabilities(cm.getActiveNetwork());
        }

        if(networkCapabilities == null) {
            Log.e("net work", "------->agagagag");
            return false;
        }
        return networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_VALIDATED);
    }

}
