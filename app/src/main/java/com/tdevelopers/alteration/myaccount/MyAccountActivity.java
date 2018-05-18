package com.tdevelopers.alteration.myaccount;

import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.app.AppCompatDialog;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.tdevelopers.alteration.Adapters.PreviousOrderAdapter;
import com.tdevelopers.alteration.Helper;
import com.tdevelopers.alteration.Models.Remote.ApiUtils;
import com.tdevelopers.alteration.MyApp;
import com.tdevelopers.alteration.Payment.PaymentGateWay.Responses.CheckOutActivity;
import com.tdevelopers.alteration.R;
import com.tdevelopers.alteration.UserPackage.GetUserResponse;
import com.tdevelopers.alteration.UserPackage.UpdateUserResponse;
import com.tdevelopers.alteration.UserPackage.userOrder.PreviousOrderResponce;
import com.tdevelopers.alteration.home.HomeActivity;
import com.tdevelopers.alteration.home.NewAlterActivity;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyAccountActivity extends AppCompatActivity implements View.OnClickListener, SwipeRefreshLayout.OnRefreshListener {

    RecyclerView previousItemsRv;

    RelativeLayout v0, v1, v2, v3;
    LinearLayout bv1, bv2;
    String userId, orderId, ordered_date, address, measurement, transactionDetails, orderInfo, order_status, _id, product_info, product_id, price, quantity, grandTotal, productTotalPrice, supporting_images, centralGST, stateGST, extra_charges, delivery_cutoff_price, delivery;
    String item_type, order_type, item_gender, item_id, item_image, item_name;
    TextView editprofile, txtprofilename;

    ImageView bottomBarImages1, bottomBarImages2, imgcheckuser;
    TextView bottomBarTexts1, bottomBarTexts2;
    BottomSheetBehavior bottomSheetBehavior;
    AppCompatEditText nameInput, mobileInput, emailInput;
    RadioButton rmale, rfemale;
    String Gender = "Male";
    private static final String emailPattern = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@" + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
    int bottomSheetBehaviorstate;
    SwipeRefreshLayout mItemsContainer;
    AppCompatDialog progressDialog;
    boolean swipe = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        adjustFontScale(getResources().getConfiguration());
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
        setContentView(R.layout.activity_my_account);
        previousItemsRv = (RecyclerView) findViewById(R.id.prevordersrv);
        previousItemsRv.setLayoutManager(new LinearLayoutManager(MyAccountActivity.this));
        previousItemsRv.setNestedScrollingEnabled(false);

        //  previousItemsRv.setAdapter(new PreviousItemsAdapter());
        bottomSheetBehavior = BottomSheetBehavior.from(findViewById(R.id.bootomsheetprofile));
        bottomSheetBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                bottomSheetBehaviorstate = newState;
                switch (newState) {
                    case BottomSheetBehavior.STATE_EXPANDED:
//                        overLay.setVisibility(View.VISIBLE);

                        break;
                    case BottomSheetBehavior.STATE_DRAGGING:
//                        overLay.setVisibility(View.VISIBLE);
                        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                        break;
                    case BottomSheetBehavior.STATE_COLLAPSED:
//                        overLay.setVisibility(View.GONE);
                        break;
                }

            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {

            }
        });
        findViewById(R.id.bootomsheetprofile).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        v0 = (RelativeLayout) findViewById(R.id.v0);

        v1 = (RelativeLayout) findViewById(R.id.v1);

        v2 = (RelativeLayout) findViewById(R.id.v2);
        v3 = (RelativeLayout) findViewById(R.id.v3);

        bv1 = (LinearLayout) findViewById(R.id.bv1);

        bv2 = (LinearLayout) findViewById(R.id.bv2);

        v0.setOnClickListener(this);
        v1.setOnClickListener(this);
        v2.setOnClickListener(this);
        v3.setOnClickListener(this);
        bv1.setOnClickListener(this);
        bv2.setOnClickListener(this);
        bottomBarImages1 = (ImageView) findViewById(R.id.i1);
        bottomBarTexts1 = (TextView) findViewById(R.id.t1);
        bottomBarImages2 = (ImageView) findViewById(R.id.i2);
        bottomBarTexts2 = (TextView) findViewById(R.id.t2);
        bottomBarImages2.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_round_account_button_with_user_inside_selected));
        bottomBarTexts2.setTextColor(ContextCompat.getColor(this, R.color.blue));
        editprofile = (TextView) findViewById(R.id.editprofile);
        txtprofilename = (TextView) findViewById(R.id.txtprofilename);
        imgcheckuser = (ImageView) findViewById(R.id.imgcheckuser);
        editprofile.setOnClickListener(this);
        mItemsContainer = (SwipeRefreshLayout) findViewById(R.id.container_items);
        mItemsContainer.setOnRefreshListener(this);
        progressDialog = new AppCompatDialog(MyAccountActivity.this);
        progressDialog.setCancelable(false);
        progressDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        progressDialog.setContentView(R.layout.progress_loading);
        progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        if (MyApp.getInstance().isConnectingToInternet()) {
            init();
        } else {
            Toast.makeText(MyAccountActivity.this, "No internet connection available.", Toast.LENGTH_LONG).show();
        }
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
                }
            }
        });
        rfemale.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    Gender = "Female";
                }
            }
        });
//        init();
        findViewById(R.id.save)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        try {
                            InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
                        } catch (Exception e) {

                        }


                        if (nameInput.getText().toString().trim().length() != 0 && mobileInput.getText().toString().trim().length() > 9 && emailInput.getText().toString().trim().length() != 0 && emailInput.getText().toString().matches(emailPattern)) {


                            HashMap<String, Object> map = new HashMap<>();
                            map.put("user_id", Helper.getLocalValue(MyAccountActivity.this, "userid"));
                            map.put("name", nameInput.getText().toString());
                            map.put("email", emailInput.getText().toString());
                            map.put("gender", Gender);
                            map.put("mobile", mobileInput.getText().toString());
                            ApiUtils.getAlterationService()
                                    .updateProfile(map)
                                    .enqueue(new Callback<UpdateUserResponse>() {
                                        @Override
                                        public void onResponse(Call<UpdateUserResponse> call, Response<UpdateUserResponse> response) {
                                            try {
                                                if (response.body() != null) {
                                                    Toast.makeText(MyAccountActivity.this, "Successfully updated", Toast.LENGTH_SHORT).show();
                                                    bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                                                    String chars = capitalize(response.body().data.name);
                                                    txtprofilename.setText(chars);
                                                    txtprofilename.setVisibility(View.VISIBLE);
                                                   /* imgcheckuser.setVisibility(View.VISIBLE);*/
                                                    nameInput.setText(response.body().data.name);
                                                    mobileInput.setText(response.body().data.mobile);
                                                    emailInput.setText(response.body().data.email);

                                                    if (response.body().data.gender.equalsIgnoreCase("male")) {
                                                        rmale.setChecked(true);
                                                        rfemale.setChecked(false);
                                                    } else {
                                                        rfemale.setChecked(true);
                                                        rmale.setChecked(false);
                                                    }
                                                    Helper.storeLocally(MyAccountActivity.this, "username", response.body().data.name);
                                                    Helper.storeLocally(MyAccountActivity.this, "useremail", response.body().data.email);
                                                }
                                            } catch (Exception e) {
                                                e.printStackTrace();
                                            }
                                        }

                                        @Override
                                        public void onFailure(Call<UpdateUserResponse> call, Throwable t) {

                                            t.printStackTrace();

                                        }
                                    });

                        } else {
                            Toast.makeText(MyAccountActivity.this, "Please enter valid details...", Toast.LENGTH_SHORT).show();
                        }

                    }
                });

    }

    public void getOrder() {
        if (progressDialog != null && !swipe)
            progressDialog.show();
        ApiUtils.getAlterationService()
                .getPreviousOrders("user/orders?user_id=" + Helper.getLocalValue(MyAccountActivity.this, "userid"))
                .enqueue(new Callback<PreviousOrderResponce>() {
                    @Override
                    public void onResponse(Call<PreviousOrderResponce> call, Response<PreviousOrderResponce> response) {

                        try {
                            if (response.isSuccessful()) {
                                if (response.body() != null) {
                                    if (response.body().data != null && response.body().data.size() != 0) {
                                        previousItemsRv.setAdapter(new PreviousOrderAdapter(response.body().data, MyAccountActivity.this));
                                    }
                                    progressDialog.dismiss();
                                    mItemsContainer.setRefreshing(false);
                                } else {
                                    Toast.makeText(MyAccountActivity.this, response.message(), Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                // parse the response body …
                                try {
                                    JSONObject jsonObject = new JSONObject(response.errorBody().string());
                                    Toast.makeText(MyAccountActivity.this, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();

                                } catch (Exception e) {
                                    e.getMessage();
                                }

                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        } finally {
                            progressDialog.dismiss();
                            mItemsContainer.setRefreshing(false);
                        }

                    }

                    @Override
                    public void onFailure(Call<PreviousOrderResponce> call, Throwable t) {
                        t.printStackTrace();
                        progressDialog.dismiss();
                        mItemsContainer.setRefreshing(false);
                    }
                });

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.v0:

                startActivity(new Intent(view.getContext(), AddressActivity.class));
                break;

            case R.id.v1:
                Intent i = new Intent(view.getContext(), MeasurementActivity.class);
                i.putExtra("fromPayment", false);
                startActivity(i);
                break;

            case R.id.v2:
                startActivity(new Intent(view.getContext(), ContactUs.class));
                break;
            case R.id.v3:
                startActivity(new Intent(view.getContext(), MyRenew.class));
                break;
            case R.id.bv1:

                startActivity(new Intent(view.getContext(), HomeActivity.class));
                MyAccountActivity.this.finish();
                break;
            case R.id.bv2:

                break;
            case R.id.editprofile:

//                startActivity(new Intent(view.getContext(), ProfileActivity.class));
//                overridePendingTransition(R.anim.slide_up, R.anim.stay);
                bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                break;
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
        adjustFontScale(getResources().getConfiguration());
        if (bottomSheetBehavior != null)
            bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
//        init();
    }

    public void init() {
        ApiUtils.getAlterationService()
                .getUserProfile("user/get-profile?user_id=" + Helper.getLocalValue(MyAccountActivity.this, "userid"))
                .enqueue(new Callback<GetUserResponse>() {
                    @Override
                    public void onResponse(Call<GetUserResponse> call, Response<GetUserResponse> response) {
                        try {
                            if (!response.body().message.equalsIgnoreCase("No userdeails found.")) {
                                String chars = capitalize(response.body().data.name);
                                txtprofilename.setText(chars);
                                txtprofilename.setVisibility(View.VISIBLE);
                               /* imgcheckuser.setVisibility(View.VISIBLE);*/
                                nameInput.setText(response.body().data.name);
                                if (response.body().data.phone != null) {
                                    mobileInput.setText(response.body().data.phone);
                                } else {
                                    mobileInput.setText(Helper.getLocalValue(MyAccountActivity.this, "userphone"));
                                }

                                emailInput.setText(response.body().data.email);
                                if (response.body().data.gender.equalsIgnoreCase("male")) {
                                    rmale.setChecked(true);
                                    rfemale.setChecked(false);
                                } else {
                                    rfemale.setChecked(true);
                                    rmale.setChecked(false);
                                }
                                Helper.storeLocally(MyAccountActivity.this, "username", response.body().data.name);
                            } else {
                                mobileInput.setText(Helper.getLocalValue(MyAccountActivity.this, "userphone"));
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                            Toast.makeText(MyAccountActivity.this, Helper.getLocalValue(MyAccountActivity.this, "userid"), Toast.LENGTH_LONG).show();

                        } finally {
                            getOrder();
                        }
                       /* if (infolist.size() > 0) {
                            previousItemsRv.setAdapter(new PreviousItemsAdapter(list));
                        }*/
                    }

                    @Override
                    public void onFailure(Call<GetUserResponse> call, Throwable t) {
                        t.printStackTrace();

                    }
                });

    }

    private String capitalize(String capString) {
        StringBuffer capBuffer = new StringBuffer();
        Matcher capMatcher = Pattern.compile("([a-z])([a-z]*)", Pattern.CASE_INSENSITIVE).matcher(capString);
        while (capMatcher.find()) {
            capMatcher.appendReplacement(capBuffer, capMatcher.group(1).toUpperCase() + capMatcher.group(2).toLowerCase());
        }

        return capMatcher.appendTail(capBuffer).toString();
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

    @Override
    public void onBackPressed() {
        if (bottomSheetBehaviorstate == 3) {
            bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
        } else {
            super.onBackPressed();
        }

    }

    @Override
    public void onRefresh() {

        if (MyApp.getInstance().isConnectingToInternet()) {
            swipe = true;

            init();
        } else {

            mItemsContainer.setRefreshing(false);
        }
    }
}
