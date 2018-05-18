package com.tdevelopers.alteration;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.google.gson.Gson;
import com.tdevelopers.alteration.Adapters.CartAdapter;
import com.tdevelopers.alteration.Cart.Checkout.CartData;
import com.tdevelopers.alteration.Models.Remote.ApiUtils;
import com.tdevelopers.alteration.Payment.PaymentGateWay.Responses.CheckOutActivity;
import com.tdevelopers.alteration.Payment.PaymentGateWay.Responses.OrderSummaryActivity;
import com.tdevelopers.alteration.UserPackage.Transaction;
import com.tdevelopers.alteration.UserPackage.TransactionPackage.SaveTransactionResponse;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TransactionFailed extends AppCompatActivity {

    RecyclerView cartRv;
    TextView totalPrice;
    CartData cartData;
    TextView next, txtretry;
    TextView orderNumberText;
    Transaction transaction;
    MaterialDialog progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        adjustFontScale(getResources().getConfiguration());
        setContentView(R.layout.activity_transaction_failed);
        cartRv = (RecyclerView) findViewById(R.id.cartRv);
        orderNumberText = (TextView) findViewById(R.id.orderText);
        cartRv.setLayoutManager(new LinearLayoutManager(this));
        cartRv.setNestedScrollingEnabled(false);
        totalPrice = (TextView) findViewById(R.id.totalPrice);
        next = (TextView) findViewById(R.id.next);
        txtretry = (TextView) findViewById(R.id.txtretry);
        progress = new MaterialDialog.Builder(this)
                .title("Please wait...")
                .content(R.string.please_wait)
                .cancelable(false)
                .progress(true, 0)
                .progressIndeterminateStyle(true)
                .build();
        if (getIntent().getExtras() != null && getIntent().getExtras().getSerializable("data") != null)
            cartData = (CartData) getIntent().getExtras().getSerializable("data");
        if (cartData != null) {

            totalPrice.setText("Total ₹ " + getIntent().getExtras().get("pay"));
            cartRv.setAdapter(new CartAdapter(cartData.cartItems, "failed"));
        }

        if (getIntent().getExtras() != null && getIntent().getExtras().getSerializable("transdata") != null)

        {
            transaction = (Transaction) getIntent().getExtras().getSerializable("transdata");
            orderNumberText.setText("Your payment for order No. #" + transaction.orderId + " has been failed.Please retry using a different payment option.");
        }

        txtretry.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onClick(View v) {
//                startActivity(new Intent(TransactionFailed.this, CheckOutActivity.class));
//                OrderSummaryActivity.fromfailed = true;
                TransactionFailed.this.finish();
            }
        });
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progress.show();
                transaction = new Transaction(OrderSummaryActivity.OrderId, OrderSummaryActivity.TransactionID, OrderSummaryActivity.PaymentID, CheckOutActivity.totalPay, CheckOutActivity.selectedMeasurement, CheckOutActivity.selectedAddress, "COD");
                saveTransaction(transaction);
            }
        });

        /*toolbar.setNavigationIcon(ContextCompat.getDrawable(this, R.drawable.ic_arrow_back_black_24dp));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });*/


       /* ApiUtils.getAlterationService()
                .clearCart(Helper.getLocalValue(TransactionFailed.this, "userid"))
                .enqueue(new Callback<ClearCartResponse>() {
                    @Override
                    public void onResponse(Call<ClearCartResponse> call, Response<ClearCartResponse> response) {
                        try {

                            setTitle("Order Failed");


                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(Call<ClearCartResponse> call, Throwable t) {
                        t.printStackTrace();
                    }
                });*/
    }

    public void saveTransaction(final Transaction transaction) {


//        Toast.makeText(this, "saving transaction in our servers...", Toast.LENGTH_SHORT).show();

        HashMap<String, Object> map = new HashMap<>();
        map.put("user_id", Helper.getLocalValue(this, "userid"));
        map.put("cart_items", new Gson().toJson(cartData));
        //  map.put("transaction", "\"" + transaction.transactionId + "\"");
        map.put("transaction", new Gson().toJson(transaction));
        map.put("address_id", CheckOutActivity.selectedAddress.addressId);
        if (CheckOutActivity.selectedMeasurement != null)
            map.put("measurement_id", CheckOutActivity.selectedMeasurement.measurementId);
        map.put("otherMeasurementOption", CheckOutActivity.otherMeasurementOption);
        map.put("cod", true);
        map.put("remarks", getIntent().getStringExtra("suggestion"));

        ApiUtils.getAlterationService()
                .saveTransaction(map)
                .enqueue(new Callback<SaveTransactionResponse>() {
                    @Override
                    public void onResponse(Call<SaveTransactionResponse> call, Response<SaveTransactionResponse> response) {
                        try {


                            if (response.body() != null && response.body().data != null) {
                                Toast.makeText(TransactionFailed.this, response.body().message, Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(TransactionFailed.this, OrderSuccessActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                intent.putExtra("data", TransactionFailed.this.cartData);
                                intent.putExtra("transdata", transaction);
                                intent.putExtra("pay", CheckOutActivity.totalPay);
                                closeProgressDialog();
                                startActivity(intent);
                            } else {

                                closeProgressDialog();
                                Toast.makeText(TransactionFailed.this, "Something went wrong ! Try again !", Toast.LENGTH_SHORT).show();
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                            closeProgressDialog();
                        }
                    }

                    @Override
                    public void onFailure(Call<SaveTransactionResponse> call, Throwable t) {
                        t.printStackTrace();

                        closeProgressDialog();
                    }
                });

    }

    public void closeProgressDialog() {

        if (progress != null && progress.isShowing())
            progress.dismiss();
    }

    @Override
    protected void onResume() {
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
