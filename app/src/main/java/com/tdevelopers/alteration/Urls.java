package com.tdevelopers.alteration;

/**
 * Created by ADMIN on 12-Dec-17.
 */


import android.util.Log;
import com.tdevelopers.alteration.android.helpers.Logger;

public class Urls {
    private static String a = "https://api.instamojo.com/";

    public Urls() {
    }

    public static String getOrderCreateUrl() {
        return a + "v2/gateway/orders/";
    }

    public static String getDefaultRedirectUrl() {
        return a + "integrations/android/redirect/";
    }

    public static String getBaseUrl() {
        return a;
    }

    public static void setBaseUrl(String baseUrl) {
        if((baseUrl = baseUrl) == null || baseUrl.trim().isEmpty()) {
            baseUrl = "https://api.instamojo.com/";
        }

        if(!baseUrl.endsWith("/")) {
            baseUrl = baseUrl + "/";
        }

        if(baseUrl.contains("test")) {
            Log.d("Urls", "Using a test base url. Use https://api.instamojo.com/ for production");
        }

        a = baseUrl;
        Logger.logDebug("Urls", "Base URL - " + a);
    }

    public static String getOrderFetchURL(String orderID) {
        return a + "v2/gateway/orders/" + orderID + "/payment-options/";
    }
}
