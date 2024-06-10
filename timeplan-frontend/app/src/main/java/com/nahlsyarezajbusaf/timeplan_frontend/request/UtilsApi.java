package com.nahlsyarezajbusaf.timeplan_frontend.request;

import android.util.Log;

import java.net.InetAddress;

/**
 * Connects the backend to frontend over an internet or by localhost
 *
 */
public class UtilsApi {

    public static final String BASE_URL_API = "http://10.0.2.2:5000/";
    public static final String DEVICE_URL_API = "http://192.168.18.6:5000/";

    public static BaseApiService getApiService() {
        try {
            InetAddress CURRENT_IP_ADDRESS = InetAddress.getLocalHost();
            Log.i("CECEP", CURRENT_IP_ADDRESS.getHostAddress());
        } catch (Exception e) {
            e.printStackTrace();
        }

        return
                RetrofitClient.getClient(DEVICE_URL_API).create(BaseApiService.class);
    }
}
