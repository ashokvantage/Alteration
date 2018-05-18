package com.tdevelopers.alteration;

import android.app.Application;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.multidex.MultiDex;

import com.tdevelopers.alteration.android.Instamojo;
import com.tdevelopers.alteration.weather.DiscreteScrollViewOptions;

/**
 * Created by ADMIN on 30-Nov-17.
 */

public class MyApp extends Application {
    public static MyApp connection;
    public MyApp() {
    }

    public void onCreate() {
        super.onCreate();
//        adjustFontScale(getResources().getConfiguration());
        Instamojo.initialize(this);
        connection = this;
        DiscreteScrollViewOptions.init(this);
//        TypefaceUtil.overrideFont(getApplicationContext(), "SERIF", "fonts/AvenirLTStd-Medium.otf");
    }
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }
    public static synchronized MyApp getInstance() {
        return connection;
    }
    public boolean isConnectingToInternet(){
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo netInfo = cm.getActiveNetworkInfo();

        if (netInfo != null && netInfo.isConnectedOrConnecting()) {

            return true;
        }

        return false;
    }
/*
    public void adjustFontScale(Configuration configuration) {
//        if (configuration.fontScale > 1.30) {
            configuration.fontScale = (float) 0.86;
            DisplayMetrics metrics = getResources().getDisplayMetrics();
            WindowManager wm = (WindowManager) getSystemService(WINDOW_SERVICE);
            wm.getDefaultDisplay().getMetrics(metrics);
            metrics.scaledDensity = configuration.fontScale * metrics.density;
            getBaseContext().getResources().updateConfiguration(configuration, metrics);
//        }
    }
*/

}
