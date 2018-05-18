package com.tdevelopers.alteration.Payment.PaymentGateWay.Responses;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.IntentSender;
import android.content.res.Configuration;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatRadioButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStates;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.gson.Gson;
import com.tdevelopers.alteration.Adapters.CheckOutAdapter;
import com.tdevelopers.alteration.Cart.Checkout.CartData;
import com.tdevelopers.alteration.Helper;
import com.tdevelopers.alteration.LocationUtil.PermissionUtils;
import com.tdevelopers.alteration.Models.Remote.ApiUtils;
import com.tdevelopers.alteration.NewLogin.NewLoginActivity;
import com.tdevelopers.alteration.NewLogin.OtpVerificationSuccess;
import com.tdevelopers.alteration.R;
import com.tdevelopers.alteration.UserPackage.AddressPackage.AddAddressResponse;
import com.tdevelopers.alteration.UserPackage.AddressPackage.Address;
import com.tdevelopers.alteration.UserPackage.AddressPackage.GetAddressResponse;
import com.tdevelopers.alteration.UserPackage.Data;
import com.tdevelopers.alteration.UserPackage.GetUserResponse;
import com.tdevelopers.alteration.UserPackage.MeasurementsPackage.GetMeasurementsResponse;
import com.tdevelopers.alteration.UserPackage.MeasurementsPackage.MeasurementObject;
import com.tdevelopers.alteration.UserPackage.UpdateUserResponse;
import com.tdevelopers.alteration.android.Instamojo;
import com.tdevelopers.alteration.myaccount.AddressActivity;
import com.tdevelopers.alteration.myaccount.MeasurementActivity;
import com.tdevelopers.alteration.myaccount.MyAccountActivity;
import com.tdevelopers.alteration.myaccount.TermsConditions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CheckOutActivity extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener, ActivityCompat.OnRequestPermissionsResultCallback,
        PermissionUtils.PermissionResultCallback {
    public static Address selectedAddress = null;
    public static int otherMeasurementOption = 2;
    public static List<Address> userAddresses;
    public static Data data;
    public static CartData cartData;
    public static TextView txttotalpricewithgst, txttotalprice, txtgstprice, txttotalitem;
    RelativeLayout proceed;
    public static RecyclerView cartRv;
    public static TextView selectM, selectaddress;
    BottomSheetBehavior bottomSheetBehavior, bottomSheetBehavioraddress, RelativeLayoutSheetaddaddress, bottomSheetBehaviorProfile;
    RecyclerView measurementRv, addressRv;
    CircleImageView addM, addAddress;
    public static TextView tagGroup, tagGroupaddress;
    RelativeLayout overLay;
    public static MeasurementObject selectedMeasurement = null;
    public static AppCompatRadioButton r0, r1;
    public MaterialDialog progress;
    public static RelativeLayout expertPriceLayout, deliveryLayout;
    public static TextView expertPrice, deliveryPrice;
    public static Address data_address;
    public static MeasurementObject data_measurment;
    public static double totalPay = 0.0;
    ImageView back;
    int bottomSheetBehaviorstate;
    AppCompatEditText edtaddressname, edtcity, edtlocality, edtflatno, edtpincode, edtstate, edtlandmark;
    EditText edt_order_note;
    TextView txtgstmessage, txtautodetect;
    //    private static final String TAG = MyLocationUsingHelper.class.getSimpleName();

    private final static int PLAY_SERVICES_REQUEST = 1000;
    private final static int REQUEST_CHECK_SETTINGS = 2000;

    private Location mLastLocation;

    // Google client to interact with Google API

    private GoogleApiClient mGoogleApiClient;

    double latitude;
    double longitude;

    // list of permissions

    ArrayList<String> permissions = new ArrayList<>();
    PermissionUtils permissionUtils;

    boolean isPermissionGranted;
    String desc = "";
    int alt_quan = 0, ref_quan = 0, total_count = 0;
    TextView txtfill;
    RelativeLayout saveprofile;
    AppCompatEditText nameInput, mobileInput, emailInput;
    RadioButton rmale, rfemale;
    String Gender = "Male";
    private static final String emailPattern = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@" + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

    @Override
    protected void onResume() {
        super.onResume();
        adjustFontScale(getResources().getConfiguration());
        try {
            if (bottomSheetBehavior != null)
                bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);

            if (bottomSheetBehavioraddress != null)
                bottomSheetBehavioraddress.setState(BottomSheetBehavior.STATE_COLLAPSED);

            if (RelativeLayoutSheetaddaddress != null)
                RelativeLayoutSheetaddaddress.setState(BottomSheetBehavior.STATE_COLLAPSED);

            if (bottomSheetBehaviorProfile != null)
                bottomSheetBehaviorProfile.setState(BottomSheetBehavior.STATE_COLLAPSED);


        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        adjustFontScale(getResources().getConfiguration());
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
        setContentView(R.layout.activity_checkout);
        data = new Data();
        data.measurements = new ArrayList<>();
        userAddresses = new ArrayList<>();
        expertPriceLayout = (RelativeLayout) findViewById(R.id.expertPriceLayout);
        deliveryLayout = (RelativeLayout) findViewById(R.id.deliveryLayout);
        txttotalprice = (TextView) findViewById(R.id.txttotalprice);
        txttotalitem = (TextView) findViewById(R.id.txttotalitem);
        expertPrice = (TextView) findViewById(R.id.expertPrice);
        deliveryPrice = (TextView) findViewById(R.id.deliveryPrice);
        back = (ImageView) findViewById(R.id.back);
        edt_order_note = (EditText) findViewById(R.id.edt_order_note);
        txtgstmessage = (TextView) findViewById(R.id.txtgstmessage);
        saveprofile = (RelativeLayout) findViewById(R.id.save);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        selectedAddress = null;
        selectedMeasurement = null;
        otherMeasurementOption = 2;
        progress = new MaterialDialog.Builder(this)
                .title("Please wait...")
                .content(R.string.please_wait)
                .cancelable(false)
                .progress(true, 0)
                .progressIndeterminateStyle(true)
                .build();

        r0 = (AppCompatRadioButton) findViewById(R.id.r0);
        r1 = (AppCompatRadioButton) findViewById(R.id.r1);
        selectM = (TextView) findViewById(R.id.selectM);
        selectaddress = (TextView) findViewById(R.id.selectaddress);
//        addAddress = (TextView) findViewById(R.id.addAddress);
        tagGroup = (TextView) findViewById(R.id.tagGroup);
        tagGroupaddress = (TextView) findViewById(R.id.tagGroupaddress);
        overLay = (RelativeLayout)findViewById(R.id.overLay);
        addM = (CircleImageView) findViewById(R.id.addM);
        addAddress = (CircleImageView) findViewById(R.id.addAddress);
        measurementRv = (RecyclerView) findViewById(R.id.mRv);
        measurementRv.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        measurementRv.setNestedScrollingEnabled(false);

        addressRv = (RecyclerView) findViewById(R.id.aRv);
        addressRv.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        addressRv.setNestedScrollingEnabled(false);
        cartRv = (RecyclerView) findViewById(R.id.cartRv);
        cartRv.setLayoutManager(new LinearLayoutManager(this));
        cartRv.setNestedScrollingEnabled(false);

        overLay.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                return false;
            }
        });
        overLay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        Instamojo.initialize(this);
        proceed = (RelativeLayout) findViewById(R.id.continues);
        txttotalpricewithgst = (TextView) findViewById(R.id.txttotalpricewithgst);
        txtgstprice = (TextView) findViewById(R.id.txtgstprice);

        bottomSheetBehavior = BottomSheetBehavior.from(findViewById(R.id.RelativeLayoutSheet));
        bottomSheetBehavioraddress = BottomSheetBehavior.from(findViewById(R.id.RelativeLayoutSheetaddress));
        RelativeLayoutSheetaddaddress = BottomSheetBehavior.from(findViewById(R.id.RelativeLayoutSheetaddaddress));
        bottomSheetBehaviorProfile = BottomSheetBehavior.from(findViewById(R.id.bootomsheetprofile));
        bottomSheetBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                bottomSheetBehaviorstate = newState;
                switch (newState) {
                    case BottomSheetBehavior.STATE_EXPANDED:
                        overLay.setVisibility(View.VISIBLE);
                        otherMeasurementOption = 2;
                        selectedMeasurement = null;
                        r0.setChecked(false);
                        r1.setChecked(false);

                        break;
                    case BottomSheetBehavior.STATE_DRAGGING:
                        overLay.setVisibility(View.VISIBLE);


                        break;

                    case BottomSheetBehavior.STATE_COLLAPSED:

                        overLay.setVisibility(View.GONE);
                        break;
                }

            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {

            }
        });

        bottomSheetBehavioraddress.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                bottomSheetBehaviorstate = newState;
                switch (newState) {
                    case BottomSheetBehavior.STATE_EXPANDED:
                        overLay.setVisibility(View.VISIBLE);
                        selectedAddress = null;
                        break;
                    case BottomSheetBehavior.STATE_DRAGGING:
                        overLay.setVisibility(View.VISIBLE);


                        break;

                    case BottomSheetBehavior.STATE_COLLAPSED:

                        overLay.setVisibility(View.GONE);
                        break;
                }

            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {

            }
        });

        RelativeLayoutSheetaddaddress.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                bottomSheetBehaviorstate = newState;
                switch (newState) {
                    case BottomSheetBehavior.STATE_EXPANDED:
                        overLay.setVisibility(View.VISIBLE);
                        selectedAddress = null;

                        break;
                    case BottomSheetBehavior.STATE_DRAGGING:
                        overLay.setVisibility(View.VISIBLE);
                        RelativeLayoutSheetaddaddress.setState(BottomSheetBehavior.STATE_EXPANDED);

                        break;

                    case BottomSheetBehavior.STATE_COLLAPSED:

                        overLay.setVisibility(View.GONE);
                        break;
                }

            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {

            }
        });
        bottomSheetBehaviorProfile.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                switch (newState) {
                    case BottomSheetBehavior.STATE_EXPANDED:
                        overLay.setVisibility(View.GONE);
                        break;
                    case BottomSheetBehavior.STATE_DRAGGING:
                        overLay.setVisibility(View.GONE);
                        break;

                    case BottomSheetBehavior.STATE_COLLAPSED:
                        overLay.setVisibility(View.GONE);
                        break;
                }

            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {

            }
        });
        r0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                otherMeasurementOption = 0;
                selectedMeasurement = null;
                bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                tagGroup.setText("Pickup Sample");
                tagGroup.setVisibility(View.VISIBLE);
                selectM.setVisibility(View.GONE);
                if (cartData.grandTotal < 2000) {
                    deliveryLayout.setVisibility(View.VISIBLE);
                    deliveryPrice.setText("₹ " + cartData.extra_charges.delivery_cutoff_price);
                    totalPay = cartData.grandTotal + cartData.extra_charges.delivery_cutoff_price;
                } else {
                    deliveryLayout.setVisibility(View.GONE);
                    deliveryPrice.setText("");
                    totalPay = cartData.grandTotal;
                }
                txttotalpricewithgst.setText("₹ " + totalPay);
            }
        });
        r1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                otherMeasurementOption = 1;
                selectedMeasurement = null;
                bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                tagGroup.setText("Expert");
                tagGroup.setVisibility(View.VISIBLE);
                selectM.setVisibility(View.GONE);

                expertPrice.setText("₹ " + cartData.extra_charges.delivery);

                if ((cartData.grandTotal + cartData.extra_charges.delivery) < 2000) {
                    deliveryPrice.setText("₹ " + cartData.extra_charges.delivery_cutoff_price);
                    deliveryLayout.setVisibility(View.VISIBLE);
                    totalPay = (cartData.grandTotal + cartData.extra_charges.delivery + cartData.extra_charges.delivery_cutoff_price);
                } else {
                    deliveryPrice.setText("");
                    deliveryLayout.setVisibility(View.GONE);
                    totalPay = (cartData.grandTotal + cartData.extra_charges.delivery);
                }
                expertPriceLayout.setVisibility(View.VISIBLE);
                txttotalpricewithgst.setText("₹ " + totalPay);

            }
        });
        tagGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                selectM.setVisibility(View.VISIBLE);
                tagGroup.setVisibility(View.GONE);
                selectedMeasurement = null;
                otherMeasurementOption = 2;
                r0.setChecked(false);
                r1.setChecked(false);
                tagGroup.setText("");
                if (cartData.grandTotal < 2000) {
                    totalPay = (cartData.grandTotal + cartData.extra_charges.delivery_cutoff_price);
                    deliveryLayout.setVisibility(View.VISIBLE);
                    deliveryPrice.setText("₹ " + cartData.extra_charges.delivery_cutoff_price);
                } else {
                    totalPay = cartData.grandTotal;
                    deliveryLayout.setVisibility(View.GONE);
                    deliveryPrice.setText("");
                }
                txttotalpricewithgst.setText("₹ " + totalPay);
                expertPriceLayout.setVisibility(View.GONE);
                expertPrice.setText("");
            }
        });
        tagGroupaddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                selectaddress.setVisibility(View.VISIBLE);
                tagGroupaddress.setVisibility(View.GONE);
                selectedAddress = null;
//                r0.setChecked(false);
//                r1.setChecked(false);
                tagGroupaddress.setText("");
            }
        });
        Intent in = getIntent();
        if (in.getBooleanExtra("from_address", false)) {
            final Address current = AddressActivity.data_address;
            tagGroupaddress.setText(current.addressName);
            tagGroupaddress.setVisibility(View.VISIBLE);
            selectaddress.setVisibility(View.GONE);
            selectedAddress = current;
        }

        selectM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                otherMeasurementOption = 2;
                selectedMeasurement = null;
                r0.setChecked(false);
                r1.setChecked(false);
                expertPrice.setText("");
                getMeasurements();
            }
        });
        selectaddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                bottomSheetBehavioraddress.setState(BottomSheetBehavior.STATE_EXPANDED);
                selectedAddress = null;
                loadAddresses();
            }
        });
        addAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                RelativeLayoutSheetaddaddress.setState(BottomSheetBehavior.STATE_EXPANDED);
                bottomSheetBehavioraddress.setState(BottomSheetBehavior.STATE_COLLAPSED);
                selectedAddress = null;
            }
        });
        addM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(CheckOutActivity.this, MeasurementActivity.class);
                i.putExtra("fromPayment", true);
                startActivity(i);
            }
        });


        if (getIntent().getExtras() != null && getIntent().getExtras().getSerializable("data") != null)
            cartData = (CartData) getIntent().getExtras().getSerializable("data");

        if (cartData != null) {
            txttotalprice.setText("₹ " + String.format("%.2f", cartData.productTotalPrice));
            txttotalitem.setText("Altration x " + cartData.cartItems.size());

            for (int i = 0; i < cartData.cartItems.size(); i++) {

                if (cartData.cartItems.get(i).type.equalsIgnoreCase("ALTER")) {
                    alt_quan = alt_quan + cartData.cartItems.get(i).quantity;
                } else if (cartData.cartItems.get(i).type.equalsIgnoreCase("REFRESH")) {
                    ref_quan = ref_quan + cartData.cartItems.get(i).quantity;
                }
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
                    txttotalitem.setText(desc.substring(0, 13) + ".. & " + total_count + " more");

                } else {
                    txttotalitem.setText(desc.substring(0, desc.indexOf(",") - 1) + ".. & " + total_count + " more");

                }
            } else {
                txttotalitem.setText(desc);
            }

            txtgstprice.setText("₹ " + String.format("%.2f", (cartData.grandTotal - cartData.productTotalPrice)));
            txtgstmessage.setText("Tax (" + (cartData.stateGST + cartData.centralGST) + "% GST) ");

            if (cartData.grandTotal < 2000) {
                deliveryPrice.setText("₹ " + cartData.extra_charges.delivery_cutoff_price);
                deliveryLayout.setVisibility(View.VISIBLE);
                totalPay = (cartData.grandTotal + cartData.extra_charges.delivery_cutoff_price);
            } else {
                deliveryPrice.setText("");
                deliveryLayout.setVisibility(View.GONE);
                totalPay = cartData.grandTotal;
            }
            txttotalpricewithgst.setText("₹ " + totalPay);
            cartRv.setAdapter(new CheckOutAdapter(cartData.cartItems, Helper.getLocalValue(this, "userid"), this));
            txtfill = (TextView) findViewById(R.id.txtfill);


            int screenSize = getResources().getConfiguration().screenLayout &
                    Configuration.SCREENLAYOUT_SIZE_MASK;

            String toastMsg;
            switch (screenSize) {
                case Configuration.SCREENLAYOUT_SIZE_LARGE:
                    if (cartData.cartItems.size() == 1) {
                        LinearLayout.LayoutParams paramsExample = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, 180);
                        txtfill.setLayoutParams(paramsExample);
                    } else if (cartData.cartItems.size() == 2) {
                        LinearLayout.LayoutParams paramsExample = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, 60);
                        txtfill.setLayoutParams(paramsExample);
                    } else {
                        LinearLayout.LayoutParams paramsExample = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, 0);
                        txtfill.setLayoutParams(paramsExample);
                    }
//                    toastMsg = "Large screen";
                    break;
                case Configuration.SCREENLAYOUT_SIZE_NORMAL:
//                    toastMsg = "Normal screen";
                    break;
                case Configuration.SCREENLAYOUT_SIZE_SMALL:
//                    toastMsg = "Small screen";
                    break;
                default:
                    toastMsg = "Screen size is neither large, normal or small";
            }
//            Toast.makeText(this, toastMsg, Toast.LENGTH_LONG).show();

            proceed.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (selectedAddress == null)
                        Toast.makeText(CheckOutActivity.this, "Please select Address", Toast.LENGTH_SHORT).show();

                    if (selectedMeasurement == null && otherMeasurementOption == 2)
                        Toast.makeText(CheckOutActivity.this, "Please select Measurement", Toast.LENGTH_SHORT).show();

                    if (selectedAddress != null && (selectedMeasurement != null || otherMeasurementOption != 2))
                        if (totalPay >= 9) {
                            boolean email_exist = Helper.getLocalValue(CheckOutActivity.this, "useremail") == null;

                            if (email_exist) {
                                bottomSheetBehaviorProfile.setState(BottomSheetBehavior.STATE_EXPANDED);


                            } else {
                                boolean terms_condition = Helper.getLocalValue(CheckOutActivity.this, "terms_condition") == null;

                                if (terms_condition) {
                                    Intent intent = new Intent(CheckOutActivity.this, TermsConditions.class);
                                    intent.putExtra("data", cartData);
                                    intent.putExtra("address", selectedAddress);
                                    intent.putExtra("measurment", selectedMeasurement);
                                    intent.putExtra("othermeasurment", otherMeasurementOption);
                                    intent.putExtra("totalPay", totalPay);
                                    intent.putExtra("suggestion", edt_order_note.getText().toString());
                                    startActivity(intent);
                                } else {

                                    Intent intent = new Intent(CheckOutActivity.this, OrderSummaryActivity.class);
                                    intent.putExtra("data", cartData);
                                    intent.putExtra("address", selectedAddress);
                                    intent.putExtra("measurment", selectedMeasurement);
                                    intent.putExtra("othermeasurment", otherMeasurementOption);
                                    intent.putExtra("totalPay", totalPay);
                                    intent.putExtra("suggestion", edt_order_note.getText().toString());
                                    startActivity(intent);
//                            pay(totalPay);
                                }
                            }

                        } else {
                            Toast.makeText(CheckOutActivity.this, "Your payment is less than Rup.9", Toast.LENGTH_SHORT).show();

                        }


                }
            });


            getProfile();

        }
        edtaddressname = (AppCompatEditText) findViewById(R.id.edtaddressname);
        edtcity = (AppCompatEditText) findViewById(R.id.edtcity);
        txtautodetect = (TextView) findViewById(R.id.txtautodetect);
        edtlocality = (AppCompatEditText) findViewById(R.id.edtlocality);
        edtflatno = (AppCompatEditText) findViewById(R.id.edtflatno);
        edtpincode = (AppCompatEditText) findViewById(R.id.edtpincode);
        edtstate = (AppCompatEditText) findViewById(R.id.edtstate);
        edtlandmark = (AppCompatEditText) findViewById(R.id.edtlandmark);
        findViewById(R.id.next)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        try {
                            InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
                        } catch (Exception e) {

                        }

                        if (edtaddressname.getText().toString().trim().length() != 0 & edtcity.getText().toString().trim().length() != 0 & edtlocality.getText().toString().trim().length() != 0 & edtflatno.getText().toString().trim().length() != 0 & edtpincode.getText().toString().trim().length() != 0 & edtstate.getText().toString().trim().length() != 0) {


                            HashMap<String, Object> map = new HashMap<>();
                            map.put("address_name", edtaddressname.getText().toString());
                            map.put("flat_no", edtflatno.getText().toString());
                            map.put("area_locality", edtlocality.getText().toString());
                            map.put("city", edtcity.getText().toString());
                            map.put("state", edtstate.getText().toString());
                            map.put("pincode", edtpincode.getText().toString());
                            map.put("landmark", edtlandmark.getText().toString());
                            map.put("lat", latitude);
                            map.put("long", longitude);
                            map.put("user_id", Helper.getLocalValue(CheckOutActivity.this, "userid"));
                            ApiUtils.getAlterationService()
                                    .addAddress(map)
                                    .enqueue(new Callback<AddAddressResponse>() {
                                        @Override
                                        public void onResponse(Call<AddAddressResponse> call, Response<AddAddressResponse> response) {
                                            try {
                                                if (response.body() != null) {
                                                    if (response.body().data != null) {
                                                        RelativeLayoutSheetaddaddress.setState(BottomSheetBehavior.STATE_COLLAPSED);
                                                        edtaddressname.setText("");
                                                        edtcity.setText("");
                                                        edtlocality.setText("");
                                                        edtflatno.setText("");
                                                        edtpincode.setText("");
                                                        edtstate.setText("");
                                                        edtlandmark.setText("");
                                                        data_address = response.body().data;
                                                        final Address current = data_address;
                                                        CheckOutActivity.tagGroupaddress.setText(current.addressName);
                                                        CheckOutActivity.tagGroupaddress.setVisibility(View.VISIBLE);
                                                        CheckOutActivity.selectaddress.setVisibility(View.GONE);
                                                        CheckOutActivity.selectedAddress = current;

                                                    }
                                                }
                                            } catch (Exception e) {
                                                e.printStackTrace();
                                            }
                                        }

                                        @Override
                                        public void onFailure(Call<AddAddressResponse> call, Throwable t) {

                                            t.printStackTrace();

                                            finish();
                                        }
                                    });

                        } else {
                            Toast.makeText(CheckOutActivity.this, "Please enter valid details...", Toast.LENGTH_SHORT).show();
                        }

                    }
                });
        permissionUtils = new PermissionUtils(CheckOutActivity.this);

        permissions.add(Manifest.permission.ACCESS_FINE_LOCATION);
        permissions.add(Manifest.permission.ACCESS_COARSE_LOCATION);

        permissionUtils.check_permission(permissions, "Need GPS permission for getting your location", 1);
        if (checkPlayServices()) {

            // Building the GoogleApi client
            buildGoogleApiClient();
        }
        edtcity.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
//                autoComp_add.setSelectAllOnFocus(false);
//                autoComp_add.setSelected(false);
                final int DRAWABLE_LEFT = 0;
                final int DRAWABLE_TOP = 1;
                final int DRAWABLE_RIGHT = 2;
                final int DRAWABLE_BOTTOM = 3;

                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (event.getRawX() >= (edtcity.getRight() - edtcity.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                        // your action here
                        getLocation();

                        if (mLastLocation != null) {
                            latitude = mLastLocation.getLatitude();
                            longitude = mLastLocation.getLongitude();
                            getAddress();

                        } else {
                            Toast.makeText(CheckOutActivity.this, "Couldn't get the location. Make sure location is enabled on the device", Toast.LENGTH_SHORT).show();
                        }
                        edtflatno.requestFocus();
                        return true;
                    }
                }
                return false;
            }
        });
        txtautodetect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getLocation();

                if (mLastLocation != null) {
                    latitude = mLastLocation.getLatitude();
                    longitude = mLastLocation.getLongitude();
                    getAddress();

                } else {
                    Toast.makeText(CheckOutActivity.this, "Couldn't get the location. Make sure location is enabled on the device", Toast.LENGTH_SHORT).show();
                }
                edtflatno.requestFocus();
            }
        });
        nameInput = (AppCompatEditText) findViewById(R.id.nameInput);
        mobileInput = (AppCompatEditText) findViewById(R.id.mobileInput);
        emailInput = (AppCompatEditText) findViewById(R.id.emailInput);
        rmale = (RadioButton) findViewById(R.id.rmale);
        rfemale = (RadioButton) findViewById(R.id.rfemale);
        mobileInput.setText(Helper.getLocalValue(CheckOutActivity.this, "userphone"));
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
        saveprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
                } catch (Exception e) {

                }


                if (nameInput.getText().toString().trim().length() != 0 && mobileInput.getText().toString().trim().length() > 9 && emailInput.getText().toString().trim().length() != 0 && emailInput.getText().toString().matches(emailPattern)) {


                    HashMap<String, Object> map = new HashMap<>();
                    map.put("user_id", Helper.getLocalValue(CheckOutActivity.this, "userid"));
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
//                                            Toast.makeText(CheckOutActivity.this, "Successfully updated", Toast.LENGTH_SHORT).show();
                                            bottomSheetBehaviorProfile.setState(BottomSheetBehavior.STATE_COLLAPSED);
                                                   /* imgcheckuser.setVisibility(View.VISIBLE);*/
//                                            nameInput.setText(response.body().data.name);
//                                            mobileInput.setText(response.body().data.mobile);
//                                            emailInput.setText(response.body().data.email);

                                           /* if (response.body().data.gender.equalsIgnoreCase("male")) {
                                                rmale.setChecked(true);
                                                rfemale.setChecked(false);
                                            } else {
                                                rfemale.setChecked(true);
                                                rmale.setChecked(false);
                                            }*/
                                            boolean terms_condition = Helper.getLocalValue(CheckOutActivity.this, "terms_condition") == null;

                                            if (terms_condition) {
                                                Intent intent = new Intent(CheckOutActivity.this, TermsConditions.class);
                                                intent.putExtra("data", cartData);
                                                intent.putExtra("address", selectedAddress);
                                                intent.putExtra("measurment", selectedMeasurement);
                                                intent.putExtra("othermeasurment", otherMeasurementOption);
                                                intent.putExtra("totalPay", totalPay);
                                                intent.putExtra("suggestion", edt_order_note.getText().toString());
                                                startActivity(intent);
                                            } else {

                                                Intent intent = new Intent(CheckOutActivity.this, OrderSummaryActivity.class);
                                                intent.putExtra("data", cartData);
                                                intent.putExtra("address", selectedAddress);
                                                intent.putExtra("measurment", selectedMeasurement);
                                                intent.putExtra("othermeasurment", otherMeasurementOption);
                                                intent.putExtra("totalPay", totalPay);
                                                intent.putExtra("suggestion", edt_order_note.getText().toString());
                                                startActivity(intent);
                                            }

                                            Helper.storeLocally(CheckOutActivity.this, "username", response.body().data.name);
                                            Helper.storeLocally(CheckOutActivity.this, "useremail", response.body().data.email);

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
                    Toast.makeText(CheckOutActivity.this, "Please enter valid details...", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    public void getProfile() {

        getMeasurements();
        loadAddresses();

    }

    public void getMeasurements() {
        ApiUtils.getAlterationService()
                .getMeasurements("measurement/get-measurements?user_id=" + Helper.getLocalValue(this, "userid"))
                .enqueue(new Callback<GetMeasurementsResponse>() {
                    @Override
                    public void onResponse(Call<GetMeasurementsResponse> call, Response<GetMeasurementsResponse> response) {
                        try {
                            if (response.body() != null) {
                                if (response.body().data != null) {

                                    if (response.body().data.measurements != null)
                                        measurementRv.setAdapter(new MeasurementAdapter(response.body().data.measurements));


                                }
                            }


                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(Call<GetMeasurementsResponse> call, Throwable t) {
                        t.printStackTrace();

                    }
                });


    }

    public void loadAddresses() {


        ApiUtils.getAlterationService()
                .getAddresses("address/get-addresses?user_id=" + Helper.getLocalValue(this, "userid"))
                .enqueue(new Callback<GetAddressResponse>() {
                    @Override
                    public void onResponse(Call<GetAddressResponse> call, Response<GetAddressResponse> response) {
                        try {

                            if (response.body() != null) {
                                if (response.body().data != null) {


                                    if (response.body().data.address != null)
                                        addressRv.setAdapter(new AddressAdapter(response.body().data.address));

                                }
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(Call<GetAddressResponse> call, Throwable t) {

                        t.printStackTrace();
                    }
                });


    }

    class MeasurementAdapter extends RecyclerView.Adapter<MeasurementAdapter.MeasurementViewHolder> {

        private List<MeasurementObject> data;

        MeasurementAdapter(List<MeasurementObject> data) {
            this.data = data;
        }

        @Override
        public MeasurementViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new MeasurementViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.measurement_item, parent, false));
        }

        @Override
        public void onBindViewHolder(MeasurementViewHolder holder, int position) {

            try {

                final MeasurementObject current = data.get(position);
                if (current != null) {
                    holder.itemView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {


                            r0.setChecked(false);
                            r1.setChecked(false);
                            tagGroup.setText(current.measurement.name);
                            tagGroup.setVisibility(View.VISIBLE);
                            selectM.setVisibility(View.GONE);
                            bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                            selectedMeasurement = current;
                            otherMeasurementOption = 2;
                            Log.v("qtest", "clicked" + otherMeasurementOption + new Gson().toJson(selectedMeasurement));

                            if (cartData.grandTotal < 2000) {
                                totalPay = (cartData.grandTotal + cartData.extra_charges.delivery_cutoff_price);
                                deliveryLayout.setVisibility(View.VISIBLE);
                                deliveryPrice.setText("₹ " + cartData.extra_charges.delivery_cutoff_price);
                            } else {
                                totalPay = cartData.grandTotal;
                                deliveryLayout.setVisibility(View.GONE);
                                deliveryPrice.setText("");
                            }
                            txttotalpricewithgst.setText("₹ " + totalPay);
                            expertPriceLayout.setVisibility(View.GONE);
                            expertPrice.setText("");
                        }
                    });
                    if (current.measurement.name.length() > 2)
                        holder.txtname.setText(current.measurement.name.substring(0, 2) + "...");
                    else
                        holder.txtname.setText(current.measurement.name);
                    holder.name.setText(current.measurement.name);
                    switch (position % 3) {


                        case 0:
                            holder.txtname.setBackgroundDrawable(ContextCompat.getDrawable(holder.itemView.getContext(), R.drawable.banner1_circle));
                            break;
                        case 1:
                            holder.txtname.setBackgroundDrawable(ContextCompat.getDrawable(holder.itemView.getContext(), R.drawable.banner2_circle));
                            break;
                        case 2:
                            holder.txtname.setBackgroundDrawable(ContextCompat.getDrawable(holder.itemView.getContext(), R.drawable.banner3_circle));
                            break;

                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        public int getItemCount() {
            return data != null ? data.size() : 0;
        }

        class MeasurementViewHolder extends RecyclerView.ViewHolder {

            TextView name, txtname;
//            CircleImageView pic;

            public MeasurementViewHolder(View itemView) {
                super(itemView);
                name = (TextView) itemView.findViewById(R.id.name);
                txtname = (TextView) itemView.findViewById(R.id.txtname);
//                pic = (CircleImageView) itemView.findViewById(R.id.pic);
            }
        }
    }

    class AddressAdapter extends RecyclerView.Adapter<AddressAdapter.AddressViewHolder> {
        List<Address> data;

        public AddressAdapter(List<Address> address) {
            this.data = address;
        }

        @Override
        public AddressAdapter.AddressViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new AddressAdapter.AddressViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.address_item, parent, false));
        }

        @Override
        public void onBindViewHolder(final AddressViewHolder holder, int position) {
            try {

                final Address current = data.get(position);
                if (current != null) {
                    holder.name.setText(current.addressName);
                    holder.desc.setText(current.flatNo + " " + current.areaLocality);
                    holder.itemView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            tagGroupaddress.setText(current.addressName);
                            tagGroupaddress.setVisibility(View.VISIBLE);
                            selectaddress.setVisibility(View.GONE);
                            bottomSheetBehavioraddress.setState(BottomSheetBehavior.STATE_COLLAPSED);
                            selectedAddress = current;
                        }
                    });

                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

        @Override
        public int getItemCount() {
            return data != null ? data.size() : 0;
        }

        class AddressViewHolder extends RecyclerView.ViewHolder {

            TextView desc;
            TextView name;

            public AddressViewHolder(View itemView) {
                super(itemView);
                desc = (TextView) itemView.findViewById(R.id.desc);
                name = (TextView) itemView.findViewById(R.id.name);
            }
        }
    }

    @Override
    public void onBackPressed() {
        if (bottomSheetBehaviorstate == 3) {
            bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
            bottomSheetBehavioraddress.setState(BottomSheetBehavior.STATE_COLLAPSED);
            RelativeLayoutSheetaddaddress.setState(BottomSheetBehavior.STATE_COLLAPSED);
        } else {
            selectedAddress = null;
            selectedMeasurement = null;
            otherMeasurementOption = 2;
            super.onBackPressed();
        }
    }

    public void adjustFontScale(Configuration configuration) {
        configuration.fontScale = (float) 0.86;
        DisplayMetrics metrics = getResources().getDisplayMetrics();
        WindowManager wm = (WindowManager) getSystemService(WINDOW_SERVICE);
        wm.getDefaultDisplay().getMetrics(metrics);
        metrics.scaledDensity = configuration.fontScale * metrics.density;
        getBaseContext().getResources().updateConfiguration(configuration, metrics);
    }

    private void getLocation() {

        if (isPermissionGranted) {

            try {
                mLastLocation = LocationServices.FusedLocationApi
                        .getLastLocation(mGoogleApiClient);
            } catch (SecurityException e) {
                e.printStackTrace();
            }

        }

    }

    public android.location.Address getAddress(double latitude, double longitude) {
        Geocoder geocoder;
        List<android.location.Address> addresses;
        geocoder = new Geocoder(this, Locale.getDefault());

        try {
            addresses = geocoder.getFromLocation(latitude, longitude, 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5
            return addresses.get(0);

        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;

    }


    public void getAddress() {

        android.location.Address locationAddress = getAddress(latitude, longitude);

        if (locationAddress != null) {
            String address = locationAddress.getAddressLine(0);
            String address1 = locationAddress.getAddressLine(1);
            String city = locationAddress.getLocality();
            String locality = locationAddress.getSubLocality();
            String state = locationAddress.getAdminArea();
            String country = locationAddress.getCountryName();
            String postalCode = locationAddress.getPostalCode();
            String currentLocation;

            if (!TextUtils.isEmpty(address)) {
                currentLocation = address;

                if (!TextUtils.isEmpty(address1))
                    currentLocation += "\n" + address1;

                if (!TextUtils.isEmpty(city)) {
                    currentLocation += "\n" + city;

                    if (!TextUtils.isEmpty(postalCode))
                        currentLocation += " - " + postalCode;
                } else {
                    if (!TextUtils.isEmpty(postalCode))
                        currentLocation += "\n" + postalCode;
                }

                if (!TextUtils.isEmpty(state))
                    currentLocation += "\n" + state;

                if (!TextUtils.isEmpty(country))
                    currentLocation += "\n" + country;

                edtcity.setText(city);
                edtlocality.setText(locality);
                edtpincode.setText(postalCode);
                edtstate.setText(state);
               /* autoComp_add.setText(currentLocation);
                autoComp_add.setSelectAllOnFocus(false);
                autoComp_add.setSelected(false);*/
            }

        }

    }

    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API).build();

        mGoogleApiClient.connect();

        LocationRequest mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(10000);
        mLocationRequest.setFastestInterval(5000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                .addLocationRequest(mLocationRequest);

        PendingResult<LocationSettingsResult> result =
                LocationServices.SettingsApi.checkLocationSettings(mGoogleApiClient, builder.build());

        result.setResultCallback(new ResultCallback<LocationSettingsResult>() {
            @Override
            public void onResult(LocationSettingsResult locationSettingsResult) {

                final Status status = locationSettingsResult.getStatus();

                switch (status.getStatusCode()) {
                    case LocationSettingsStatusCodes.SUCCESS:
                        // All location settings are satisfied. The client can initialize location requests here
                        getLocation();
                        break;
                    case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                        try {
                            // Show the dialog by calling startResolutionForResult(),
                            // and check the result in onActivityResult().
                            status.startResolutionForResult(CheckOutActivity.this, REQUEST_CHECK_SETTINGS);

                        } catch (IntentSender.SendIntentException e) {
                            // Ignore the error.
                        }
                        break;
                    case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                        break;
                }
            }
        });


    }


    /**
     * Method to verify google play services on the device
     */

    private boolean checkPlayServices() {

        GoogleApiAvailability googleApiAvailability = GoogleApiAvailability.getInstance();

        int resultCode = googleApiAvailability.isGooglePlayServicesAvailable(this);

        if (resultCode != ConnectionResult.SUCCESS) {
            if (googleApiAvailability.isUserResolvableError(resultCode)) {
                googleApiAvailability.getErrorDialog(this, resultCode,
                        PLAY_SERVICES_REQUEST).show();
            } else {
                Toast.makeText(getApplicationContext(),
                        "This device is not supported.", Toast.LENGTH_LONG)
                        .show();
                finish();
            }
            return false;
        }
        return true;
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        final LocationSettingsStates states = LocationSettingsStates.fromIntent(data);
        switch (requestCode) {
            case REQUEST_CHECK_SETTINGS:
                switch (resultCode) {
                    case Activity.RESULT_OK:
                        // All required changes were successfully made
                        getLocation();
                        break;
                    case Activity.RESULT_CANCELED:
                        // The user was asked to change settings, but chose not to
                        break;
                    default:
                        break;
                }
                break;
        }
    }

    /**
     * Google api callback methods
     */
    @Override
    public void onConnectionFailed(ConnectionResult result) {
//        Log.i(TAG, "Connection failed: ConnectionResult.getErrorCode() = "
//                + result.getErrorCode());
    }

    @Override
    public void onConnected(Bundle arg0) {

        // Once connected with google api, get the location
        getLocation();
    }

    @Override
    public void onConnectionSuspended(int arg0) {
        mGoogleApiClient.connect();
    }


    // Permission check functions


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        // redirects to utils
        permissionUtils.onRequestPermissionsResult(requestCode, permissions, grantResults);

    }


    @Override
    public void PermissionGranted(int request_code) {
        Log.i("PERMISSION", "GRANTED");
        isPermissionGranted = true;
    }

    @Override
    public void PartialPermissionGranted(int request_code, ArrayList<String> granted_permissions) {
        Log.i("PERMISSION PARTIALLY", "GRANTED");
    }

    @Override
    public void PermissionDenied(int request_code) {
        Log.i("PERMISSION", "DENIED");
    }

    @Override
    public void NeverAskAgain(int request_code) {
        Log.i("PERMISSION", "NEVER ASK AGAIN");
    }

    public void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
