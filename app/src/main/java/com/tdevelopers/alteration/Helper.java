package com.tdevelopers.alteration;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by st185188 on 11/5/17.
 */

public class Helper {

    public static void storeLocally(Context context, String key, String value) {
        try {

            SharedPreferences sharedPreferences = context.getSharedPreferences(context.getResources().getString(R.string.app_name), Context.MODE_PRIVATE);
            sharedPreferences.edit().putString(key, value).apply();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String getLocalValue(Context context, String key) {
        try {
            return context.getSharedPreferences(context.getResources().getString(R.string.app_name), Context.MODE_PRIVATE).getString(key, null);
        } catch (Exception e) {

            e.printStackTrace();
            return null;
        }
    }
}
