package com.tdevelopers.alteration.myaccount;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatEditText;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.CompoundButton;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.Toast;

import com.tdevelopers.alteration.Helper;
import com.tdevelopers.alteration.Models.Remote.ApiUtils;
import com.tdevelopers.alteration.R;
import com.tdevelopers.alteration.UserPackage.GetUserResponse;
import com.tdevelopers.alteration.UserPackage.UpdateUserResponse;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileActivity extends AppCompatActivity {
    ProgressBar progressBar;
    AppCompatEditText nameInput, mobileInput, emailInput;
    RadioButton rmale, rfemale;
    String Gender = "Male";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        adjustFontScale(getResources().getConfiguration());
//        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
//
//        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_profile);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        progressBar.setProgress(70);
        nameInput = (AppCompatEditText) findViewById(R.id.nameInput);
        mobileInput = (AppCompatEditText) findViewById(R.id.mobileInput);
        emailInput = (AppCompatEditText) findViewById(R.id.emailInput);
        rmale = (RadioButton) findViewById(R.id.rmale);
        rfemale = (RadioButton) findViewById(R.id.rfemale);

        rmale.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    Gender = "Male";
                } else {
                    Gender = "Female";
                }
            }
        });
        init();
        findViewById(R.id.save)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        try {
                            InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
                        } catch (Exception e) {

                        }

                        if (nameInput.getText().toString().trim().length() != 0 && mobileInput.getText().toString().trim().length() != 0 && emailInput.getText().toString().trim().length() != 0) {


                            HashMap<String, Object> map = new HashMap<>();
                            map.put("user_id", Helper.getLocalValue(ProfileActivity.this, "userid"));
                            map.put("name", nameInput.getText().toString());
                            map.put("email", emailInput.getText().toString());
                            map.put("gender", Gender);
                            map.put("phone", mobileInput.getText().toString());
                            ApiUtils.getAlterationService()
                                    .updateProfile(map)
                                    .enqueue(new Callback<UpdateUserResponse>() {
                                        @Override
                                        public void onResponse(Call<UpdateUserResponse> call, Response<UpdateUserResponse> response) {
                                            try {
                                                if (response.body() != null) {
                                                    Toast.makeText(ProfileActivity.this, "Successfully updated", Toast.LENGTH_SHORT).show();

                                                }
                                            } catch (Exception e) {
                                                e.printStackTrace();
                                            }

                                            finish();
                                        }

                                        @Override
                                        public void onFailure(Call<UpdateUserResponse> call, Throwable t) {

                                            t.printStackTrace();

                                            finish();
                                        }
                                    });

                        } else {
                            Toast.makeText(ProfileActivity.this, "Please enter valid details...", Toast.LENGTH_SHORT).show();
                        }

                    }
                });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.stay, R.anim.slide_down);
    }

    public void init() {
        ApiUtils.getAlterationService()
                .getUserProfile("user/get-profile?user_id=" + Helper.getLocalValue(ProfileActivity.this, "userid"))
                .enqueue(new Callback<GetUserResponse>() {
                    @Override
                    public void onResponse(Call<GetUserResponse> call, Response<GetUserResponse> response) {
                        try {
                            nameInput.setText(response.body().data.name);
//                            mobileInput.setText(response.body().data.m);
                            emailInput.setText(response.body().data.email);
                            if (response.body().data.gender.equalsIgnoreCase("male")) {
                                rmale.setChecked(true);
                                rfemale.setChecked(false);
                            } else {
                                rfemale.setChecked(true);
                                rmale.setChecked(false);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();

                        }
                    }

                    @Override
                    public void onFailure(Call<GetUserResponse> call, Throwable t) {
                        t.printStackTrace();

                    }
                });

    }
    @Override
    public void onResume() {
        super.onResume();
        adjustFontScale(getResources().getConfiguration());
    }
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
}
