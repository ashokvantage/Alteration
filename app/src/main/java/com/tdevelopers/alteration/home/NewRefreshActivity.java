package com.tdevelopers.alteration.home;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.CursorLoader;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.StrictMode;
import android.os.Vibrator;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.tdevelopers.alteration.Cart.Checkout.CartItem;
import com.tdevelopers.alteration.Cart.Checkout.CheckoutResponse;
import com.tdevelopers.alteration.Cart.Checkout.DeleteCartResponce;
import com.tdevelopers.alteration.Cart.Checkout.Support_Image;
import com.tdevelopers.alteration.Cart.getCart.GetCartItemsResponse;
import com.tdevelopers.alteration.DBHelper;
import com.tdevelopers.alteration.Helper;
import com.tdevelopers.alteration.Models.Objects.Alters;
import com.tdevelopers.alteration.Models.Objects.Image;
import com.tdevelopers.alteration.Models.Remote.ApiUtils;
import com.tdevelopers.alteration.Models.Responses.AlterListResponse;
import com.tdevelopers.alteration.MyApp;
import com.tdevelopers.alteration.Payment.PaymentGateWay.Responses.CheckOutActivity;
import com.tdevelopers.alteration.R;
import com.tdevelopers.alteration.UserPackage.MeasurementsPackage.AddMeasurementResponse;
import com.tdevelopers.alteration.Utility;
import com.tdevelopers.alteration.weather.DiscreteScrollViewOptions;
import com.yarolegovich.discretescrollview.DiscreteScrollView;
import com.yarolegovich.discretescrollview.transform.ScaleTransformer;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewRefreshActivity extends AppCompatActivity {
    int bottomSheetBehaviorstate;
    TextView txtitemsquantity, txttitle;
    int genderFlag;  //0 men , 1 women
    HashMap<String, List<Image>> dataMap;
    RelativeLayout checkout;
    Response<AlterListResponse> response;
    String userid;
    List<AlterItemAdapter> alterItemAdapters;

    ImageView cartIcon;
    int ItemQuantity = 0;
    private int REQUEST_CAMERA = 0, SELECT_FILE = 1;
    private String userChoosenTask, realPath;
    //    Image cur_image;
    List<File> Source_file_List = new ArrayList<>();
    File source_file = null;
    DBHelper mydb;
    Cursor c;
    ArrayList<String> p_id_array_list = new ArrayList<>();
    String image_path;
    LinearLayout header;
    String cart_action = "ADD";
    Vibrator vibe;
    AppCompatDialog progressDialog;
    boolean swipe = false;
    RecyclerView menRv, womenRv;
    TextView txtmen, txtwomen;
    BottomSheetBehavior bottomSheetBehavior, uploadpictureBootomSheet;
    RelativeLayout layoutfull;
    TextView txtclose;
    /* public static boolean checkout_page_refresh = false;*/
    Handler handler;

    private DiscreteScrollView recyclerView;
    RelativeLayout rl_submit;
    int s_i_count = 1;
    AlterItemAdapter.AlterOptionItemViewHolder cur_ref_holder;
    //    public ForecastAdapter cur_slid_holder;
    TextView add_image_quantity, txt_title;
    int clickedPosition;
    String note_list = "", note_list_new = "";
    Alters alters;
    String action_type = "Add";
    ImageView img_alert_close;
boolean large_device=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        adjustFontScale(getResources().getConfiguration());
        setContentView(R.layout.activity_new_refresh);
        mydb = new DBHelper(this);
        HomeActivity.pics.clear();
        HomeActivity.places.clear();
        HomeActivity.checkout_page = false;
        boolean result = Utility.checkPermission(NewRefreshActivity.this);
        userid = Helper.getLocalValue(this, "userid");
        checkout = (RelativeLayout) findViewById(R.id.checkout);
        txttitle = (TextView) findViewById(R.id.txttitle);
        txtitemsquantity = (TextView) findViewById(R.id.txtitemsquantity);
        header = (LinearLayout) findViewById(R.id.header);
        menRv = (RecyclerView) findViewById(R.id.menRv);
        womenRv = (RecyclerView) findViewById(R.id.womenRv);
        txtmen = (TextView) findViewById(R.id.txtmen);
        txtwomen = (TextView) findViewById(R.id.txtwomen);
        menRv.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        womenRv.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        bottomSheetBehavior = BottomSheetBehavior.from(findViewById(R.id.AlertBootomSheet));
        uploadpictureBootomSheet = BottomSheetBehavior.from(findViewById(R.id.uploadpictureBootomSheet));
        layoutfull = (RelativeLayout) findViewById(R.id.layoutfull);
        rl_submit = (RelativeLayout) findViewById(R.id.rl_submit);
        recyclerView = (DiscreteScrollView) findViewById(R.id.forecast_city_picker);
        add_image_quantity = (TextView) findViewById(R.id.add_image_quantity);
        img_alert_close = (ImageView) findViewById(R.id.img_alert_close);
        txt_title = (TextView) findViewById(R.id.txt_title);
        img_alert_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                uploadpictureBootomSheet.setState(BottomSheetBehavior.STATE_COLLAPSED);
            }
        });
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = size.x;
        int height = size.y;
        if (height > 2000) {
            large_device = true;
        } else {
            large_device = false;
        }
        uploadpictureBootomSheet.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
//                uploadpictureBootomSheet = newState;
                bottomSheetBehaviorstate = newState;
                switch (newState) {
                    case BottomSheetBehavior.STATE_EXPANDED:
                        layoutfull.setVisibility(View.VISIBLE);
                        break;
                    case BottomSheetBehavior.STATE_DRAGGING:
                        layoutfull.setVisibility(View.VISIBLE);
                        uploadpictureBootomSheet.setState(BottomSheetBehavior.STATE_EXPANDED);
                        break;

                    case BottomSheetBehavior.STATE_COLLAPSED:
                        layoutfull.setVisibility(View.GONE);
                        break;
                }

            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {

            }
        });

        alterItemAdapters = new ArrayList<>();
        cartIcon = (ImageView) findViewById(R.id.cartIcon);
        cartIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                /*startActivity(new Intent(NewRefreshActivity.this, CartActivity.class));*/
                ApiUtils.getAlterationService()
                        .checkOut("user/checkout", Helper.getLocalValue(NewRefreshActivity.this, "userid"))
                        .enqueue(new Callback<CheckoutResponse>() {
                            @Override
                            public void onResponse(Call<CheckoutResponse> call, Response<CheckoutResponse> response) {
                                try {

                                    if (response.body() != null && response.body().cartData != null) {

                                        Intent intent = new Intent(NewRefreshActivity.this, CheckOutActivity.class);
                                        intent.putExtra("data", response.body().cartData);
                                        startActivity(intent);
                                    } else {
                                        Toast.makeText(NewRefreshActivity.this, "Empty cart", Toast.LENGTH_LONG).show();
                                    }

                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }

                            @Override
                            public void onFailure(Call<CheckoutResponse> call, Throwable t) {
                                t.printStackTrace();
                            }
                        });

            }
        });
        /*mydb.delete();*/

        progressDialog = new AppCompatDialog(NewRefreshActivity.this);
        progressDialog.setCancelable(false);
        progressDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        progressDialog.setContentView(R.layout.progress_loading);
        progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        progressDialog.show();

        ItemQuantity = 0;
        if (MyApp.getInstance().isConnectingToInternet()) {
            initData();
        } else {
            Toast.makeText(NewRefreshActivity.this, "No internet connection available.", Toast.LENGTH_LONG).show();
        }

        vibe = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
    }

    private void initData() {
        if (progressDialog != null && !swipe)
            progressDialog.show();
        String endPoint = ApiUtils.getAPIEndPoint("refresh/list");

        ApiUtils.getAlterationService().getAlterList(endPoint)
                .enqueue(new Callback<AlterListResponse>() {
                    @Override
                    public void onResponse(Call<AlterListResponse> call, Response<AlterListResponse> response) {
                        try {
                            if (response.body() != null) {
                                if (response.body().data != null) {
                                    if (response.body().data.alters.men != null)
                                        menRv.setAdapter(new AlterItemAdapter(response.body().data.alters.men));
                                    if (response.body().data.alters.women != null)
                                        womenRv.setAdapter(new AlterItemAdapter(response.body().data.alters.women));
                                    alters = response.body().data.alters;
                                    setUpUI(alters);
                                }
                            }
                            /* progressDialog.dismiss();*/

                        } catch (Exception e) {
                            e.printStackTrace();
                            progressDialog.dismiss();
                        }
                    }

                    @Override
                    public void onFailure(Call<AlterListResponse> call, Throwable t) {
                        t.printStackTrace();
                        progressDialog.dismiss();
                    }
                });

    }

    @TargetApi(Build.VERSION_CODES.O)
    private void setUpUI(final Alters alters) {

        if (userid != null) {

            ApiUtils.getAlterationService()
                    .getCartItems("user/get-cart-items?user_id=" + userid)
                    .enqueue(new Callback<GetCartItemsResponse>() {
                        @Override
                        public void onResponse(Call<GetCartItemsResponse> call, Response<GetCartItemsResponse> response) {
                            try {

                                if (response.body() != null && response.body().data != null && response.body().data.cartItems != null && response.body().data.cartItems.size() != 0) {
                                    {
                                        for (CartItem cartItem : response.body().data.cartItems) {
                                            ItemQuantity = ItemQuantity + cartItem.quantity;
                                        }

                                        for (int i = 0; i < alters.men.size(); i++) {

                                            for (CartItem cartItem : response.body().data.cartItems) {
                                                if (cartItem.productId.equals(alters.men.get(i).id)) {
                                                    alters.men.get(i).quantitiy = cartItem.quantity;
                                                    alters.men.get(i).supporting_images = cartItem.supporting_images;
                                                    alters.men.get(i).product_id = cartItem.productId;
//                                                    ItemQuantity = ItemQuantity + cartItem.quantity;
                                                }
                                            }
                                        }

                                        for (int i = 0; i < alters.women.size(); i++) {

                                            for (CartItem cartItem : response.body().data.cartItems) {
                                                if (cartItem.productId.equals(alters.women.get(i).id)) {
                                                    alters.women.get(i).quantitiy = cartItem.quantity;
                                                    alters.women.get(i).supporting_images = cartItem.supporting_images;
                                                    alters.women.get(i).product_id = cartItem.productId;
//                                                    ItemQuantity = ItemQuantity + cartItem.quantity;
                                                }
                                            }
                                        }
                                        txtitemsquantity.setText("" + ItemQuantity);
                                        txtitemsquantity.setVisibility(View.VISIBLE);
                                        if (alters.men.size() > 0)
                                            menRv.setAdapter(new AlterItemAdapter(alters.men));
                                        else
                                            txtmen.setVisibility(View.GONE);
                                        if (alters.women.size() > 0)
                                            womenRv.setAdapter(new AlterItemAdapter(alters.women));
                                        else
                                            txtwomen.setVisibility(View.GONE);
                                        checkout.setVisibility(View.VISIBLE);
                                        checkout.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                if (MyApp.getInstance().isConnectingToInternet()) {
                                                    checkoutFunction();
                                                } else {
                                                    Toast.makeText(NewRefreshActivity.this, "No internet connection available.", Toast.LENGTH_LONG).show();
                                                }
                                            }
                                        });

                                    }


                                } else {
                                    mydb.delete();
                                    txtitemsquantity.setText("+");
                                    ItemQuantity = 0;
                                    txtitemsquantity.setVisibility(View.GONE);
                                    checkout.setVisibility(View.GONE);
                                    checkout.setOnClickListener(null);

                                    if (alters.men.size() > 0)
                                        menRv.setAdapter(new AlterItemAdapter(alters.men));
                                    else
                                        txtmen.setVisibility(View.GONE);
                                    if (alters.women.size() > 0)
                                        womenRv.setAdapter(new AlterItemAdapter(alters.women));
                                    else
                                        txtwomen.setVisibility(View.GONE);
                                }
                                /*handler = new Handler();

                                final Runnable r = new Runnable() {
                                    public void run() {

                                        handler.postDelayed(this, 1000);
                                        progressDialog.dismiss();
                                    }
                                };

                                handler.postDelayed(r, 0000);*/
                                progressDialog.dismiss();
                            } catch (Exception e) {
                                e.printStackTrace();
                                progressDialog.dismiss();
                            }
                        }

                        @Override
                        public void onFailure(Call<GetCartItemsResponse> call, Throwable t) {
                            t.printStackTrace();
                            progressDialog.dismiss();
                        }
                    });
        }


    }


    private void checkoutFunction() {

        ApiUtils.getAlterationService()
                .checkOut("user/checkout", userid)
                .enqueue(new Callback<CheckoutResponse>() {
                    @Override
                    public void onResponse(Call<CheckoutResponse> call, Response<CheckoutResponse> response) {
                        try {

                            if (response.body() != null && response.body().cartData != null) {

                                Intent intent = new Intent(NewRefreshActivity.this, CheckOutActivity.class);
                                intent.putExtra("data", response.body().cartData);
                                startActivity(intent);
                            } else {
                                Toast.makeText(NewRefreshActivity.this, response.body().message, Toast.LENGTH_LONG).show();
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(Call<CheckoutResponse> call, Throwable t) {
                        t.printStackTrace();
                    }
                });
    }


    public class AlterItemAdapter extends RecyclerView.Adapter<AlterItemAdapter.AlterOptionItemViewHolder> {

        public List<Image> data;

        public AlterItemAdapter(List<Image> data) {
            this.data = data;
        }


        @Override
        public AlterItemAdapter.AlterOptionItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            return new AlterItemAdapter.AlterOptionItemViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.alter_option_item_layout, parent, false));
        }

        public void onBindViewHolder(final AlterItemAdapter.AlterOptionItemViewHolder holder, int position) {

            try {
                int colors[] = {0xff255779, 0xffa6c0cd};


                final Image current = data.get(position);


                if (current != null) {

                    holder.plusMinusLayout.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (current.quantitiy > 0) {

                                Bitmap myLogo = null;
                                s_i_count = current.quantitiy;
                                HomeActivity.pics.clear();
                                HomeActivity.places.clear();
                                cur_ref_holder = holder;
                                HomeActivity.cur_image = current;
                                StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                                StrictMode.setThreadPolicy(policy);
                                try {
                                    for (int i = 0; i < current.quantitiy; i++) {
                                        URL url = new URL(current.supporting_images.get(i).image);
                                        myLogo = BitmapFactory.decodeStream((InputStream) url.getContent());
                                        HomeActivity.pics.add(myLogo);
                                        HomeActivity.places.add(current.supporting_images.get(i).note);
                                    }
                                } catch (IOException e) {
                                    Log.e("sdf", e.getMessage());
                                }
                                add_image_quantity.setText("" + current.quantitiy);

                                Intent in = new Intent(NewRefreshActivity.this, UploadPhotosActivity.class);
                                in.putExtra("quantity", current.quantitiy);
                                in.putExtra("action_type", "Update");
                                in.putExtra("type", "REFRESH");
                                startActivity(in);
                            } else {
                                HomeActivity.pics.clear();
                                HomeActivity.places.clear();
                                HomeActivity.cur_image = current;
                                Intent in = new Intent(NewRefreshActivity.this, UploadQuantity.class);
                                in.putExtra("type", "REFRESH");
                                startActivity(in);
                            }

                        }
                    });
                    holder.rlimage.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (current.quantitiy > 0) {

                                Bitmap myLogo = null;
                                s_i_count = current.quantitiy;
                                HomeActivity.pics.clear();
                                HomeActivity.places.clear();
                                cur_ref_holder = holder;
                                HomeActivity.cur_image = current;
                                StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                                StrictMode.setThreadPolicy(policy);
                                try {
                                    for (int i = 0; i < current.quantitiy; i++) {
                                        URL url = new URL(current.supporting_images.get(i).image);
                                        myLogo = BitmapFactory.decodeStream((InputStream) url.getContent());
                                        HomeActivity.pics.add(myLogo);
                                        HomeActivity.places.add(current.supporting_images.get(i).note);
                                    }
                                } catch (IOException e) {
                                    Log.e("sdf", e.getMessage());
                                }
                                add_image_quantity.setText("" + current.quantitiy);

                                Intent in = new Intent(NewRefreshActivity.this, UploadPhotosActivity.class);
                                in.putExtra("quantity", current.quantitiy);
                                in.putExtra("action_type", "Update");
                                in.putExtra("type", "REFRESH");
                                startActivity(in);
                            } else {
                                HomeActivity.pics.clear();
                                HomeActivity.places.clear();
                                HomeActivity.cur_image = current;
                                Intent in = new Intent(NewRefreshActivity.this, UploadQuantity.class);
                                in.putExtra("type", "REFRESH");
                                startActivity(in);
                            }
                        }
                    });
                   /* if (large_device) {

                    } else {
                        holder.rlimage.getViewTreeObserver()
                                .addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {

                                    @Override
                                    public void onGlobalLayout() {
                                        // TODO Auto-generated method stub
                                        int w = holder.rlimage.getWidth();
                                        int h = holder.rlimage.getHeight();
                                        DisplayMetrics metrics = Resources.getSystem().getDisplayMetrics();

//                                    int dpheight = Math.round((h / (metrics.densityDpi / 160f)));
//                                    int dp_margin = Math.round((20 / (metrics.densityDpi / 160f)));
//                                    Math.round(dpheight);
                                    *//*ViewGroup.LayoutParams params = holder.rltop.getLayoutParams();
                                    params.height = 100;
                                    params.width = 100;
                                    layout.setLayoutParams(params);*//*

//                                        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(h, ViewGroup.LayoutParams.MATCH_PARENT); // or set height to any fixed value you want
//                                        holder.rltop.setLayoutParams(lp);

                                        RelativeLayout.LayoutParams lp1 = new RelativeLayout.LayoutParams(h, h); // or set height to any fixed value you want
                                        holder.rlimage.setLayoutParams(lp1);

                                       *//* RelativeLayout.LayoutParams lp2 = new RelativeLayout.LayoutParams(h - 50, h - 50); // or set height to any fixed value you want
                                        holder.imgpic.setLayoutParams(lp2);
                                        holder.rloriginal.setLayoutParams(lp2);*//*
// OR
//                                        RelativeLayout.LayoutParams layoutParams =
//                                                (RelativeLayout.LayoutParams) holder.imgpic.getLayoutParams();

                                        *//*if (SOMETHING_THAT_WOULD_LIKE_YOU_TO_CHECK) {*//*
                                        // if true center text:
                                       *//* layoutParams.addRule(RelativeLayout.CENTER_IN_PARENT);
                                        holder.imgpic.setLayoutParams(layoutParams);

                                        RelativeLayout.LayoutParams lp3 = new RelativeLayout.LayoutParams(h - 54, h - 54); // or set height to any fixed value you want
                                        holder.imgoriginal.setLayoutParams(lp3);

                                        RelativeLayout.LayoutParams layoutParams2 =
                                                (RelativeLayout.LayoutParams) holder.imgoriginal.getLayoutParams();

                                        *//**//*if (SOMETHING_THAT_WOULD_LIKE_YOU_TO_CHECK) {*//**//*
                                        // if true center text:
                                        layoutParams2.addRule(RelativeLayout.CENTER_IN_PARENT);
                                        holder.imgoriginal.setLayoutParams(layoutParams2);*//*
                                  *//*  } else {
                                        // if false remove center:
                                        layoutParams.addRule(RelativeLayout.CENTER_IN_PARENT, 0);
                                        holder.txtGuestName.setLayoutParams(layoutParams);
                                    }*//*

//                                    your_textView.setLayoutParams(lp);


                                    }
                                });
                    }*/

                    if (current.quantitiy == 0) {
//                    holder.plusMinusLayout.setVisibility(View.GONE);
//                    holder.addName.setVisibility(View.VISIBLE);
                        holder.quantity.setText("+");
                        holder.txt_actiontype.setText("ADD");
                        holder.quantity.setVisibility(View.GONE);
                    } else {
//                    holder.plusMinusLayout.setVisibility(View.VISIBLE);
//                    holder.addName.setVisibility(View.GONE);
                        holder.quantity.setText(current.quantitiy + "");
                        holder.txt_actiontype.setText("EDIT");
                        holder.quantity.setVisibility(View.VISIBLE);

                    }
                    /*holder.name.setText(current.name);*/

                    holder.fullName.setText(current.name);
                    holder.price.setText("₹ " + current.price + "/-");
                   /* if (current.supporting_images.size() > 0) {
                        Bitmap bm = null;
                        if (current.supporting_images.size() == 1) {
                            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                            StrictMode.setThreadPolicy(policy);
                            try {
                                URL url = new URL(current.supporting_images.get(0).image);
                                bm = BitmapFactory.decodeStream((InputStream) url.getContent());
                            } catch (IOException e) {
                                Log.e("sdf", e.getMessage());
                            }
                            Glide.with(holder.itemView.getContext()).load(getResources().getString(R.string.server_url) + "/backend/web/uploads/refresh/" + current.image).into(holder.imgoriginal);

                            Drawable dd = new BitmapDrawable(getResources(), bm);
                            holder.imgpic.setBackground(dd);
                            holder.imgpic.setVisibility(View.VISIBLE);
                            holder.imgpic.setRotation(7);
                        } else {

                            Glide.with(holder.itemView.getContext()).load(getResources().getString(R.string.server_url) + "/backend/web/uploads/refresh/" + current.image).into(holder.imgoriginal);

                            try {
                                URL url = new URL(current.supporting_images.get(1).image);
                                bm = BitmapFactory.decodeStream((InputStream) url.getContent());
                            } catch (IOException e) {
                                Log.e("sdf", e.getMessage());
                            }
                            Drawable dd = new BitmapDrawable(getResources(), bm);
                            holder.imgpic.setBackground(dd);
                            holder.imgpic.setVisibility(View.VISIBLE);
                            holder.imgpic.setRotation(7);
                        }


//                        Glide.with(holder.itemView.getContext()).load(current.supporting_images.get(0).image).into(holder.imgoriginal);
                    } else {
                        if (current.image.length() > 5) {
                            Glide.with(holder.itemView.getContext()).load(getResources().getString(R.string.server_url) + "/backend/web/uploads/refresh/" + current.image).into(holder.imgoriginal);
                            holder.imgpic.setVisibility(View.GONE);
                        }
                    }*/
                    if (current.image.length() > 5) {
                        Glide.with(holder.itemView.getContext()).load(getResources().getString(R.string.server_url) + "/backend/web/uploads/refresh/" + current.image).into(holder.imgoriginal);
                    }

                }

            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(NewRefreshActivity.this, "error", Toast.LENGTH_LONG).show();
            }
        }


        @Override
        public int getItemCount() {
            return data != null ? data.size() : 0;
        }

        class AlterOptionItemViewHolder extends RecyclerView.ViewHolder {
            TextView fullName, price;
            TextView quantity, txt_actiontype;
            LinearLayout plusMinusLayout;
            ImageView imgoriginal;
            RelativeLayout rlimage, rltop;


            public AlterOptionItemViewHolder(View itemView) {
                super(itemView);
                fullName = (TextView) itemView.findViewById(R.id.fullName);
                price = (TextView) itemView.findViewById(R.id.price);
                quantity = (TextView) itemView.findViewById(R.id.quantity);
                txt_actiontype = (TextView) itemView.findViewById(R.id.txt_actiontype);
                plusMinusLayout = (LinearLayout) itemView.findViewById(R.id.plusMinusLayout);
                imgoriginal = (ImageView) itemView.findViewById(R.id.imgoriginal);
                rlimage = (RelativeLayout) itemView.findViewById(R.id.rlimage);
                rltop = (RelativeLayout) itemView.findViewById(R.id.rltop);
            }
        }


    }


    @Override
    public void onResume() {
        super.onResume();
        //        ItemQuantity = 0;
//        initData(0);
        adjustFontScale(getResources().getConfiguration());
        if (HomeActivity.checkout_page) {
            ItemQuantity = 0;
            HomeActivity.checkout_page = false;
            initData();
        }
    }

    public void updateScreen(final Alters alters) {
        if (userid != null) {

            ApiUtils.getAlterationService()
                    .getCartItems("user/get-cart-items?user_id=" + userid)
                    .enqueue(new Callback<GetCartItemsResponse>() {
                        @Override
                        public void onResponse(Call<GetCartItemsResponse> call, Response<GetCartItemsResponse> response) {
                            try {
                                ItemQuantity = 0;
                                if (response.body() != null && response.body().data != null && response.body().data.cartItems != null && response.body().data.cartItems.size() != 0) {
                                    {
                                        for (CartItem cartItem : response.body().data.cartItems) {
                                            ItemQuantity = ItemQuantity + cartItem.quantity;
                                        }

                                        for (int i = 0; i < alters.men.size(); i++) {

                                            for (CartItem cartItem : response.body().data.cartItems) {
                                                if (cartItem.productId.equals(alters.men.get(i).id)) {
                                                    alters.men.get(i).quantitiy = cartItem.quantity;
                                                    alters.men.get(i).supporting_images = cartItem.supporting_images;
                                                    alters.men.get(i).product_id = cartItem.productId;
//                                                    ItemQuantity = ItemQuantity + cartItem.quantity;
                                                }
                                            }
                                        }

                                        for (int i = 0; i < alters.women.size(); i++) {

                                            for (CartItem cartItem : response.body().data.cartItems) {
                                                if (cartItem.productId.equals(alters.women.get(i).id)) {
                                                    alters.women.get(i).quantitiy = cartItem.quantity;
                                                    alters.women.get(i).supporting_images = cartItem.supporting_images;
                                                    alters.women.get(i).product_id = cartItem.productId;
//                                                    ItemQuantity = ItemQuantity + cartItem.quantity;
                                                }
                                            }
                                        }
                                        txtitemsquantity.setText("" + ItemQuantity);
                                        txtitemsquantity.setVisibility(View.VISIBLE);
//                                        if (action_type.equalsIgnoreCase("Update")) {
                                        if (alters.men.size() > 0)
                                            menRv.setAdapter(new AlterItemAdapter(alters.men));
                                        else
                                            txtmen.setVisibility(View.GONE);
                                        if (alters.women.size() > 0)
                                            womenRv.setAdapter(new AlterItemAdapter(alters.women));
                                        else
                                            txtwomen.setVisibility(View.GONE);
                                        progressDialog.dismiss();
//                                        }
                                        checkout.setVisibility(View.VISIBLE);
                                        checkout.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                if (MyApp.getInstance().isConnectingToInternet()) {
                                                    checkoutFunction();
                                                } else {
                                                    Toast.makeText(NewRefreshActivity.this, "No internet connection available.", Toast.LENGTH_LONG).show();
                                                }
                                            }
                                        });

                                    }


                                } else {
                                    mydb.delete();
                                    txtitemsquantity.setText("+");
                                    ItemQuantity = 0;
                                    txtitemsquantity.setVisibility(View.GONE);
                                    checkout.setVisibility(View.GONE);
                                    checkout.setOnClickListener(null);
                                    progressDialog.dismiss();
//                                    if (action_type.equalsIgnoreCase("Update")) {
                                    if (alters.men.size() > 0)
                                        menRv.setAdapter(new AlterItemAdapter(alters.men));
                                    else
                                        txtmen.setVisibility(View.GONE);
                                    if (alters.women.size() > 0)
                                        womenRv.setAdapter(new AlterItemAdapter(alters.women));
                                    else
                                        txtwomen.setVisibility(View.GONE);
//                                    }
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                                progressDialog.dismiss();
                            }
                            progressDialog.dismiss();
                        }

                        @Override
                        public void onFailure(Call<GetCartItemsResponse> call, Throwable t) {
                            t.printStackTrace();
                            progressDialog.dismiss();
                        }
                    });
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
    public void onBackPressed() {
        if (bottomSheetBehaviorstate == 3) {
//            Toast.makeText(NewRefreshActivity.this,"Please add first",Toast.LENGTH_SHORT).show();
            uploadpictureBootomSheet.setState(BottomSheetBehavior.STATE_COLLAPSED);
        } else {
            super.onBackPressed();
            overridePendingTransition(R.anim.slide_in_bottom, R.anim.stay);
        }

    }
}
