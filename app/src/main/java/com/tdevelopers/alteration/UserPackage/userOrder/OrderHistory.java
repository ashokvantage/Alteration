package com.tdevelopers.alteration.UserPackage.userOrder;

import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.tdevelopers.alteration.Helper;
import com.tdevelopers.alteration.Models.Remote.ApiUtils;
import com.tdevelopers.alteration.MyApp;
import com.tdevelopers.alteration.R;
import com.tdevelopers.alteration.UserPackage.AddressPackage.Address;
import com.tdevelopers.alteration.myaccount.ContactUs;
import com.tdevelopers.alteration.Adapters.PreviousOrderHistoryAdapter;
import com.tdevelopers.alteration.myaccount.MyAccountActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrderHistory extends AppCompatActivity {
    RecyclerView orderhistoryRv;
    TextView txttotalprice, txtgstprice,txtgstmessage, txttotalpricewithgst, txttotalitem, deliveryPrice;
    TextView txtorderno, txtorderdate, txtmeasurment, txtpayment, txtfulladdress, txtaddressname, txthelp, expertPrice, txtstatus;
    ImageView back;
    RelativeLayout expertPriceLayout, deliveryLayout;
    AppCompatDialog progressDialog;
    OrderData data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        adjustFontScale(getResources().getConfiguration());
        setContentView(R.layout.order_history);
        orderhistoryRv = (RecyclerView) findViewById(R.id.orderhistoryRv);
        orderhistoryRv.setLayoutManager(new LinearLayoutManager(this));
        orderhistoryRv.setNestedScrollingEnabled(false);
        txttotalprice = (TextView) findViewById(R.id.txttotalprice);
        txtgstprice = (TextView) findViewById(R.id.txtgstprice);
        txtgstmessage=(TextView)findViewById(R.id.txtgstmessage);
        txttotalpricewithgst = (TextView) findViewById(R.id.txttotalpricewithgst);
        txttotalitem = (TextView) findViewById(R.id.txttotalitem);
        deliveryPrice = (TextView) findViewById(R.id.deliveryPrice);
        txtorderno = (TextView) findViewById(R.id.txtorderno);
        txtorderdate = (TextView) findViewById(R.id.txtorderdate);
        txtmeasurment = (TextView) findViewById(R.id.txtmeasurment);
        txtpayment = (TextView) findViewById(R.id.txtpayment);
        txtaddressname = (TextView) findViewById(R.id.txtaddressname);
        txtfulladdress = (TextView) findViewById(R.id.txtfulladdress);
        txthelp = (TextView) findViewById(R.id.txthelp);
        expertPrice = (TextView) findViewById(R.id.expertPrice);
        txtstatus = (TextView) findViewById(R.id.txtstatus);
        expertPriceLayout = (RelativeLayout) findViewById(R.id.expertPriceLayout);
        deliveryLayout = (RelativeLayout) findViewById(R.id.deliveryLayout);
        back = (ImageView) findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        progressDialog = new AppCompatDialog(OrderHistory.this);
        progressDialog.setCancelable(false);
        progressDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        progressDialog.setContentView(R.layout.progress_loading);
        progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        if (MyApp.getInstance().isConnectingToInternet()) {
            getOrder();
        } else {
            Toast.makeText(OrderHistory.this, "No internet connection available.", Toast.LENGTH_LONG).show();
        }
        txthelp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(OrderHistory.this, ContactUs.class));
            }
        });

    }

    public void getOrder() {
        if (progressDialog != null)
            progressDialog.show();
        ApiUtils.getAlterationService()
                .getPreviousOrders("user/orders?user_id=" + Helper.getLocalValue(OrderHistory.this, "userid") + "&order_id=" + getIntent().getStringExtra("orderId"))
                .enqueue(new Callback<PreviousOrderResponce>() {
                    @Override
                    public void onResponse(Call<PreviousOrderResponce> call, Response<PreviousOrderResponce> response) {
                        try {

                            if (response.body() != null) {
                                if (response.body().data != null) {


                                    if (response.body().data != null && response.body().data.size() != 0) {
                                        data = response.body().data.get(0);

                                        txtstatus.setText(data.order_status);
                                        txtorderno.setText("ORDER #" + data.order_id);
                                        txtorderdate.setText(data.ordered_date);
                                        txttotalprice.setText("₹ " + String.format("%.2f", data.order_info.productTotalPrice));
                                        txttotalpricewithgst.setText("₹ " + String.format("%.2f", data.order_info.grandTotal));
                                        txtgstprice.setText("₹ " + String.format("%.2f", (data.order_info.grandTotal - data.order_info.productTotalPrice)));
                                        txtgstmessage.setText("Tax (" + (data.order_info.stateGST + data.order_info.centralGST) + "% GST) ");
                                        /*txttotalitem.setText("Alteration x " +data. in.getIntExtra("totalquantity", 0));
*/
                                        try {
                                            Address address = data.transaction_details.address;
                                            txtaddressname.setText(address.addressName);
                                            txtfulladdress.setText(address.flatNo + ", " + address.areaLocality + ", " + address.city + ", " + address.state + "," + address.pincode);
                                            if (!data.cod) {
                                                txtpayment.setText("Online");
                                            } else {
                                                txtpayment.setText("Cash on Pickup");
                                            }
                                            if (data.otherMeasurementOption == 2) {
                                                txtmeasurment.setText(data.transaction_details.measurements.measurements.name);
                                                if (data.order_info.grandTotal < 2000) {
                                                    deliveryLayout.setVisibility(View.VISIBLE);
                                                    deliveryPrice.setText("₹ " + data.order_info.extra_charges.delivery_cutoff_price);
                                                    txttotalpricewithgst.setText("₹ " + String.format("%.2f", (data.order_info.grandTotal + data.order_info.extra_charges.delivery_cutoff_price)));
                                                } else {
                                                    deliveryLayout.setVisibility(View.GONE);
                                                    deliveryPrice.setText("");
                                                    txttotalpricewithgst.setText("₹ " + String.format("%.2f", data.order_info.grandTotal));

                                                }
                                            } else if (data.otherMeasurementOption == 0) {

                                                txtmeasurment.setText("Pickup Sample");

                                                if (data.order_info.grandTotal < 2000) {
                                                    deliveryLayout.setVisibility(View.VISIBLE);
                                                    deliveryPrice.setText("₹ " + data.order_info.extra_charges.delivery_cutoff_price);
                                                    txttotalpricewithgst.setText("₹ " + String.format("%.2f", (data.order_info.grandTotal + data.order_info.extra_charges.delivery_cutoff_price)));
                                                } else {
                                                    deliveryLayout.setVisibility(View.GONE);
                                                    deliveryPrice.setText("");
                                                    txttotalpricewithgst.setText("₹ " + String.format("%.2f", data.order_info.grandTotal));
                                                }

                                            } else {
                                                txtmeasurment.setText("Book an Expert");
                                                expertPriceLayout.setVisibility(View.VISIBLE);
                                                expertPrice.setText("₹ " + data.order_info.extra_charges.delivery);
                                                if ((data.order_info.grandTotal + data.order_info.extra_charges.delivery) < 2000) {
                                                    deliveryLayout.setVisibility(View.VISIBLE);
                                                    deliveryPrice.setText("₹ " + data.order_info.extra_charges.delivery_cutoff_price);
                                                    txttotalpricewithgst.setText("₹ " + String.format("%.2f", (data.order_info.grandTotal + data.order_info.extra_charges.delivery + data.order_info.extra_charges.delivery_cutoff_price)));

                                                } else {
                                                    deliveryLayout.setVisibility(View.GONE);
                                                    deliveryPrice.setText("");
                                                    txttotalpricewithgst.setText("₹ " + String.format("%.2f", (data.order_info.grandTotal + data.order_info.extra_charges.delivery)));

                                                }
                                            }


                                        } catch (Exception e) {

                                        }
                                        orderhistoryRv.setAdapter(new PreviousOrderHistoryAdapter(response.body().data.get(0).order_info.cart_items, OrderHistory.this));

                                    }

                                }
                                progressDialog.dismiss();
                            } else {
                                Toast.makeText(OrderHistory.this, response.message(), Toast.LENGTH_SHORT).show();
                                progressDialog.dismiss();
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                            progressDialog.dismiss();
                        }

                    }

                    @Override
                    public void onFailure(Call<PreviousOrderResponce> call, Throwable t) {
                        t.printStackTrace();
                        progressDialog.dismiss();
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
