package com.tdevelopers.alteration.myaccount;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.content.res.Configuration;
import android.graphics.drawable.ColorDrawable;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v4.app.ActivityCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.app.AppCompatDialog;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

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
import com.tdevelopers.alteration.Helper;
import com.tdevelopers.alteration.LocationUtil.PermissionUtils;
import com.tdevelopers.alteration.Models.Remote.ApiUtils;
import com.tdevelopers.alteration.MyApp;
import com.tdevelopers.alteration.R;
import com.tdevelopers.alteration.UserPackage.AddressPackage.AddAddressResponse;
import com.tdevelopers.alteration.UserPackage.AddressPackage.Address;
import com.tdevelopers.alteration.UserPackage.AddressPackage.GetAddressResponse;
import com.tdevelopers.alteration.UserPackage.MeasurementsPackage.AddMeasurementResponse;
import com.tdevelopers.alteration.home.HomeActivity;
import com.tdevelopers.alteration.home.UploadPhotosActivity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddressActivity extends AppCompatActivity implements View.OnClickListener, SwipeRefreshLayout.OnRefreshListener, GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener, ActivityCompat.OnRequestPermissionsResultCallback,
        PermissionUtils.PermissionResultCallback {

    RecyclerView addressRv;
    //    boolean fromPayment = false;
    TextView addnew, txttype, txtsave;
    BottomSheetBehavior bottomSheetBehavior;
    AppCompatEditText edtaddressname, edtcity, edtlocality, edtflatno, edtpincode, edtstate, edtlandmark;
    ImageView back;
    int bottomSheetBehaviorstate;
    public static Address data_address;
    RelativeLayout save;
    String savetype = "";
    String address_id = "";
    SwipeRefreshLayout mItemsContainer;
    AppCompatDialog progressDialog;
    boolean swipe = false;

//    private static final String TAG = MyLocationUsingHelper.class.getSimpleName();

    private final static int PLAY_SERVICES_REQUEST = 1000;
    private final static int REQUEST_CHECK_SETTINGS = 2000;

    private Location mLastLocation;

    // Google client to interact with Google API

    private GoogleApiClient mGoogleApiClient;

    double latitude = 0.0;
    double longitude = 0.0;

    // list of permissions

    ArrayList<String> permissions = new ArrayList<>();
    PermissionUtils permissionUtils;

    boolean isPermissionGranted;
TextView txtautodetect;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        adjustFontScale(getResources().getConfiguration());
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
        setContentView(R.layout.activity_address);
        addressRv = (RecyclerView) findViewById(R.id.addressrv);
        addressRv.setLayoutManager(new LinearLayoutManager(AddressActivity.this));
        addressRv.setNestedScrollingEnabled(false);
        txttype = (TextView) findViewById(R.id.txttype);
        addnew = (TextView) findViewById(R.id.addnew);
        back = (ImageView) findViewById(R.id.back);
        save = (RelativeLayout) findViewById(R.id.next);
        txtsave = (TextView) findViewById(R.id.txtsave);
        edtaddressname = (AppCompatEditText) findViewById(R.id.edtaddressname);
        edtcity = (AppCompatEditText) findViewById(R.id.edtcity);
        edtlocality = (AppCompatEditText) findViewById(R.id.edtlocality);
        edtflatno = (AppCompatEditText) findViewById(R.id.edtflatno);
        edtpincode = (AppCompatEditText) findViewById(R.id.edtpincode);
        edtstate = (AppCompatEditText) findViewById(R.id.edtstate);
        edtlandmark = (AppCompatEditText) findViewById(R.id.edtlandmark);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        save.setOnClickListener(this);
        mItemsContainer = (SwipeRefreshLayout) findViewById(R.id.container_items);
        mItemsContainer.setOnRefreshListener(this);
        progressDialog = new AppCompatDialog(AddressActivity.this);
        progressDialog.setCancelable(false);
        progressDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        progressDialog.setContentView(R.layout.progress_loading);
        progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        txtautodetect=(TextView)findViewById(R.id.txtautodetect);
       /* try {
            fromPayment = getIntent().getExtras().getBoolean("fromPayment");
        } catch (Exception e) {
            e.printStackTrace();
        }*/
        DividerItemDecoration mDividerItemDecoration = new DividerItemDecoration(this,
                LinearLayoutManager.VERTICAL);
        addressRv.addItemDecoration(mDividerItemDecoration);

        bottomSheetBehavior = BottomSheetBehavior.from(findViewById(R.id.RelativeLayoutSheetaddaddress));
        bottomSheetBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                bottomSheetBehaviorstate = newState;
                switch (newState) {
                    case BottomSheetBehavior.STATE_EXPANDED:
//                        overLay.setVisibility(View.VISIBLE);

                        break;
                    case BottomSheetBehavior.STATE_DRAGGING:
//                        overLay.setVisibility(View.VISIBLE);
                        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                        break;
                    case BottomSheetBehavior.STATE_COLLAPSED:
//                        overLay.setVisibility(View.GONE);
                        break;
                }

            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {

            }
        });
        addnew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent in=new Intent(AddressActivity.this, CheckOutActivity.class);
//                in.putExtra("fromaddress",true);
//                startActivity(in);

//                startActivity(new Intent(AddressActivity.this, CheckOutActivity.class));
                savetype = "add";
                txttype.setText("ADD NEW ADDRESS");
                txtsave.setText("Add Address");
                edtaddressname.setText("");
                edtcity.setText("");
                edtlocality.setText("");
                edtflatno.setText("");
                edtpincode.setText("");
                edtstate.setText("");
                edtlandmark.setText("");

                address_id = "";
                bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
            }
        });

        permissionUtils = new PermissionUtils(AddressActivity.this);

        permissions.add(Manifest.permission.ACCESS_FINE_LOCATION);
        permissions.add(Manifest.permission.ACCESS_COARSE_LOCATION);

        permissionUtils.check_permission(permissions, "Need GPS permission for getting your location", 1);
        if (checkPlayServices()) {

            // Building the GoogleApi client
            buildGoogleApiClient();
        }
        txtautodetect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getLocation();

                if (mLastLocation != null) {
                    latitude = mLastLocation.getLatitude();
                    longitude = mLastLocation.getLongitude();
                    getAddress();

                } else {
                    Toast.makeText(AddressActivity.this, "Couldn't get the location. Make sure location is enabled on the device", Toast.LENGTH_SHORT).show();
                }
                edtflatno.requestFocus();
            }
        });
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
                            Toast.makeText(AddressActivity.this, "Couldn't get the location. Make sure location is enabled on the device", Toast.LENGTH_SHORT).show();
                        }
                        edtflatno.requestFocus();
                        return true;
                    }
                }
                return false;
            }
        });
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
        else
        {
            Toast.makeText(AddressActivity.this, "Permission not granted", Toast.LENGTH_SHORT).show();

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
            final String address = locationAddress.getAddressLine(0);
            String address1 = locationAddress.getAddressLine(1);
            final String city = locationAddress.getLocality();
            final String locality = locationAddress.getSubLocality();
            final String state = locationAddress.getAdminArea();
            final String country = locationAddress.getCountryName();
            final String postalCode = locationAddress.getPostalCode();

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

                final Dialog dialog1 = new Dialog(AddressActivity.this);
                dialog1.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog1.setContentView(R.layout.custom_alert);
                dialog1.setCancelable(false);
                Button Confirm = (Button) dialog1.findViewById(R.id.btnconfirm);
                Button Calcel = (Button) dialog1.findViewById(R.id.btncancel);
                final TextView txtaddress = (TextView) dialog1.findViewById(R.id.txtaddress);
                txtaddress.setText(address);
                Confirm.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        edtcity.setText(city);
                        edtlocality.setText(locality);
                        edtpincode.setText(postalCode);
                        edtstate.setText(state);
                        dialog1.dismiss();
                    }
                });
                Calcel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog1.dismiss();

                    }
                });
                dialog1.show();
               /* edtcity.setText(city);
                edtlocality.setText(locality);
                edtpincode.setText(postalCode);
                edtstate.setText(state);*/
                /*autoComp_add.setText(currentLocation);
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
                            status.startResolutionForResult(AddressActivity.this, REQUEST_CHECK_SETTINGS);

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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.next:
                if (savetype.equalsIgnoreCase("add")) {
                    addProfile();
                } else
                    editProfile();
                break;

        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        adjustFontScale(getResources().getConfiguration());
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        try {
            if (bottomSheetBehavior != null)
                bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
            if (MyApp.getInstance().isConnectingToInternet()) {
                loadAddresses();
            } else {
                Toast.makeText(AddressActivity.this, "No internet connection available.", Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        checkPlayServices();
    }

    public void loadAddresses() {
        if (progressDialog != null && !swipe)
            progressDialog.show();

        ApiUtils.getAlterationService()
                .getAddresses("address/get-addresses?user_id=" + Helper.getLocalValue(this, "userid"))
                .enqueue(new Callback<GetAddressResponse>() {
                    @Override
                    public void onResponse(Call<GetAddressResponse> call, Response<GetAddressResponse> response) {
                        try {

                            if (response.body() != null) {
                                if (response.body().data != null) {


                                    if (response.body().data.address != null && response.body().data.address.size() != 0) {


                                        addressRv.setAdapter(new AddressListAdapter(response.body().data.address));

                                    }

                                }
                                progressDialog.dismiss();
                                mItemsContainer.setRefreshing(false);
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                            progressDialog.dismiss();
                            mItemsContainer.setRefreshing(false);
                        }
                    }

                    @Override
                    public void onFailure(Call<GetAddressResponse> call, Throwable t) {

                        t.printStackTrace();
                        progressDialog.dismiss();
                        mItemsContainer.setRefreshing(false);
                    }
                });


    }

    @Override
    public void onBackPressed() {
        if (bottomSheetBehaviorstate == 3) {
            bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
        } else {
            super.onBackPressed();
        }
    }

    public class AddressListAdapter extends RecyclerView.Adapter<AddressListAdapter.AddressViewHolder> {

        List<Address> data;

        public AddressListAdapter(List<Address> address) {
            this.data = address;
        }

        @Override
        public AddressViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new AddressViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.address_list_item, parent, false));
        }

        @Override
        public void onBindViewHolder(final AddressViewHolder holder, int position) {
            try {

                final Address current = data.get(position);
                if (current != null) {
                    holder.name.setText(current.addressName);
                    holder.desc.setText(current.flatNo);
                /*if (fromPayment)
                    holder.itemView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            try {
                                CheckOutActivity.selectedAddress = current;
                                CheckOutActivity.updateSelectedAddressInUI(CheckOutActivity.selectedAddress);
                                ((Activity) holder.itemView.getContext()).finish();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                        }
                    });*/

                    holder.delete.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {


                            HashMap<String, Object> map = new HashMap<>();
                            map.put("user_id", Helper.getLocalValue(v.getContext(), "userid"));
                            map.put("address_id", current.addressId);
                            ApiUtils.getAlterationService()
                                    .deleteAddress(map)
                                    .enqueue(new Callback<AddMeasurementResponse>() {
                                        @Override
                                        public void onResponse(Call<AddMeasurementResponse> call, Response<AddMeasurementResponse> response) {
                                            try {

                                                if (response.body().message != null) {
                                                    Toast.makeText(holder.itemView.getContext(), response.body().message, Toast.LENGTH_SHORT).show();
                                                    data.remove(current);
                                                    notifyDataSetChanged();
                                                }
                                            } catch (Exception e) {
                                                e.printStackTrace();
                                            }
                                        }

                                        @Override
                                        public void onFailure(Call<AddMeasurementResponse> call, Throwable t) {
                                            t.printStackTrace();

                                        }
                                    });
                        }
                    });
                    holder.edit.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
//                            Intent intent = new Intent(v.getContext(), EditAddressActivity.class);
//                            intent.putExtra("id", current);
//                            v.getContext().startActivity(intent);
                            savetype = "update";
                            txttype.setText("EDIT ADDRESS");
                            txtsave.setText("Update Address");
                            bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                            edtaddressname.setText(current.addressName);
                            edtcity.setText(current.city);
                            edtlocality.setText(current.areaLocality);
                            edtflatno.setText(current.flatNo);
                            edtpincode.setText(current.pincode);
                            edtstate.setText(current.state);
                            edtlandmark.setText(current.landmark);
                            address_id = current.addressId;
                            if (current.lat != null)
                                latitude = Double.parseDouble(current.lat);
                            if (current.longs != null)
                                longitude = Double.parseDouble(current.longs);
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
            View edit, delete;

            public AddressViewHolder(View itemView) {
                super(itemView);
                desc = (TextView) itemView.findViewById(R.id.desc);

                name = (TextView) itemView.findViewById(R.id.name);

                edit = itemView.findViewById(R.id.edit);
                delete = itemView.findViewById(R.id.delete);
            }
        }
    }

    private void addProfile() {
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
            map.put("user_id", Helper.getLocalValue(AddressActivity.this, "userid"));
            ApiUtils.getAlterationService()
                    .addAddress(map)
                    .enqueue(new Callback<AddAddressResponse>() {
                        @Override
                        public void onResponse(Call<AddAddressResponse> call, Response<AddAddressResponse> response) {
                            try {
                                if (response.body() != null) {
                                    if (response.body().data != null) {


                                        if (response.body().data != null)
                                            data_address = response.body().data;
//                                                        final Address current = data_address;
//                                                        CheckOutActivity.ar0.setChecked(false);
//                                                        CheckOutActivity.ar1.setChecked(false);
//                                                        CheckOutActivity.tagGroupaddress.setText(current.flatNo + ", " + current.areaLocality + ", " + current.cityState + ", " + current.pincode);
//                                                        CheckOutActivity.tagGroupaddress.setVisibility(View.VISIBLE);
//                                                        CheckOutActivity.selectaddress.setVisibility(View.GONE);
//                                                        CheckOutActivity.selectedAddress = current;
//                                                        CheckOutActivity.otherAddressOption = 0;
                                       /* Intent in = new Intent(AddressActivity.this, CheckOutActivity.class);
                                        in.putExtra("from_address", true);
                                        startActivity(in);*/
                                        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                                        loadAddresses();
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
            Toast.makeText(AddressActivity.this, "Please enter valid details...", Toast.LENGTH_SHORT).show();
        }


    }

    private void editProfile() {
        try {
            InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        } catch (Exception e) {

        }
        if (edtaddressname.getText().toString().trim().length() != 0 & edtcity.getText().toString().trim().length() != 0 & edtlocality.getText().toString().trim().length() != 0 & edtflatno.getText().toString().trim().length() != 0 & edtpincode.getText().toString().trim().length() != 0 & edtstate.getText().toString().trim().length() != 0) {


            HashMap<String, Object> map = new HashMap<>();
            map.put("address_id", address_id);
            map.put("address_name", edtaddressname.getText().toString());
            map.put("flat_no", edtflatno.getText().toString());
            map.put("area_locality", edtlocality.getText().toString());
            map.put("city", edtcity.getText().toString());
            map.put("state", edtstate.getText().toString());
            map.put("pincode", edtpincode.getText().toString());
            map.put("landmark", edtlandmark.getText().toString());
            map.put("lat", latitude);
            map.put("long", longitude);
            map.put("user_id", Helper.getLocalValue(AddressActivity.this, "userid"));
            ApiUtils.getAlterationService()
                    .editAddress(map)
                    .enqueue(new Callback<AddAddressResponse>() {
                        @Override
                        public void onResponse(Call<AddAddressResponse> call, Response<AddAddressResponse> response) {
                            try {
                                if (response.body() != null && response.body().message != null) {
                                    Toast.makeText(AddressActivity.this, response.body().message, Toast.LENGTH_SHORT).show();
                                    bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }

//                            finish();
                            loadAddresses();
                        }

                        @Override
                        public void onFailure(Call<AddAddressResponse> call, Throwable t) {

                            t.printStackTrace();

                            finish();
                        }
                    });

        } else {
            Toast.makeText(AddressActivity.this, "Please enter valid details...", Toast.LENGTH_SHORT).show();
        }


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

    @Override
    public void onRefresh() {

        if (MyApp.getInstance().isConnectingToInternet()) {
            swipe = true;
            loadAddresses();
        } else {

            mItemsContainer.setRefreshing(false);
        }
    }
}
