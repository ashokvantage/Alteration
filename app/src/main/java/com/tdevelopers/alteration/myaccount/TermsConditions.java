package com.tdevelopers.alteration.myaccount;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.tdevelopers.alteration.Cart.Checkout.CartData;
import com.tdevelopers.alteration.Helper;
import com.tdevelopers.alteration.Payment.PaymentGateWay.Responses.OrderSummaryActivity;
import com.tdevelopers.alteration.R;
import com.tdevelopers.alteration.UserPackage.AddressPackage.Address;
import com.tdevelopers.alteration.UserPackage.MeasurementsPackage.MeasurementObject;

public class TermsConditions extends Activity {
    WebView webview;
    ImageView back;
    RelativeLayout accept;
    public CartData cartData;
    public Address selectedAddress;
    public MeasurementObject selectedMeasurement;
    public int otherMeasurementOption = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_terms_conditions);
        getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        back = (ImageView) findViewById(R.id.back);
        accept = (RelativeLayout) findViewById(R.id.accept);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TermsConditions.this.finish();
            }
        });
        webview = (WebView) findViewById(R.id.webview);
        webview.loadUrl("file:///android_asset/term.html");
        Intent in = getIntent();
        if (getIntent().getExtras() != null && getIntent().getExtras().getSerializable("data") != null) {
            cartData = (CartData) getIntent().getExtras().getSerializable("data");
            selectedMeasurement = (MeasurementObject) getIntent().getExtras().getSerializable("measurment");
            selectedAddress = (Address) getIntent().getExtras().getSerializable("address");
            otherMeasurementOption = getIntent().getIntExtra("othermeasurment", 0);
        }
        accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TermsConditions.this, OrderSummaryActivity.class);
                intent.putExtra("data", cartData);
                intent.putExtra("address", selectedAddress);
                intent.putExtra("measurment", selectedMeasurement);
                intent.putExtra("othermeasurment", otherMeasurementOption);
                intent.putExtra("totalPay", getIntent().getDoubleExtra("totalPay", 0.0));
                intent.putExtra("suggestion",getIntent().getStringExtra("suggestion"));
                startActivity(intent);
                Helper.storeLocally(TermsConditions.this, "terms_condition","dismiss");
                TermsConditions.this.finish();
            }
        });
    }
}
