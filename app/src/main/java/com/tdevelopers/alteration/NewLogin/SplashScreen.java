package com.tdevelopers.alteration.NewLogin;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;

import com.tdevelopers.alteration.Helper;
import com.tdevelopers.alteration.R;
import com.tdevelopers.alteration.slider.FragmentsActivity;

public class SplashScreen extends AppCompatActivity {
    Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash_screen);
        handler = new Handler();

        final Runnable r = new Runnable() {
            public void run() {
                boolean isLoggedIn = Helper.getLocalValue(SplashScreen.this, "userid") != null;
//                Toast.makeText(SplashScreen.this,""+isLoggedIn,Toast.LENGTH_LONG).show();
                if (isLoggedIn) {

                    startActivity(new Intent(SplashScreen.this, com.tdevelopers.alteration.home.HomeActivity.class));
                    finish();
                } else {
                    startActivity(new Intent(SplashScreen.this, FragmentsActivity.class));
                    SplashScreen.this.finish();
                }

            }
        };

        handler.postDelayed(r, 2000);
    }
}
