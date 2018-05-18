package com.tdevelopers.alteration.android;

import android.app.Application;

public class InstamojoApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Instamojo.initialize(this);
    }
}
