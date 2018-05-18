package com.tdevelopers.alteration;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.JsonObject;
import com.tdevelopers.alteration.Cart.Checkout.CartData;
import com.tdevelopers.alteration.Cart.ClearCartResponse;
import com.tdevelopers.alteration.Models.Remote.ApiUtils;
import com.tdevelopers.alteration.UserPackage.Transaction;
import com.tdevelopers.alteration.UserPackage.userOrder.OrderHistory;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrderSuccessActivity extends AppCompatActivity {
    CartData cartData;
    TextView next;
    TextView orderNumberText;
    TextView name, txttotal, track, type;
    ImageView pic;
    String userId, orderId, ordered_date, address, measurement, transactionDetails, orderInfo, order_status, _id, product_info, product_id, price, quantity, grandTotal, productTotalPrice, supporting_images;
    String item_type, order_type, item_gender, item_id, item_image, item_name;
    Transaction transaction = null;
    String Order_Id;
    String desc = "";
    int alt_quan = 0, ref_quan = 0, total_count = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        adjustFontScale(getResources().getConfiguration());
        setContentView(R.layout.activity_order_success);
        next = (TextView) findViewById(R.id.next);
        txttotal = (TextView) findViewById(R.id.txttotal);
        track = (TextView) findViewById(R.id.track);
        type = (TextView) findViewById(R.id.type);
        name = (TextView) findViewById(R.id.name);
        pic = (ImageView) findViewById(R.id.pic);
        next.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onClick(View v) {
                startActivity(new Intent(OrderSuccessActivity.this, com.tdevelopers.alteration.home.HomeActivity.class));
                OrderSuccessActivity.this.finishAffinity();
            }
        });
        orderNumberText = (TextView) findViewById(R.id.orderText);
        /*toolbar.setNavigationIcon(ContextCompat.getDrawable(this, R.drawable.ic_arrow_back_black_24dp));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });*/

        if (getIntent().getExtras() != null && getIntent().getExtras().getSerializable("data") != null)
            cartData = (CartData) getIntent().getExtras().getSerializable("data");
        if (cartData != null) {

            name.setText("Alteration x " + cartData.cartItems.size());
            txttotal.setText("TOTAL ₹ " + getIntent().getExtras().get("pay"));
            Glide.with(OrderSuccessActivity.this).load(cartData.cartItems.get(0).productInfo.image)
                    .into(pic);
        }


        for (int i = 0; i < cartData.cartItems.size(); i++) {

            if (cartData.cartItems.get(i).type.equalsIgnoreCase("ALTER")) {
                alt_quan = alt_quan + cartData.cartItems.get(i).quantity;
            } else if (cartData.cartItems.get(i).type.equalsIgnoreCase("REFRESH")) {
                ref_quan = ref_quan + cartData.cartItems.get(i).quantity;
            }
        }
        if (alt_quan > 0 && ref_quan > 0) {
            type.setText("(Alter x " + alt_quan + ", Refresh x " + ref_quan + ")");
        } else if (alt_quan > 0) {
            type.setText("(Alter x " + alt_quan + ")");
        } else if (ref_quan > 0) {
            type.setText("(Refresh x " + ref_quan + ")");
        }

        for (int i = 0; i < cartData.cartItems.size(); i++) {
            if (i == 0) {
                desc = cartData.cartItems.get(i).productInfo.name;
            } else {
                desc = desc + ", " + cartData.cartItems.get(i).productInfo.name;
            }
        }
        total_count = alt_quan + ref_quan - 1;
        if (cartData.cartItems.size() > 1) {
            if (desc.substring(0, desc.indexOf(",")).length() > 13) {
                name.setText(desc.substring(0, 13) + ".. & " + total_count + " more");

            } else {
                name.setText(desc.substring(0, desc.indexOf(",") - 1) + ".. & " + total_count + " more");

            }
        } else {
            name.setText(desc);
        }


        if (getIntent().getExtras() != null && getIntent().getExtras().getSerializable("transdata") != null)

        {
            transaction = (Transaction) getIntent().getExtras().getSerializable("transdata");
//            orderNumberText.setText("Your payment for order No. #" + transaction.orderId + " has been succesfully completed. You will receive a call from us soon.");
            Order_Id = getIntent().getExtras().getString("order_id");
            orderNumberText.setText("Your payment for order No. #" + Order_Id + " has been succesfully completed. You will receive a call from us soon.");

            track.setOnClickListener(new View.OnClickListener() {
                @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
                @Override
                public void onClick(View v) {
                    Intent in = new Intent(OrderSuccessActivity.this, OrderHistory.class);
                    in.putExtra("orderId", Order_Id);
                    startActivity(in);
                }
            });
        }
        ApiUtils.getAlterationService()
                .clearCart(Helper.getLocalValue(OrderSuccessActivity.this, "userid"))
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
