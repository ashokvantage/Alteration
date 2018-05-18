package com.tdevelopers.alteration.NewLogin;

import android.content.ClipData;
import android.content.ClipDescription;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.app.AppCompatDialog;
import android.support.v7.widget.AppCompatEditText;
import android.text.Editable;
import android.text.Selection;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.ActionMode;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.crash.FirebaseCrash;
import com.tdevelopers.alteration.Helper;
import com.tdevelopers.alteration.Models.Remote.ApiUtils;
import com.tdevelopers.alteration.MyApp;
import com.tdevelopers.alteration.NewLogin.OTPAuthenticate.OTPAuthenticateResponse;
import com.tdevelopers.alteration.NewLogin.OTPRequest.OTPRequestResponse;
import com.tdevelopers.alteration.R;
import com.tdevelopers.alteration.UserPackage.GetUserResponse;
import com.tdevelopers.alteration.home.NewAlterActivity;
import com.tdevelopers.alteration.myaccount.ContactUs;
import com.tdevelopers.alteration.myaccount.MyAccountActivity;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewLoginActivity extends AppCompatActivity {

    AppCompatEditText mobileInput;
    TextView text;
    TextView verify;
    TextView resendOTP;
    RelativeLayout rlproceed;
    //    OtpView otp;
    EditText otp_one_edit_text, otp_two_edit_text, otp_three_edit_text, otp_four_edit_text;
    Handler handler;
    public static final int REQUEST_ID_MULTIPLE_PERMISSIONS = 1;
    String pasteData = "";
    boolean getcopydata = false;
    AppCompatDialog progressDialog;
    TextView pMessage, txttimer;
    public int counter = 45;
    InputMethodManager imm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
        adjustFontScale(getResources().getConfiguration());
        setContentView(R.layout.activity_new_login);
//        InputMethodManager inputManager = (InputMethodManager)
//                getSystemService(Context.INPUT_METHOD_SERVICE);
//
//        inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
//                InputMethodManager.HIDE_NOT_ALWAYS);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        try {
            FirebaseCrash.report(new Exception("My first Android non-fatal error"));
            FirebaseCrash.log("MainActivity started");
        } catch (Exception e) {

        }
        checkAndRequestPermissions();
        boolean isLoggedIn = Helper.getLocalValue(this, "userid") != null;

        if (isLoggedIn) {

            startActivity(new Intent(this, com.tdevelopers.alteration.home.HomeActivity.class));
            finish();
        }
        progressDialog = new AppCompatDialog(NewLoginActivity.this);
        progressDialog.setCancelable(false);
        progressDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        progressDialog.setContentView(R.layout.progress_loading);
        progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        pMessage = progressDialog.findViewById(R.id.tv_progress_message);
        pMessage.setText("Please wait..");

        text = (TextView) findViewById(R.id.text);
        txttimer = (TextView) findViewById(R.id.texttimer);
        otp_one_edit_text = (EditText) findViewById(R.id.otp_one_edit_text);
        otp_two_edit_text = (EditText) findViewById(R.id.otp_two_edit_text);
        otp_three_edit_text = (EditText) findViewById(R.id.otp_three_edit_text);
        otp_four_edit_text = (EditText) findViewById(R.id.otp_four_edit_text);
        mobileInput = (AppCompatEditText) findViewById(R.id.mobileInput);
        resendOTP = (TextView) findViewById(R.id.resend);
        verify = (TextView) findViewById(R.id.verify);
//        otp = (OtpView) findViewById(R.id.otp);
        rlproceed = (RelativeLayout) findViewById(R.id.rlproceed);
//        text.setText(Html.fromHtml("By signing up, you agree to our <b>Terms and Conditions</b>"));
        text.setVisibility(View.VISIBLE);
        rlproceed.getBackground().setAlpha(51);
        rlproceed.setEnabled(false);
        resendOTP.setAlpha(0.5f);
        resendOTP.setEnabled(false);
        resendOTP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (MyApp.getInstance().isConnectingToInternet()) {
                    sendOTP();
                } else {
                    Toast.makeText(NewLoginActivity.this, "No internet connection available.", Toast.LENGTH_LONG).show();
                }
            }
        });
        verify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (MyApp.getInstance().isConnectingToInternet()) {
                    sendOTP();
                } else {
                    Toast.makeText(NewLoginActivity.this, "No internet connection available.", Toast.LENGTH_LONG).show();
                }


            }
        });

        rlproceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (MyApp.getInstance().isConnectingToInternet()) {
                    if (counter != 0) {
                        verifyOTP();
                    } else {
                        Toast.makeText(NewLoginActivity.this, "Time out. Please try again with new otp.", Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(NewLoginActivity.this, "No internet connection available.", Toast.LENGTH_LONG).show();
                }
            }
        });
        SmsReceiver.bindListener(new SmsListener() {
            @Override
            public void messageReceived(String messageText) {
                try {

                    Log.d("Text", messageText);
//                    Toast.makeText(NewLoginActivity.this, messageText, Toast.LENGTH_LONG).show();
                    if (counter != 0) {
                        if (messageText.length() == 4) {
                            otp_one_edit_text.setText(messageText.substring(0, 1));
                            otp_two_edit_text.setText(messageText.substring(1, 2));
                            otp_three_edit_text.setText(messageText.substring(2, 3));
                            otp_four_edit_text.setText(messageText.substring(3, 4));
                        }
                        handler = new Handler();
                        final Runnable r = new Runnable() {
                            public void run() {
                                verifyOTP();
                            }
                        };

                        handler.postDelayed(r, 1000);
                    }
                } catch (Exception e) {

                }
            }
        });
        otp_one_edit_text.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if ((editable.toString().length() == 1) && (!editable.toString().equalsIgnoreCase(" ")))
                    otp_two_edit_text.requestFocus();
                else {
                    otp_one_edit_text.requestFocus();
                }

            }
        });
        otp_two_edit_text.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if ((editable.toString().length() == 1) && (!editable.toString().equalsIgnoreCase(" ")))
                    otp_three_edit_text.requestFocus();
                else {
                    otp_one_edit_text.requestFocus();
                }
            }
        });
        otp_three_edit_text.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if ((editable.toString().length() == 1) && (!editable.toString().equalsIgnoreCase(" ")))
                    otp_four_edit_text.requestFocus();
                else {
                    otp_two_edit_text.requestFocus();
                }
            }
        });
        otp_four_edit_text.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if ((editable.toString().length() == 1) && (!editable.toString().equalsIgnoreCase(" "))) {
                    imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(otp_four_edit_text.getWindowToken(), 0);
                } else {
                    otp_three_edit_text.requestFocus();
                }
            }
        });
/*
        otp_two_edit_text.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                //You can identify which key pressed buy checking keyCode value with KeyEvent.KEYCODE_
                if(keyCode == KeyEvent.KEYCODE_DEL) {
                    otp_one_edit_text.requestFocus();
                }
                return false;
            }
        });
*/
/*
        otp_two_edit_text.setOnEditorActionListener(new EditText.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    // your action here
                    return true;
                }
                return false;
            }
        });
*/
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_DEL:
                if ((otp_one_edit_text.hasFocus())&&(otp_one_edit_text.getText().toString().length()==0)) {
                    otp_one_edit_text.requestFocus();
                } else if ((otp_two_edit_text.hasFocus())&&(otp_two_edit_text.getText().toString().length()==0)) {
                    otp_one_edit_text.requestFocus();
                } else if ((otp_three_edit_text.hasFocus())&&(otp_three_edit_text.getText().toString().length()==0)) {
                    otp_two_edit_text.requestFocus();
                } else if ((otp_four_edit_text.hasFocus())&&(otp_four_edit_text.getText().toString().length()==0)) {
                    otp_three_edit_text.requestFocus();
                }
                return true;
            case KeyEvent.KEYCODE_0:
            case KeyEvent.KEYCODE_1:
            case KeyEvent.KEYCODE_2:
            case KeyEvent.KEYCODE_3:
            case KeyEvent.KEYCODE_4:
            case KeyEvent.KEYCODE_5:
            case KeyEvent.KEYCODE_6:
            case KeyEvent.KEYCODE_7:
            case KeyEvent.KEYCODE_8:
            case KeyEvent.KEYCODE_9:
                if (otp_one_edit_text.hasFocus()&&(otp_one_edit_text.getText().toString().length() == 1)) {
                    otp_two_edit_text.requestFocus();
                } else if (otp_two_edit_text.hasFocus()&&(otp_two_edit_text.getText().toString().length() == 1)) {
                    otp_three_edit_text.requestFocus();
                } else if (otp_three_edit_text.hasFocus()&&(otp_three_edit_text.getText().toString().length() == 1)) {
                    otp_four_edit_text.requestFocus();
                } else if (otp_four_edit_text.hasFocus()&&(otp_four_edit_text.getText().toString().length() == 1)) {
                    imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(otp_four_edit_text.getWindowToken(), 0);
                }
                return true;
            case KeyEvent.KEYCODE_F:
                return true;
            case KeyEvent.KEYCODE_J:
                return true;
            case KeyEvent.KEYCODE_K:
                return true;
            default:
                return super.onKeyUp(keyCode, event);
        }
    }

    @Override
    public boolean onKeyLongPress(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_CALL) {
            // a long press of the call key.
            // do our work, returning true to consume it.  by
            // returning true, the framework knows an action has
            // been performed on the long press, so will set the
            // canceled flag for the following up event.
            return true;
        }
        return super.onKeyLongPress(keyCode, event);
    }

    public void check_paste() {
        getcopydata = true;
        ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);

// If it does contain data, decide if you can handle the data.
        if (!(clipboard.hasPrimaryClip())) {

            Toast.makeText(NewLoginActivity.this, "Something went wrong ! Try again !", Toast.LENGTH_SHORT).show();

        } else if (!(clipboard.getPrimaryClipDescription().hasMimeType(ClipDescription.MIMETYPE_TEXT_PLAIN))) {
            Toast.makeText(NewLoginActivity.this, "Something went wrong ! Try again !", Toast.LENGTH_SHORT).show();

            // since the clipboard has data but it is not plain text

        } else {

            //since the clipboard contains plain text.
            ClipData.Item item = clipboard.getPrimaryClip().getItemAt(0);

            // Gets the clipboard as text.
            pasteData = item.getText().toString();
            if (pasteData.length() == 4) {
                otp_one_edit_text.setText(pasteData.substring(0));
                otp_two_edit_text.setText(pasteData.substring(1));
                otp_three_edit_text.setText(pasteData.substring(2));
                otp_four_edit_text.setText(pasteData.substring(3));

            }

        }

    }

    private void verifyOTP() {

        if (otp_one_edit_text.getText().toString().length() > 0 && otp_two_edit_text.getText().toString().length() > 0 && otp_three_edit_text.getText().toString().length() > 0 && otp_four_edit_text.getText().toString().length() > 0) {
            String otp = otp_one_edit_text.getText().toString() + otp_two_edit_text.getText().toString() + otp_three_edit_text.getText().toString() + otp_four_edit_text.getText().toString();

            ApiUtils.getAlterationService()
                    .authenticateOTP("user/authenticate", mobileInput.getText().toString(), otp)
                    .enqueue(new Callback<OTPAuthenticateResponse>() {
                        @Override
                        public void onResponse(Call<OTPAuthenticateResponse> call, Response<OTPAuthenticateResponse> response) {
                            try {

                                if (response.body() != null) {

                                    if (response.body().statusCode == 200) {
                                        Helper.storeLocally(NewLoginActivity.this, "userid", response.body().data.userId);
                                        Helper.storeLocally(NewLoginActivity.this, "userphone", response.body().data.mobile);
                                        init();
                                    } else {
                                        Toast.makeText(NewLoginActivity.this, response.body().message, Toast.LENGTH_SHORT).show();
                                    }
                                } else {
                                    Toast.makeText(NewLoginActivity.this, "Something went wrong ! Try again !", Toast.LENGTH_SHORT).show();
                                }

                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }

                        @Override
                        public void onFailure(Call<OTPAuthenticateResponse> call, Throwable t) {
                            t.printStackTrace();
                        }
                    });
        } else
            Toast.makeText(NewLoginActivity.this, "Please enter valid OTP", Toast.LENGTH_SHORT).show();
    }

    private void sendOTP() {
        InputMethodManager inputManager = (InputMethodManager)
                getSystemService(Context.INPUT_METHOD_SERVICE);

        inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
                InputMethodManager.HIDE_NOT_ALWAYS);
        if (mobileInput.getText().toString().length() == 10) {
            if (progressDialog != null)
                progressDialog.show();
            verify.setBackgroundResource(R.drawable.rounded_verify);
            verify.setEnabled(false);
            ApiUtils.getAlterationService()
                    .requestOTP("user/request-onetime-password", mobileInput.getText().toString())
                    .enqueue(new Callback<OTPRequestResponse>() {
                        @Override
                        public void onResponse(Call<OTPRequestResponse> call, Response<OTPRequestResponse> response) {
                            try {
                                if (response.code() == 200) {
                                    if (response.body().statusCode == 200) {
                                        verify.setBackgroundResource(R.drawable.rounded_verify);
                                        verify.setEnabled(false);
                                        rlproceed.getBackground().setAlpha(255);
                                        rlproceed.setEnabled(true);
                                        resendOTP.setAlpha(1f);
                                        resendOTP.setEnabled(true);
                                        text.setText("Please enter otp verification code sent to " + mobileInput.getText().toString());
                                        txttimer.setText("Time remaining :" + String.valueOf(counter) + " seconds");
                                        counter = 45;
                                        new CountDownTimer(45000, 1000) {
                                            public void onTick(long millisUntilFinished) {
                                                counter--;
                                                txttimer.setText("Time remaining :" + String.valueOf(counter) + " seconds");
                                            }

                                            public void onFinish() {
                                                counter = 0;
                                                txttimer.setText("Time out. Try again with new otp.");
                                            }
                                        }.start();
                                    } else {
                                        Toast.makeText(NewLoginActivity.this, response.body().message, Toast.LENGTH_SHORT).show();
                                        verify.setBackgroundResource(R.drawable.rounded2);
                                        verify.setEnabled(true);
                                    }
                                } else {
                                    String res_string = response.errorBody().string();
                                    JSONObject jsonobject = new JSONObject(res_string);
                                    Toast.makeText(NewLoginActivity.this, jsonobject.getString("message") + " or Invalid number", Toast.LENGTH_SHORT).show();
                                    verify.setBackgroundResource(R.drawable.rounded2);
                                    verify.setEnabled(true);
                                }
                                progressDialog.dismiss();
                            } catch (Exception e) {
                                e.printStackTrace();
                                progressDialog.dismiss();
                                verify.setBackgroundResource(R.drawable.rounded2);
                                verify.setEnabled(true);
                            }
                        }

                        @Override
                        public void onFailure(Call<OTPRequestResponse> call, Throwable t) {
                            t.printStackTrace();
                            progressDialog.dismiss();
                            verify.setBackgroundResource(R.drawable.rounded2);
                            verify.setEnabled(true);
                        }
                    });
        } else
            Toast.makeText(NewLoginActivity.this, "Please enter valid mobile number", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onResume() {
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

    private boolean checkAndRequestPermissions() {
        int recievesms = ContextCompat.checkSelfPermission(NewLoginActivity.this, android.Manifest.permission.RECEIVE_SMS);
        int readsms = ContextCompat.checkSelfPermission(NewLoginActivity.this, android.Manifest.permission.READ_SMS);
        int sendsms = ContextCompat.checkSelfPermission(NewLoginActivity.this, android.Manifest.permission.SEND_SMS);
        List<String> listPermissionsNeeded = new ArrayList<>();

        if (recievesms != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(android.Manifest.permission.RECEIVE_SMS);
        }
        if (readsms != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(android.Manifest.permission.READ_SMS);
        }
        if (sendsms != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(android.Manifest.permission.SEND_SMS);
        }
        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(NewLoginActivity.this, listPermissionsNeeded.toArray
                    (new String[listPermissionsNeeded.size()]), REQUEST_ID_MULTIPLE_PERMISSIONS);
            return false;
        }
        return true;
    }

    public void init() {
        ApiUtils.getAlterationService()
                .getUserProfile("user/get-profile?user_id=" + Helper.getLocalValue(NewLoginActivity.this, "userid"))
                .enqueue(new Callback<GetUserResponse>() {
                    @Override
                    public void onResponse(Call<GetUserResponse> call, Response<GetUserResponse> response) {
                        try {
                            if (!response.body().message.equalsIgnoreCase("No userdeails found.")) {
                                Helper.storeLocally(NewLoginActivity.this, "username", response.body().data.name);
                                Helper.storeLocally(NewLoginActivity.this, "userphone", response.body().data.phone);
                                Helper.storeLocally(NewLoginActivity.this, "useremail", response.body().data.email);
                                Helper.storeLocally(NewLoginActivity.this, "usergender", response.body().data.gender);
                            } else {
                                Helper.storeLocally(NewLoginActivity.this, "username", null);
                                Helper.storeLocally(NewLoginActivity.this, "useremail", null);
                                Helper.storeLocally(NewLoginActivity.this, "usergender", null);
                            }
//                            startActivity(new Intent(NewLoginActivity.this, OtpVerificationSuccess.class));
//                            NewLoginActivity.this.finish();

                            Intent intent = new Intent(NewLoginActivity.this, OtpVerificationSuccess.class);
                            startActivity(intent);


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

   /* @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_D:
                return true;
            case KeyEvent.KEYCODE_F:
                return true;
            case KeyEvent.KEYCODE_J:
                return true;
            case KeyEvent.KEYCODE_K:
                return true;
            default:
                return super.onKeyUp(keyCode, event);
        }
    }*/

}
