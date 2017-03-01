package com.example.user.newsweats.Controllers;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by sasikiran on 21/2/17.
 * version 1.0
 */

//  checker for network availability
public class NetworkConnectionChecker {

    Context con;

//    get context constructor
    public NetworkConnectionChecker(Context context) {

        this.con=context;

    }

//    network checker
    public boolean isNetworkAvailable() {

        ConnectivityManager connectivityManager
                = (ConnectivityManager)con.getSystemService(con.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();

    }

}
