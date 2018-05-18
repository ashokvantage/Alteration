package com.tdevelopers.alteration.myaccount;

import android.content.res.Configuration;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDialog;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatRadioButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.tdevelopers.alteration.Helper;
import com.tdevelopers.alteration.Models.Remote.ApiUtils;
import com.tdevelopers.alteration.MyApp;
import com.tdevelopers.alteration.Payment.PaymentGateWay.Responses.CheckOutActivity;
import com.tdevelopers.alteration.R;
import com.tdevelopers.alteration.UserPackage.Measurement;
import com.tdevelopers.alteration.UserPackage.MeasurementsPackage.AddMeasurementResponse;
import com.tdevelopers.alteration.UserPackage.MeasurementsPackage.GetMeasurementsResponse;
import com.tdevelopers.alteration.UserPackage.MeasurementsPackage.MeasurementObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MeasurementActivity extends AppCompatActivity implements View.OnClickListener {

    RecyclerView measurementRv;
    boolean fromPayment = false;
    public String gender = "male";
    RelativeLayout save;
    RadioGroup radioGroup;
    TextView txttype;

    AppCompatEditText name, e0, e1, e2, e3, e4, e5, e6, e7, e8, e9, e10, e11, e12, e13, e14, e15;

    MeasurementObject currentMeasurements;
    String measurment_id;
    AppCompatRadioButton r0, r1;
    CircleImageView addM;
    NestedScrollView nested;
    ImageView back;
    String savetype = "";
    AppCompatDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        adjustFontScale(getResources().getConfiguration());
        setContentView(R.layout.activity_measurment);
        measurementRv = (RecyclerView) findViewById(R.id.mRv);
        measurementRv.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        fromPayment = getIntent().getBooleanExtra("fromPayment", false);
        //        measurementRv.setNestedScrollingEnabled(false);

        /*try {
            fromPayment = getIntent().getExtras().getBoolean("fromPayment");
        } catch (Exception e) {
            e.printStackTrace();
        }*/
//        DividerItemDecoration mDividerItemDecoration = new DividerItemDecoration(this,
//                LinearLayoutManager.VERTICAL);
//        measurementRv.addItemDecoration(mDividerItemDecoration);

//        addnew.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent(MeasurementActivity.this, AddNewMeasurement.class));
//            }
//        });
        save = (RelativeLayout) findViewById(R.id.save);
        back = (ImageView) findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        e0 = (AppCompatEditText) findViewById(R.id.e0);
        e1 = (AppCompatEditText) findViewById(R.id.e1);
        e2 = (AppCompatEditText) findViewById(R.id.e2);
        e3 = (AppCompatEditText) findViewById(R.id.e3);
        e4 = (AppCompatEditText) findViewById(R.id.e4);
        e5 = (AppCompatEditText) findViewById(R.id.e5);
        e6 = (AppCompatEditText) findViewById(R.id.e6);
        e7 = (AppCompatEditText) findViewById(R.id.e7);
        e8 = (AppCompatEditText) findViewById(R.id.e8);
        e9 = (AppCompatEditText) findViewById(R.id.e9);
        e10 = (AppCompatEditText) findViewById(R.id.e10);
        e11 = (AppCompatEditText) findViewById(R.id.e11);
        e12 = (AppCompatEditText) findViewById(R.id.e12);
        e13 = (AppCompatEditText) findViewById(R.id.e13);
        e14 = (AppCompatEditText) findViewById(R.id.e14);
        e15 = (AppCompatEditText) findViewById(R.id.e15);

        r0 = (AppCompatRadioButton) findViewById(R.id.r0);
        r1 = (AppCompatRadioButton) findViewById(R.id.r1);

        name = (AppCompatEditText) findViewById(R.id.name);
        txttype = (TextView) findViewById(R.id.txttype);
        addM = (CircleImageView) findViewById(R.id.addM);
        nested = (NestedScrollView) findViewById(R.id.nested);
        addM.setOnClickListener(this);
        radioGroup = (RadioGroup) findViewById(R.id.radioGroup);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.r0:

                        gender = "male";
                        break;
                    case R.id.r1:
                        gender = "female";
                        break;
                }

            }
        });
        save.setOnClickListener(this);
        progressDialog = new AppCompatDialog(MeasurementActivity.this);
        progressDialog.setCancelable(false);
        progressDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        progressDialog.setContentView(R.layout.progress_loading);
        progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.save:
                if (savetype.equalsIgnoreCase("add"))
                    addmeasurment();
                else
                    editmeasurment();
                break;
            case R.id.addM:
                txttype.setText("ADD NEW");
                nested.setVisibility(View.VISIBLE);
                save.setVisibility(View.VISIBLE);
                name.setText("");
                e0.setText("");
                e1.setText("");
                e2.setText("");
                e3.setText("");
                e4.setText("");
                e5.setText("");
                e6.setText("");
                e7.setText("");
                e8.setText("");
                e9.setText("");
                e10.setText("");
                e11.setText("");
                e12.setText("");
                e13.setText("");
                e14.setText("");
                e15.setText("");

                r0.setChecked(true);
                r1.setChecked(false);
                savetype = "add";
                break;
        }
    }

    private void addmeasurment() {
        try {
            InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        } catch (Exception e) {

        }
        if (name.getText().toString().trim().length() == 0)
            Toast.makeText(this, "Please enter valid name", Toast.LENGTH_SHORT).show();


        if (name.getText().toString().trim().length() != 0 && e0.getText().toString().trim().length() != 0 && e1.getText().toString().trim().length() != 0 && e2.getText().toString().trim().length() != 0 && e3.getText().toString().trim().length() != 0 && e4.getText().toString().trim().length() != 0 && e5.getText().toString().trim().length() != 0 && e6.getText().toString().trim().length() != 0 && e7.getText().toString().trim().length() != 0 && e8.getText().toString().trim().length() != 0 && e9.getText().toString().trim().length() != 0 && e10.getText().toString().trim().length() != 0 && e11.getText().toString().trim().length() != 0 && e12.getText().toString().trim().length() != 0 && e13.getText().toString().trim().length() != 0 && e14.getText().toString().trim().length() != 0 && e15.getText().toString().trim().length() != 0) {

            HashMap<String, Object> map = new HashMap<>();

            map.put("user_id", Helper.getLocalValue(this, "userid"));


            Measurement measurement = new Measurement(name.getText().toString(), gender, e0.getText().toString(), e1.getText().toString(), e2.getText().toString(), e3.getText().toString(), e4.getText().toString(), e5.getText().toString(), e6.getText().toString(), e7.getText().toString(), e8.getText().toString(), e9.getText().toString(), e10.getText().toString(), e11.getText().toString(), e12.getText().toString(), e13.getText().toString(), e14.getText().toString(), e15.getText().toString());

            map.put("measurement", new Gson().toJson(measurement));

            for (Map.Entry<String, Object> m : map.entrySet())
                Log.v("mmtest", m.getKey() + ":" + m.getValue());

            ApiUtils.getAlterationService()
                    .addMeasurement(map)
                    .enqueue(new Callback<AddMeasurementResponse>() {
                        @Override
                        public void onResponse(Call<AddMeasurementResponse> call, Response<AddMeasurementResponse> response) {
                            try {

                                Log.v("mtest", new Gson().toJson(response.body()));
                                if (response.body() != null && response.body().message != null) {
                                    if (fromPayment) {
                                        CheckOutActivity.data_measurment = response.body().data;
                                        final MeasurementObject current = CheckOutActivity.data_measurment;
                                        CheckOutActivity.r0.setChecked(false);
                                        CheckOutActivity.r1.setChecked(false);
                                        CheckOutActivity.tagGroup.setText(current.measurement.name);
                                        CheckOutActivity.tagGroup.setVisibility(View.VISIBLE);
                                        CheckOutActivity.selectM.setVisibility(View.GONE);
                                        CheckOutActivity.selectedMeasurement = current;
                                        CheckOutActivity.otherMeasurementOption = 0;
                                        CheckOutActivity.totalPay = CheckOutActivity.cartData.grandTotal;
                                        finish();
                                    } else {
                                        Toast.makeText(MeasurementActivity.this, response.body().message, Toast.LENGTH_SHORT).show();
                                        nested.setVisibility(View.GONE);
                                        save.setVisibility(View.GONE);
                                        getMeasurements();
                                    }
                                } else
                                    Toast.makeText(MeasurementActivity.this, "Something went wrong ! Try again !", Toast.LENGTH_SHORT).show();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }

                        @Override
                        public void onFailure(Call<AddMeasurementResponse> call, Throwable t) {

                            t.printStackTrace();
                        }
                    });
        } else {
            Toast.makeText(this, "Please enter all details", Toast.LENGTH_SHORT).show();
        }


    }

    private void editmeasurment() {
        try {
            InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        } catch (Exception e) {

        }
        if (name.getText().toString().trim().length() == 0)
            Toast.makeText(this, "Please enter valid name", Toast.LENGTH_SHORT).show();


        if (name.getText().toString().trim().length() != 0 && e0.getText().toString().trim().length() != 0 && e1.getText().toString().trim().length() != 0 && e2.getText().toString().trim().length() != 0 && e3.getText().toString().trim().length() != 0 && e4.getText().toString().trim().length() != 0 && e5.getText().toString().trim().length() != 0 && e6.getText().toString().trim().length() != 0 && e7.getText().toString().trim().length() != 0 && e8.getText().toString().trim().length() != 0 && e9.getText().toString().trim().length() != 0 && e10.getText().toString().trim().length() != 0 && e11.getText().toString().trim().length() != 0 && e12.getText().toString().trim().length() != 0 && e13.getText().toString().trim().length() != 0 && e14.getText().toString().trim().length() != 0 && e15.getText().toString().trim().length() != 0) {

            HashMap<String, Object> map = new HashMap<>();

            map.put("user_id", Helper.getLocalValue(this, "userid"));


            Measurement measurement = new Measurement(name.getText().toString(), gender, e0.getText().toString(), e1.getText().toString(), e2.getText().toString(), e3.getText().toString(), e4.getText().toString(), e5.getText().toString(), e6.getText().toString(), e7.getText().toString(), e8.getText().toString(), e9.getText().toString(), e10.getText().toString(), e11.getText().toString(), e12.getText().toString(), e13.getText().toString(), e14.getText().toString(), e15.getText().toString());

            map.put("measurement", new Gson().toJson(measurement));
            map.put("measurement_id", measurment_id);

            for (Map.Entry<String, Object> m : map.entrySet())
                Log.v("mmtest", m.getKey() + ":" + m.getValue());

            ApiUtils.getAlterationService()
                    .editMeasurement(map)
                    .enqueue(new Callback<AddMeasurementResponse>() {
                        @Override
                        public void onResponse(Call<AddMeasurementResponse> call, Response<AddMeasurementResponse> response) {
                            try {

                                Log.v("mtest", new Gson().toJson(response.body()));
                                if (response.body() != null && response.body().message != null) {
                                    if (fromPayment) {
                                        /*CheckOutActivity.data_measurment = response.body().data;
                                        final MeasurementObject current = CheckOutActivity.data_measurment;
                                        CheckOutActivity.r0.setChecked(false);
                                        CheckOutActivity.r1.setChecked(false);
                                        CheckOutActivity.tagGroup.setText(current.measurement.name);
                                        CheckOutActivity.tagGroup.setVisibility(View.VISIBLE);
                                        CheckOutActivity.selectM.setVisibility(View.GONE);
                                        CheckOutActivity.selectedMeasurement = current;
                                        CheckOutActivity.otherMeasurementOption = 0;
                                        CheckOutActivity.totalPay = CheckOutActivity.cartData.grandTotal;*/
//                                        finish();
                                        nested.setVisibility(View.GONE);
                                        save.setVisibility(View.GONE);
                                        getMeasurements();
                                    } else {
                                        Toast.makeText(MeasurementActivity.this, response.body().message, Toast.LENGTH_SHORT).show();
                                        nested.setVisibility(View.GONE);
                                        save.setVisibility(View.GONE);
                                        getMeasurements();
                                    }
                                } else
                                    Toast.makeText(MeasurementActivity.this, "Something went wrong ! Try again !", Toast.LENGTH_SHORT).show();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }

                        @Override
                        public void onFailure(Call<AddMeasurementResponse> call, Throwable t) {

                            t.printStackTrace();
                        }
                    });
        } else {
            Toast.makeText(this, "Please enter all details", Toast.LENGTH_SHORT).show();
        }


    }

    @Override
    protected void onResume() {
        super.onResume();
        adjustFontScale(getResources().getConfiguration());
        try {
            if (MyApp.getInstance().isConnectingToInternet()) {
                getMeasurements();
            } else {
                Toast.makeText(MeasurementActivity.this, "No internet connection available.", Toast.LENGTH_LONG).show();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void getMeasurements() {
        if (progressDialog != null)
            progressDialog.show();
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
//                                    measurementRv.setAdapter(new MeasurementsListAdapter(response.body().data.measurements));

                                }
                            }
                            progressDialog.dismiss();

                        } catch (Exception e) {
                            e.printStackTrace();
                            progressDialog.dismiss();
                        }
                    }

                    @Override
                    public void onFailure(Call<GetMeasurementsResponse> call, Throwable t) {
                        t.printStackTrace();
                        progressDialog.dismiss();

                    }
                });


    }

    class MeasurementAdapter extends RecyclerView.Adapter<MeasurementAdapter.MeasurementViewHolder> {

        private List<MeasurementObject> data;

        MeasurementAdapter(List<MeasurementObject> data) {
            this.data = data;
        }

        @Override
        public MeasurementAdapter.MeasurementViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new MeasurementAdapter.MeasurementViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.measurement_item, parent, false));
        }

        @Override
        public void onBindViewHolder(MeasurementAdapter.MeasurementViewHolder holder, int position) {

            try {

                final MeasurementObject current = data.get(position);
                if (current != null) {
                    holder.itemView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            txttype.setText("EDIT");
                            nested.setVisibility(View.VISIBLE);
                            save.setVisibility(View.VISIBLE);
                            savetype = "update";
                            getmesurment(current);
                            /*r0.setChecked(false);
                            r1.setChecked(false);
                            tagGroup.setText(current.measurement.name);
                            tagGroup.setVisibility(View.VISIBLE);
                            selectM.setVisibility(View.GONE);
                            bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                            selectedMeasurement = current;
                            otherMeasurementOption = 0;
                            totalPay = cartData.grandTotal;
                            Log.v("qtest", "clicked" + otherMeasurementOption + new Gson().toJson(selectedMeasurement));*/


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
            }
        }
    }

    public void getmesurment(MeasurementObject currentMeasurement) {
        if (currentMeasurement != null) {
            currentMeasurements = currentMeasurement;
            if (currentMeasurement.measurement.gender.equals("male")) {
                r0.setChecked(true);
                gender = "male";
            } else {
                r1.setChecked(true);
                gender = "female";
            }
            measurment_id = currentMeasurement.measurementId;
            name.setText(currentMeasurement.measurement.name);

            e0.setText(currentMeasurement.measurement.trouserlength);
            e1.setText(currentMeasurement.measurement.waist);
            e2.setText(currentMeasurement.measurement.thigh);
            e3.setText(currentMeasurement.measurement.ankle);
            e4.setText(currentMeasurement.measurement.crotch);
            e5.setText(currentMeasurement.measurement.hipW);
            e6.setText(currentMeasurement.measurement.shortlength);
            e7.setText(currentMeasurement.measurement.shoulder);
            e8.setText(currentMeasurement.measurement.sleevelength);
            e9.setText(currentMeasurement.measurement.chest);
            e10.setText(currentMeasurement.measurement.tummy);
            e11.setText(currentMeasurement.measurement.hip);
            e12.setText(currentMeasurement.measurement.neck);
            e13.setText(currentMeasurement.measurement.bicep);
            e14.setText(currentMeasurement.measurement.forearm);
            e15.setText(currentMeasurement.measurement.troso);
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
}
