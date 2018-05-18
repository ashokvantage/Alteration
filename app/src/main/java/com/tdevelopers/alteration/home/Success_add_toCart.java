package com.tdevelopers.alteration.home;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.RelativeLayout;

import com.tdevelopers.alteration.Helper;
import com.tdevelopers.alteration.NewLogin.SplashScreen;
import com.tdevelopers.alteration.R;
import com.tdevelopers.alteration.slider.FragmentsActivity;

public class Success_add_toCart extends AppCompatActivity {
    Animation animationFadeOut;
    RelativeLayout relayout;
    Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_success_add_to_cart);
        animationFadeOut = AnimationUtils.loadAnimation(Success_add_toCart.this, R.anim.fadeout);
        relayout = (RelativeLayout) findViewById(R.id.relayout);
        relayout.startAnimation(animationFadeOut);
        handler = new Handler();
        final Runnable r = new Runnable() {
            public void run() {
                Success_add_toCart.this.finish();

            }
        };

        handler.postDelayed(r, 3000);


    }
}
