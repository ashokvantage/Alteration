package com.tdevelopers.alteration.slider;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RelativeLayout;

import com.ramotion.paperonboarding.PaperOnboardingFragment;
import com.ramotion.paperonboarding.PaperOnboardingPage;
import com.ramotion.paperonboarding.listeners.PaperOnboardingOnChangeListener;
import com.ramotion.paperonboarding.listeners.PaperOnboardingOnRightOutListener;
import com.tdevelopers.alteration.NewLogin.NewLoginActivity;
import com.tdevelopers.alteration.R;

import java.util.ArrayList;

public class FragmentsActivity extends AppCompatActivity {

    private FragmentManager fragmentManager;
    RelativeLayout proceed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.fragments_activity_layout);
        fragmentManager = getSupportFragmentManager();
        proceed = (RelativeLayout) findViewById(R.id.proceed);
        final PaperOnboardingFragment onBoardingFragment = PaperOnboardingFragment.newInstance(getDataForOnboarding());

        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.fragment_container, onBoardingFragment);
        fragmentTransaction.commit();

        onBoardingFragment.setOnRightOutListener(new PaperOnboardingOnRightOutListener() {
            @Override
            public void onRightOut() {
//                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//                Fragment bf = new BlankFragment();
//                fragmentTransaction.replace(R.id.fragment_container, bf);
//                fragmentTransaction.commit();


            }
        });
        onBoardingFragment.setOnChangeListener(new PaperOnboardingOnChangeListener() {
            @Override
            public void onPageChanged(int oldElementIndex, int newElementIndex) {
//                startActivity(new Intent(FragmentsActivity.this, NewLoginActivity.class));
//                FragmentsActivity.this.finish();
                if (newElementIndex == 2) {
                    proceed.setVisibility(View.VISIBLE);
                } else {
                    proceed.setVisibility(View.GONE);
                }
                Log.d("dfs", "sdfd");
            }
        });
        proceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(FragmentsActivity.this, NewLoginActivity.class));
                FragmentsActivity.this.finish();
            }
        });
    }

    private ArrayList<PaperOnboardingPage> getDataForOnboarding() {
        // prepare data
        PaperOnboardingPage scr1 = new PaperOnboardingPage(getResources().getString(R.string.slide1), getResources().getString(R.string.subslide1),
                Color.parseColor("#668fb5"), R.mipmap.onboardingfirst, R.mipmap.sbottomfirst);
        PaperOnboardingPage scr2 = new PaperOnboardingPage(getResources().getString(R.string.slide2), getResources().getString(R.string.subslide2),
                Color.parseColor("#66b0b5"), R.mipmap.onboardingsecond, R.mipmap.sbottomsecond);
        PaperOnboardingPage scr3 = new PaperOnboardingPage(getResources().getString(R.string.slide3), getResources().getString(R.string.subslide3),
                Color.parseColor("#9c8fbd"), R.mipmap.onboardingthird, R.mipmap.sbottomthird);

        ArrayList<PaperOnboardingPage> elements = new ArrayList<>();
        elements.add(scr1);
        elements.add(scr2);
        elements.add(scr3);
        return elements;
    }
}
