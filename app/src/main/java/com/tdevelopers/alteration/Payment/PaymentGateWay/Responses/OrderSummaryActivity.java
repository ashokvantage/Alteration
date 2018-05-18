package com.tdevelopers.alteration.Payment.PaymentGateWay.Responses;

import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.tdevelopers.alteration.Cart.Checkout.CartData;
import com.tdevelopers.alteration.Cart.Checkout.CartItem;
import com.tdevelopers.alteration.DBHelper;
import com.tdevelopers.alteration.Helper;
import com.tdevelopers.alteration.Models.Remote.ApiUtils;
import com.tdevelopers.alteration.OrderSuccessActivity;
import com.tdevelopers.alteration.Payment.PaymentGateWay.InstaMojoPaymentUtils;
import com.tdevelopers.alteration.R;
import com.tdevelopers.alteration.TransactionFailed;
import com.tdevelopers.alteration.UserPackage.AddressPackage.Address;
import com.tdevelopers.alteration.UserPackage.MeasurementsPackage.MeasurementObject;
import com.tdevelopers.alteration.UserPackage.Transaction;
import com.tdevelopers.alteration.UserPackage.TransactionPackage.SaveTransactionResponse;
import com.tdevelopers.alteration.android.activities.PaymentDetailsActivity;
import com.tdevelopers.alteration.android.callbacks.OrderRequestCallBack;
import com.tdevelopers.alteration.android.helpers.Constants;
import com.tdevelopers.alteration.android.models.Errors;
import com.tdevelopers.alteration.android.models.Order;
import com.tdevelopers.alteration.android.network.Request;
import com.tdevelopers.alteration.home.NewAlterActivity;

import java.util.HashMap;
import java.util.List;
import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrderSummaryActivity extends AppCompatActivity {
    public RecyclerView cartRv;
    public CartData cartData;
    TextView txttotalpricewithgst, tagGroupaddress, tagGroup;
    RelativeLayout continues;
    public Address selectedAddress;
    public MeasurementObject selectedMeasurement;
//    public MaterialDialog progress;
    private String accessToken = null;
    public double totalPay = 0.0;
    String orderID, transactionID, paymentID;
    public static String OrderId, TransactionID, PaymentID;
    public Transaction transaction;
    public int otherMeasurementOption = 2;
    CheckBox ch_onlinepayment, ch_codpayment;
    String Payment_Type = "Online";
    RelativeLayout codlayout, onlinelayout, rl_expert, deliveryLayout;
    ImageView back;
    TextView txt_order_note, deliveryPrice, txtgstmessage, txtgstprice, expertPrice;
    LinearLayout ll_order_note;
    DBHelper mydb;
    AppCompatDialog progressDialog;
    TextView tv_progress_message;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        adjustFontScale(getResources().getConfiguration());
        setContentView(R.layout.activity_order_summary);
        mydb = new DBHelper(this);
        continues = (RelativeLayout) findViewById(R.id.continues);
        txttotalpricewithgst = (TextView) findViewById(R.id.txttotalpricewithgst);
        tagGroupaddress = (TextView) findViewById(R.id.tagGroupaddress);
        tagGroup = (TextView) findViewById(R.id.tagGroup);
        codlayout = (RelativeLayout) findViewById(R.id.codlayout);
        onlinelayout = (RelativeLayout) findViewById(R.id.onlinelayout);
        rl_expert = (RelativeLayout) findViewById(R.id.rl_expert);
        deliveryLayout = (RelativeLayout) findViewById(R.id.deliveryLayout);
        txt_order_note = (TextView) findViewById(R.id.txt_order_note);
        deliveryPrice = (TextView) findViewById(R.id.deliveryPrice);
        txtgstmessage = (TextView) findViewById(R.id.txtgstmessage);
        txtgstprice = (TextView) findViewById(R.id.txtgstprice);
        expertPrice = (TextView) findViewById(R.id.expertPrice);
        ll_order_note = (LinearLayout) findViewById(R.id.ll_order_note);
        if (CheckOutActivity.otherMeasurementOption == 1) {
            rl_expert.setVisibility(View.VISIBLE);
        } else {
            rl_expert.setVisibility(View.GONE);
        }
        progressDialog = new AppCompatDialog(OrderSummaryActivity.this);
        progressDialog.setCancelable(false);
        progressDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        progressDialog.setContentView(R.layout.progress_loading);
        progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
//        progressDialog.show();

        tv_progress_message=progressDialog.findViewById(R.id.tv_progress_message);
        cartRv = (RecyclerView) findViewById(R.id.cartRv);
        cartRv.setLayoutManager(new LinearLayoutManager(this));
        cartRv.setNestedScrollingEnabled(false);
        back = (ImageView) findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        ch_onlinepayment = (CheckBox) findViewById(R.id.ch_onlinepayment);
        ch_codpayment = (CheckBox) findViewById(R.id.ch_codpayment);
        ch_onlinepayment.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    Payment_Type = "Online";
                    ch_onlinepayment.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_checked, 0);
                    ch_onlinepayment.setChecked(true);
                    ch_codpayment.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_checked_off, 0);
                    ch_codpayment.setChecked(false);
                } else {
                    Payment_Type = "COD";
                    ch_codpayment.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_checked, 0);
                    ch_codpayment.setChecked(true);
                    ch_onlinepayment.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_checked_off, 0);
                    ch_onlinepayment.setChecked(false);
                }
            }
        });
        ch_codpayment.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    Payment_Type = "COD";
                    ch_codpayment.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_checked, 0);
                    ch_codpayment.setChecked(true);
                    ch_onlinepayment.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_checked_off, 0);
                    ch_onlinepayment.setChecked(false);
                } else {
                    Payment_Type = "Online";
                    ch_onlinepayment.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_checked, 0);
                    ch_onlinepayment.setChecked(true);
                    ch_codpayment.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_checked_off, 0);
                    ch_codpayment.setChecked(false);
                }
            }
        });
        codlayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Payment_Type = "COD";
                ch_onlinepayment.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_checked_off, 0);
                ch_onlinepayment.setChecked(false);
                ch_codpayment.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_checked, 0);
                ch_codpayment.setChecked(true);
            }
        });
        onlinelayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Payment_Type = "Online";
                ch_onlinepayment.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_checked, 0);
                ch_onlinepayment.setChecked(true);
                ch_codpayment.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_checked_off, 0);
                ch_codpayment.setChecked(false);
            }
        });
        if (getIntent().getExtras() != null && getIntent().getExtras().getSerializable("data") != null) {
            cartData = (CartData) getIntent().getExtras().getSerializable("data");
            selectedMeasurement = (MeasurementObject) getIntent().getExtras().getSerializable("measurment");
            selectedAddress = (Address) getIntent().getExtras().getSerializable("address");
            otherMeasurementOption = getIntent().getIntExtra("othermeasurment", 0);
        }

        if (cartData != null) {
            txttotalpricewithgst.setText("₹ " + getIntent().getDoubleExtra("totalPay", 0.0));
            tagGroupaddress.setText(selectedAddress.flatNo + ", " + selectedAddress.areaLocality + ", " + selectedAddress.city + ", " + selectedAddress.state + ", " + selectedAddress.pincode);
            if (otherMeasurementOption == 0) {
                tagGroup.setText("Pickup Sample");
                if (cartData.grandTotal < 2000) {
                    deliveryLayout.setVisibility(View.VISIBLE);
                    deliveryPrice.setText("₹ " + cartData.extra_charges.delivery_cutoff_price);
                } else {
                    deliveryLayout.setVisibility(View.GONE);
                    deliveryPrice.setText("");
                }
            } else if (otherMeasurementOption == 1) {
                tagGroup.setText("Expert");
                expertPrice.setText("₹ " + cartData.extra_charges.delivery);
                if ((cartData.grandTotal + cartData.extra_charges.delivery) < 2000) {
                    deliveryLayout.setVisibility(View.VISIBLE);
                    deliveryPrice.setText("₹ " + cartData.extra_charges.delivery_cutoff_price);
                } else {
                    deliveryLayout.setVisibility(View.GONE);
                    deliveryPrice.setText("");
                }
            } else {
                tagGroup.setText(selectedMeasurement.measurement.name);
                if (cartData.grandTotal < 2000) {
                    deliveryLayout.setVisibility(View.VISIBLE);
                    deliveryPrice.setText("₹ " + cartData.extra_charges.delivery_cutoff_price);
                } else {
                    deliveryLayout.setVisibility(View.GONE);
                    deliveryPrice.setText("");
                }
            }
            if (getIntent().getStringExtra("suggestion").length() > 0)
                txt_order_note.setText(getIntent().getStringExtra("suggestion"));
            else
                ll_order_note.setVisibility(View.GONE);
            totalPay = getIntent().getDoubleExtra("totalPay", 0.0);

            txtgstmessage.setText("Tax(" + (cartData.stateGST + cartData.centralGST) + "% GST) ");
            txtgstprice.setText("₹ " + String.format("%.2f", (cartData.grandTotal - cartData.productTotalPrice)));
            cartRv.setAdapter(new MyAdapter(cartData.cartItems, Helper.getLocalValue(this, "userid"), this));


            continues.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    tv_progress_message.setText("Loading..");
                    if (progressDialog != null)
                        progressDialog.show();
                    if (Payment_Type.equalsIgnoreCase("Online")) {
                        pay(totalPay);
                    } else {
                        transaction = new Transaction(OrderId, TransactionID, PaymentID, CheckOutActivity.totalPay, CheckOutActivity.selectedMeasurement, CheckOutActivity.selectedAddress, "COD");
                        saveTransaction(transaction);
                    }
                }
            });
        }

    }

    public class MyAdapter extends RecyclerView.Adapter<MyAdapter.CheckViewHolder> {

        List<CartItem> data;
        public String userid;
        OrderSummaryActivity activity;

        public MyAdapter(List<CartItem> data, String userid, OrderSummaryActivity activity) {
            this.data = data;
            this.userid = userid;
            this.activity = activity;
        }

        @Override
        public MyAdapter.CheckViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new MyAdapter.CheckViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_item, parent, false));
        }

        @Override
        public void onBindViewHolder(final MyAdapter.CheckViewHolder holder, int position) {
            try {

                final CartItem current = data.get(position);
                if (current != null) {
                    holder.delete_item.setVisibility(View.GONE);
                    holder.name.setText(current.productInfo.name + "  x  " + current.quantity);
                    holder.price.setText("₹ " + current.price);
                    holder.price.setGravity(Gravity.RIGHT);
                    holder.desc.setText(current.productInfo.garmentType);
                    if (current.type.equalsIgnoreCase("ALTER")) {
                        holder.product_type.setText("Alter");
                    } else if (current.type.equalsIgnoreCase("REFRESH")) {
                        holder.product_type.setText("Refresh");
                    } else {
                        holder.product_type.setText("Old Order");
                    }
                    Glide.with(holder.itemView.getContext()).load(current.productInfo.image)
                            .into(holder.pic);
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

        }

        @Override
        public int getItemCount() {
            return data != null ? data.size() : 0;
        }

        class CheckViewHolder extends RecyclerView.ViewHolder {

            TextView name, price, desc, quantity, product_type;
            ImageView pic, plus, minus,delete_item;
            LinearLayout plusMinusLayout;

            public CheckViewHolder(View itemView) {
                super(itemView);
                name = (TextView) itemView.findViewById(R.id.name);
                price = (TextView) itemView.findViewById(R.id.price);
                desc = (TextView) itemView.findViewById(R.id.desc);
                pic = (ImageView) itemView.findViewById(R.id.pic);
                plus = (ImageView) itemView.findViewById(R.id.plus);
                minus = (ImageView) itemView.findViewById(R.id.minus);
                quantity = (TextView) itemView.findViewById(R.id.quantity);
                product_type = (TextView) itemView.findViewById(R.id.type);
                plusMinusLayout = (LinearLayout) itemView.findViewById(R.id.plusMinusLayout);
                delete_item=(ImageView)itemView.findViewById(R.id.delete_item);
            }
        }
    }

    public void pay(final double grandTotal) {

        String clientId = "test_bC4HXGD74rup1Jw9K3r5UzKYaev9xSH0G2G";
        String clientSecret = "test_YgwEO6gkClccjMFi7gUqI2BfyCXZ5lHBBMtpIzmHvLen0gI3kmRqyQwtMMzLVdcUQaFM4NjDtiI3IyrZZOr8py3uG6T7MpgYZEKciqtUR1eE37xdu7I0Nxr058C";
        final HashMap<String, String> map = new HashMap<>();
        map.put("client_id", clientId);
        map.put("client_secret", clientSecret);
        map.put("grant_type", "client_credentials");
        InstaMojoPaymentUtils.getInstaMojoService()
                .getAccessToken(map)
                .enqueue(new Callback<GetAccessTokenResponse>() {
                    @Override
                    public void onResponse(Call<GetAccessTokenResponse> call, Response<GetAccessTokenResponse> response) {
                        try {


                            accessToken = response.body().accessToken;
                            final String header = "Bearer " + accessToken;
                            final HashMap<String, String> map2 = new HashMap<>();
                            map2.put("amount", grandTotal + "");
                            map2.put("purpose", "alterations_payment");
                            map2.put("buyer_name", "Ashok");
                            map2.put("email", "satishvantage6@gmail.com");
                            map2.put("phone", "8699780938");
//                            map2.put("name", "sonu");
                            map2.put("redirect_url", "https://www.instamojo.com/integrations/android/redirect/");
                            map2.put("send_email", "false");
                            map2.put("send_sms", "false");
                            map2.put("allow_repeated_payments", "true");
                            map2.put("webhook", "http://www.example.com/webhook/");
                            InstaMojoPaymentUtils.getInstaMojoService()
                                    .getPaymentId(header, map2)
                                    .enqueue(new Callback<GetPaymentIdResponse>() {
                                        @Override
                                        public void onResponse(Call<GetPaymentIdResponse> call, Response<GetPaymentIdResponse> response) {
                                            try {
//                                                Intent intent = new Intent(CheckOutActivity.this, WebPage.class);
//                                                intent.putExtra("url", response.body().longurl);
//                                                startActivity(intent);

                                                HashMap<String, String> map3 = new HashMap();
                                                map3.put("id", response.body().id);
//                                                Toast.makeText(OrderSummaryActivity.this, response.body().id.toString(), Toast.LENGTH_SHORT).show();

//                                                map3.put("name", map2.get("name"));
//                                                map3.put("email", map2.get("email"));
//                                                map3.put("phone", map2.get("phone"));


                                                InstaMojoPaymentUtils.getInstaMojoService()
                                                        .getOrder(header, map3)
                                                        .enqueue(new Callback<GetOrderIdResponse>() {
                                                            @Override
                                                            public void onResponse(Call<GetOrderIdResponse> call, Response<GetOrderIdResponse> response) {
                                                                try {
                                                                    OrderId = response.body().orderId;
//                                                                    Toast.makeText(OrderSummaryActivity.this, OrderId.toString(), Toast.LENGTH_SHORT).show();
                                                                    String result = "";
                                                                    Random myRandom = new Random();

                                                                    result = String.valueOf(myRandom.nextInt());

                                                                    createOrder(accessToken, result);
//                                                                    payUsingMojoSDK(accessToken, response.body().orderId);
                                                                } catch (Exception e) {
                                                                    e.printStackTrace();
                                                                    closeProgressDialog();
                                                                }
                                                            }

                                                            @Override
                                                            public void onFailure(Call<GetOrderIdResponse> call, Throwable t) {
                                                                t.printStackTrace();
                                                                closeProgressDialog();
                                                            }
                                                        });

                                            } catch (Exception e) {
                                                e.printStackTrace();
                                                closeProgressDialog();
                                            }

                                        }

                                        @Override
                                        public void onFailure(Call<GetPaymentIdResponse> call, Throwable t) {
                                            t.printStackTrace();
                                            closeProgressDialog();
                                        }
                                    });


                        } catch (Exception e) {
                            e.printStackTrace();
                            closeProgressDialog();
                        }
                    }

                    @Override
                    public void onFailure(Call<GetAccessTokenResponse> call, Throwable t) {
                        t.printStackTrace();
                        closeProgressDialog();
                    }
                });

    }

    private void createOrder(String accessToken, String transactionID) {
//        String name = nameBox.getText().toString();
//        final String email = emailBox.getText().toString();
//        String phone = phoneBox.getText().toString();
//        String amount = amountBox.getText().toString();
//        String description = descriptionBox.getText().toString();
        //Create the Order
        Order order = new Order(accessToken, transactionID, "Buyer name", "buyer@gmail.com", "8699780938", "" + totalPay, "Altrations");

        Request request = new Request(order, new OrderRequestCallBack() {
            @Override
            public void onFinish(final Order order, final Exception error) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (error != null) {
                            if (error instanceof Errors.ConnectionError) {
                                showToast("No internet connection");
                            } else if (error instanceof Errors.ServerError) {
                                showToast("Server error. Try again");
                            } else if (error instanceof Errors.AuthenticationError) {
                                showToast("Access token is invalid or expired. Please Update the token.");
                            } else if (error instanceof Errors.ValidationError) {
                                // Cast object to validation to pinpoint the issue
                                Errors.ValidationError validationError = (Errors.ValidationError) error;

                                if (!validationError.isValidTransactionID()) {
                                    showToast("Transaction ID is not Unique");
                                    return;
                                }

                                if (!validationError.isValidRedirectURL()) {
                                    showToast("Redirect url is invalid");
                                    return;
                                }

                                if (!validationError.isValidWebhook()) {
                                    showToast("Webhook url is invalid");
                                    return;
                                }

                            } else {
                                showToast(error.getMessage());
                            }
                            return;
                        }
                        closeProgressDialog();
                        startPreCreatedUI(order);
                    }
                });
            }
        });

        request.execute();
    }

    private void startPreCreatedUI(Order order) {
        //Using Pre created UI
//        Toast.makeText(CheckOutActivity.this, "Web service not working yet", Toast.LENGTH_SHORT).show();

        Intent intent = new Intent(OrderSummaryActivity.this, PaymentDetailsActivity.class);
//        startActivity(intent);
        intent.putExtra(Constants.ORDER, order);
        startActivityForResult(intent, Constants.REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        tv_progress_message.setText("Checking payment status");
        if (progressDialog != null)
            progressDialog.show();
        if (requestCode == Constants.REQUEST_CODE && data != null) {
            orderID = data.getStringExtra(Constants.ORDER_ID);
            transactionID = data.getStringExtra(Constants.TRANSACTION_ID);
            paymentID = data.getStringExtra(Constants.PAYMENT_ID);

            //Check for Payment status with Order ID or Transaction ID or paymentID
            if (paymentID != null) {
               /* checkPaymentStatus(transactionID, orderID);*/
                checkPaymentStatus(paymentID);
                    /*Transaction transaction = new Transaction(orderID, transactionID, paymentID, CheckOutActivity.this.cartData.grandTotal, selectedMeasurement, selectedAddress, otherMeasurementOption);
                    saveTransaction(transaction);*/
            } else {
                showToast("Oops!! Payment was failed");
                closeProgressDialog();
            }

        } else {
            closeProgressDialog();
        }
    }

    public void checkPaymentStatus(String paymentID) {
        final String header = "Bearer " + accessToken;
//        final HashMap<String, String> map = new HashMap<>();
//        map.put("id",paymentID);
        InstaMojoPaymentUtils.getInstaMojoService()
                .getStatus(header, "v2/payments/" + paymentID)
                .enqueue(new Callback<GetPaymentStatus>() {
                    @Override
                    public void onResponse(Call<GetPaymentStatus> call, Response<GetPaymentStatus> response) {
                        try {


                            if (response.body().status.equalsIgnoreCase("true")) {
                                tv_progress_message.setText("Saving Transaction...");
//                                Transaction transaction = new Transaction(orderID, transactionID, response.body().id,totalPay, selectedMeasurement.measurementId, selectedAddress.addressId, otherMeasurementOption);
                                if (selectedMeasurement == null) {
                                    transaction = new Transaction(orderID, transactionID, response.body().id, totalPay, selectedMeasurement, selectedAddress, Payment_Type);
                                    saveTransaction(transaction);
                                } else {
//                                    transaction = new Transaction(orderID, transactionID, response.body().id, totalPay, selectedMeasurement.measurementId, selectedAddress, Payment_Type);
                                    transaction = new Transaction(orderID, transactionID, response.body().id, totalPay, selectedMeasurement, selectedAddress, Payment_Type);
                                    saveTransaction(transaction);
                                }


                                /*Intent intent = new Intent(CheckOutActivity.this, OrderSuccessActivity.class);
                                intent.putExtra("data", CheckOutActivity.this.cartData);
                                intent.putExtra("transdata", response.body().id.toString());
                                startActivity(intent);
                                closeProgressDialog();*/


                            } else {
                                transaction = new Transaction(orderID, transactionID, response.body().id, totalPay, selectedMeasurement, selectedAddress, "COD");

                                Intent intent = new Intent(OrderSummaryActivity.this, TransactionFailed.class);
                                intent.putExtra("data", CheckOutActivity.cartData);
                                intent.putExtra("transdata", transaction);
                                intent.putExtra("pay", CheckOutActivity.totalPay);
                                intent.putExtra("suggestion", getIntent().getStringExtra("suggestion"));
                                startActivity(intent);
                                closeProgressDialog();
//                                CheckOutActivity.this.finishAffinity();
//                                Toast.makeText(CheckOutActivity.this, "Something went wrong ! Try again !", Toast.LENGTH_SHORT).show();
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                            closeProgressDialog();
                        }
                    }

                    @Override
                    public void onFailure(Call<GetPaymentStatus> call, Throwable t) {
                        t.printStackTrace();

                        closeProgressDialog();
                    }
                });

    }

    public void saveTransaction(final Transaction transaction) {

//        Toast.makeText(OrderSummaryActivity.this, "df", Toast.LENGTH_SHORT).show();

//        Toast.makeText(this, "saving transaction in our servers...", Toast.LENGTH_SHORT).show();
      /*  String cart_item="";
        for(int i=0;i<cartData.cartItems.size();i++)
        {
            if(i==0)
                cart_item=cartData.cartItems.get(i).id;
            else
                cart_item=cart_item+","+cartData.cartItems.get(i).id;
        }*/
        HashMap<String, Object> map = new HashMap<>();
        map.put("user_id", Helper.getLocalValue(this, "userid"));
        map.put("cart_items", new Gson().toJson(cartData));
        map.put("transaction", new Gson().toJson(transaction));
        map.put("address_id", CheckOutActivity.selectedAddress.addressId);
        if (CheckOutActivity.selectedMeasurement != null)
            map.put("measurement_id", CheckOutActivity.selectedMeasurement.measurementId);
        map.put("otherMeasurementOption", CheckOutActivity.otherMeasurementOption);
        if (Payment_Type.equalsIgnoreCase("Online")) {
            map.put("cod", false);
        } else {
            map.put("cod", true);
        }
        map.put("remarks", txt_order_note.getText().toString());
        ApiUtils.getAlterationService()
                .saveTransaction(map)
                .enqueue(new Callback<SaveTransactionResponse>() {
                    @Override
                    public void onResponse(Call<SaveTransactionResponse> call, Response<SaveTransactionResponse> response) {
                        try {
                            if (response.body().statusCode == 200) {

                                if (response.body() != null && response.body().data != null) {
//                                Toast.makeText(OrderSummaryActivity.this, response.body().message, Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(OrderSummaryActivity.this, OrderSuccessActivity.class);
                                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                    intent.putExtra("data", OrderSummaryActivity.this.cartData);
                                    intent.putExtra("transdata", transaction);
                                    intent.putExtra("pay", totalPay);
                                    intent.putExtra("order_id", response.body().data.orderId);
                                    closeProgressDialog();
                                    startActivity(intent);
                                    Toast.makeText(OrderSummaryActivity.this, "Order successfuly placed", Toast.LENGTH_SHORT).show();
                                    OrderSummaryActivity.this.finishAffinity();
                                    mydb.delete();
                                } else {

                                    closeProgressDialog();
                                    Toast.makeText(OrderSummaryActivity.this, "Something went wrong ! Try again !", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                closeProgressDialog();
                                Toast.makeText(OrderSummaryActivity.this, response.body().message, Toast.LENGTH_SHORT).show();

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


    private void showToast(String s) {
        Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
    }

    public void closeProgressDialog() {

        if (progressDialog != null )
            progressDialog.dismiss();
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
