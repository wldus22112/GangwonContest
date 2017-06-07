package com.example.admin.gangwon.StateCheck;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by admin on 2016-07-15.
 */
public class NetworkCheck {

    Context fContext;

    public NetworkCheck(Context context){
        this.fContext = context;
    }

    public void checking(){

        ConnectivityManager manager = (ConnectivityManager) fContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo mobile = manager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE); // 3G나 LTE등 데이터 네트워크에 연결된 상태
        NetworkInfo wifi = manager.getNetworkInfo(ConnectivityManager.TYPE_WIFI); // 와이파이에 연결된 상태

        if (wifi.isConnected()) { // 와이파이에 연결된 경우

        } else if (mobile.isConnected()) { // 데이터 네트워크에 연결된 경우

        } else { // 인터넷에 연결되지 않은 경우

        }

    }
}
