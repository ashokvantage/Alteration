package com.tdevelopers.alteration.Cart;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.tdevelopers.alteration.Adapters.CartAdapter;
import com.tdevelopers.alteration.Cart.Checkout.CheckoutResponse;
import com.tdevelopers.alteration.Helper;
import com.tdevelopers.alteration.Models.Remote.ApiUtils;
import com.tdevelopers.alteration.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CartActivity extends AppCompatActivity {

    RecyclerView cartRv;
    LinearLayout emptycart;
    ImageView back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        adjustFontScale(getResources().getConfiguration());
        setContentView(R.layout.activity_cart);
        emptycart = (LinearLayout)findViewById(R.id.emptycart);
        cartRv = (RecyclerView) findViewById(R.id.cartRv);
        cartRv.setLayoutManager(new LinearLayoutManager(this));
        back = (ImageView) findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        fetchCartItems();


    }


    public void fetchCartItems() {


/*
        ApiUtils.getAlterationService()
                .getCartItems("user/get-cart-items?user_id=" + Helper.getLocalValue(this, "userid"))
                .enqueue(new Callback<GetCartItemsResponse>() {
                    @Override
                    public void onResponse(Call<GetCartItemsResponse> call, Response<GetCartItemsResponse> response) {
                        try {

                            if (response.body() != null && response.body().data != null && response.body().data.cartItems != null && response.body().data.cartItems.size() != 0) {

                                cartRv.setAdapter(new CartAdapter(response.body().data.cartItems, "cart"));


                            } else {
                                emptycart.setVisibility(View.VISIBLE);
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
*/

        ApiUtils.getAlterationService()
                .checkOut("user/checkout", Helper.getLocalValue(this, "userid"))
                .enqueue(new Callback<CheckoutResponse>() {
                    @Override
                    public void onResponse(Call<CheckoutResponse> call, Response<CheckoutResponse> response) {
                        try {

                            if (response.body() != null && response.body().cartData != null && response.body().cartData.cartItems != null && response.body().cartData.cartItems.size() != 0) {

                                cartRv.setAdapter(new CartAdapter(response.body().cartData.cartItems, "cart"));


                            } else {
                                emptycart.setVisibility(View.VISIBLE);
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

    @Override
    protected void onResume() {
        super.onResume();
        adjustFontScale(getResources().getConfiguration());
    }

    public void adjustFontScale(Configuration configuration) {
        configuration.fontScale = (float) 0.86;
        DisplayMetrics metrics = getResources().getDisplayMetrics();
        WindowManager wm = (WindowManager) getSystemService(WINDOW_SERVICE);
        wm.getDefaultDisplay().getMetrics(metrics);
        metrics.scaledDensity = configuration.fontScale * metrics.density;
        getBaseContext().getResources().updateConfiguration(configuration, metrics);
    }
}
