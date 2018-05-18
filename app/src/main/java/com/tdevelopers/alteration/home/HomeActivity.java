package com.tdevelopers.alteration.home;

import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.tdevelopers.alteration.Cart.Checkout.CartItem;
import com.tdevelopers.alteration.Cart.Checkout.CheckoutResponse;
import com.tdevelopers.alteration.Cart.ClearCartResponse;
import com.tdevelopers.alteration.Cart.getCart.GetCartItemsResponse;
import com.tdevelopers.alteration.Helper;
import com.tdevelopers.alteration.Models.Objects.Image;
import com.tdevelopers.alteration.Models.Remote.ApiUtils;
import com.tdevelopers.alteration.Payment.PaymentGateWay.Responses.CheckOutActivity;
import com.tdevelopers.alteration.R;
import com.tdevelopers.alteration.myaccount.MyAccountActivity;
import com.tdevelopers.alteration.myaccount.MyRenew;

import java.io.File;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeActivity extends AppCompatActivity implements View.OnClickListener {


    RelativeLayout banner1, banner2, banner3, banner4;
    LinearLayout bv1, bv2;
    ImageView bottomBarImages1, bottomBarImages2;
    TextView bottomBarTexts1, bottomBarTexts2;
    boolean doubleBackToExitPressedOnce = false;
    TextView title, subtitle;
    ImageView cartIcon;
    TextView txtitemsquantity;
    int ItemQuantity = 0;
    Integer[] imageId = {R.mipmap.af, R.mipmap.bf, R.mipmap.cf, R.mipmap.df, R.mipmap.ef, R.mipmap.ff};
    String[] name = {"Lenin Orange & Green Checkered Mexican", "Jute Lemon Yellow & Green Dotted Cuban", "Linen Blue & Whote Floral Print Osaka", "Linen Green & Black Stripes Rhodesia", "Cotton Pink Seoul", "Viscose Olive Green Savannah"};
    String[] style = {"Mexican", "Cuban", "Osaka", "Rhodesia", "Seoul", "Savannah"};
    public static ArrayList<Bitmap> pics = new ArrayList<>();
    public static ArrayList<String> places = new ArrayList<>();
    public static Image cur_image;
    public static boolean checkout_page = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        adjustFontScale(getResources().getConfiguration());
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
        setContentView(R.layout.activity_home);

        title = (TextView) findViewById(R.id.txtprofilename);
        subtitle = (TextView) findViewById(R.id.txtexplore);
        cartIcon = (ImageView) findViewById(R.id.cartIcon);
        txtitemsquantity = (TextView) findViewById(R.id.txtitemsquantity);
        File path = getDatabasePath("MyDBName.db");
        if (path.exists()) {
        } else {
            ApiUtils.getAlterationService()
                    .clearCart(Helper.getLocalValue(HomeActivity.this, "userid"))
                    .enqueue(new Callback<ClearCartResponse>() {
                        @Override
                        public void onResponse(Call<ClearCartResponse> call, Response<ClearCartResponse> response) {
                            try {


                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }

                        @Override
                        public void onFailure(Call<ClearCartResponse> call, Throwable t) {
                            t.printStackTrace();
                        }
                    });

        }
        cartIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                /*startActivity(new Intent(HomeActivity.this, CartAct*/
                ApiUtils.getAlterationService()
                        .checkOut("user/checkout", Helper.getLocalValue(HomeActivity.this, "userid"))
                        .enqueue(new Callback<CheckoutResponse>() {
                            @Override
                            public void onResponse(Call<CheckoutResponse> call, Response<CheckoutResponse> response) {
                                try {

                                    if (response.body() != null && response.body().cartData != null) {

                                        Intent intent = new Intent(HomeActivity.this, CheckOutActivity.class);
                                        intent.putExtra("data", response.body().cartData);
                                        startActivity(intent);
                                    } else {
                                        Toast.makeText(HomeActivity.this, response.body().message, Toast.LENGTH_LONG).show();
                                    }

                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }

                            @Override
                            public void onFailure(Call<CheckoutResponse> call, Throwable t) {
                                t.printStackTrace();
                            }
                        });
            }
        });

        banner1 = (RelativeLayout) findViewById(R.id.banner1);
        banner2 = (RelativeLayout) findViewById(R.id.banner2);
        banner3 = (RelativeLayout) findViewById(R.id.banner3);
        banner4 = (RelativeLayout) findViewById(R.id.banner4);
        bv1 = (LinearLayout) findViewById(R.id.bv1);

        bv2 = (LinearLayout) findViewById(R.id.bv2);
        banner1.setOnClickListener(this);
        banner2.setOnClickListener(this);
        banner3.setOnClickListener(this);
        banner4.setOnClickListener(this);
        bv1.setOnClickListener(this);
        bv2.setOnClickListener(this);

        bottomBarImages1 = (ImageView) findViewById(R.id.i1);
        bottomBarTexts1 = (TextView) findViewById(R.id.t1);
        bottomBarImages2 = (ImageView) findViewById(R.id.i2);
        bottomBarTexts2 = (TextView) findViewById(R.id.t2);
        bottomBarImages1.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_house_black_silhouette_without_door_selected));
        bottomBarTexts1.setTextColor(ContextCompat.getColor(this, R.color.blue));
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.banner1:

                intent = new Intent(HomeActivity.this, NewAlterActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_top, R.anim.stay);
                break;
            case R.id.banner2:

                intent = new Intent(HomeActivity.this, NewRefreshActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_top, R.anim.stay);
                break;

            case R.id.banner3:
//                intent = new Intent(getContext(), NewRenewActivity.class);
                /*intent = new Intent(HomeActivity.this, NewRenewActivity.class);
//                intent.putExtra("id", "Renew");
                startActivity(intent);*/
//                overridePendingTransition(R.anim.slide_up, R.anim.stay);
                startActivity(new Intent(HomeActivity.this, MyRenew.class));
            case R.id.banner4:
                break;
            case R.id.bv1:
                break;
            case R.id.bv2:
                startActivity(new Intent(v.getContext(), MyAccountActivity.class));
                break;
        }
    }

    @Override
    public void onBackPressed() {
        FragmentManager manager = getSupportFragmentManager();
        if (manager.getBackStackEntryCount() > 0) {
            // If there are back-stack entries, leave the FragmentActivity
            // implementation take care of them.
            manager.popBackStack();

        } else {
            // Otherwise, ask user if he wants to leave :)
            if (doubleBackToExitPressedOnce) {
                super.onBackPressed();
                HomeActivity.this.finishAffinity();
                return;
            }

            this.doubleBackToExitPressedOnce = true;
            Toast.makeText(this, "Please click back again to exit", Toast.LENGTH_SHORT).show();

            new Handler().postDelayed(new Runnable() {

                @Override
                public void run() {
                    doubleBackToExitPressedOnce = false;
                }
            }, 2000);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        adjustFontScale(getResources().getConfiguration());
        ItemQuantity = 0;
        fetchCartItems();
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

    public void fetchCartItems() {
        ApiUtils.getAlterationService()
                .getCartItems("user/get-cart-items?user_id=" + Helper.getLocalValue(this, "userid"))
                .enqueue(new Callback<GetCartItemsResponse>() {
                    @Override
                    public void onResponse(Call<GetCartItemsResponse> call, Response<GetCartItemsResponse> response) {
                        try {

                            if (response.body() != null && response.body().data != null && response.body().data.cartItems != null && response.body().data.cartItems.size() != 0) {
                                {
                                    for (CartItem cartItem : response.body().data.cartItems) {
                                        ItemQuantity = ItemQuantity + cartItem.quantity;
                                    }
                                    txtitemsquantity.setText("" + ItemQuantity);
                                    txtitemsquantity.setVisibility(View.VISIBLE);
                                }
                            } else {

                                txtitemsquantity.setText("0");
                                ItemQuantity = 0;
                                txtitemsquantity.setVisibility(View.GONE);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(Call<GetCartItemsResponse> call, Throwable t) {
                        t.printStackTrace();
                    }
                });

    }
    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        // TODO Auto-generated method stub
        super.onWindowFocusChanged(hasFocus);
        updateSizeInfo();
    }

    private void updateSizeInfo() {
        int w = banner1.getWidth();

        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(w, w); // or set height to any fixed value you want
        banner1.setLayoutParams(lp);
        banner2.getLayoutParams().height = w;
        banner3.getLayoutParams().height = w;
        banner4.getLayoutParams().height = w;
    }
}
