package com.tdevelopers.alteration.NewLogin;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.tdevelopers.alteration.Helper;
import com.tdevelopers.alteration.R;
import com.tdevelopers.alteration.home.HomeActivity;

public class OtpVerificationSuccess extends AppCompatActivity {
    Handler handler;
TextView txtnumber;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_otp_verification_success);
        txtnumber=(TextView)findViewById(R.id.txtnumber);
        txtnumber.setText(Helper.getLocalValue(this, "userphone"));
        handler = new Handler();

        final Runnable r = new Runnable() {
            public void run() {
//                startActivity(new Intent(OtpVerificationSuccess.this, HomeActivity.class));
//                OtpVerificationSuccess.this.finish();

                Intent intent = new Intent(OtpVerificationSuccess.this, HomeActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);

            }
        };

        handler.postDelayed(r, 1000);

    }
}
