package com.tdevelopers.alteration.home;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.bumptech.glide.Glide;
import com.tdevelopers.alteration.Cart.ClearCartResponse;
import com.tdevelopers.alteration.Helper;
import com.tdevelopers.alteration.Models.Remote.ApiUtils;
import com.tdevelopers.alteration.R;
import com.tdevelopers.alteration.Renew.Button.ButtonResponses.ButtonResponse;
import com.tdevelopers.alteration.Renew.Button.ButtonsModelsAdapter;
import com.tdevelopers.alteration.Renew.Collar.CollarModelsAdapter;
import com.tdevelopers.alteration.Renew.Collar.CollarResponses.CollarResponse;
import com.tdevelopers.alteration.Renew.Cuff.CuffModelsAdapter;
import com.tdevelopers.alteration.Renew.Cuff.CuffsResponses.CuffsResponse;
import com.tdevelopers.alteration.Renew.Placket.PlacketResponses.PlacketResponse;
import com.tdevelopers.alteration.Renew.Placket.PlacketsModelAdapter;
import com.tdevelopers.alteration.Renew.Pocket.PocketResponses.PocketResponse;
import com.tdevelopers.alteration.Renew.Pocket.PocketsModelAdapter;
import com.tdevelopers.alteration.Renew.Sleeve.SleeveResponses.SleeveResponse;
import com.tdevelopers.alteration.Renew.Sleeve.SleevesModelAdapter;
import com.tdevelopers.alteration.Renew.home.ColorItem;
import com.tdevelopers.alteration.Renew.home.ColorResponce;
import com.tdevelopers.alteration.Renew.home.ShirtItems;
import com.tdevelopers.alteration.Renew.home.ShirtResponce;
import com.tdevelopers.alteration.Renew.home.ShirtsModelAdapter;
import com.tdevelopers.alteration.myaccount.MyRenew;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewRenewActivity extends AppCompatActivity implements View.OnClickListener {

    RecyclerView fabricRv;
    //    RecyclerView modelsRv, modelsRvimage;
//    LinearLayout modelsFrame;
    TextView shirtTextView, collarTextView, buttonTextView, cuffTextView, sleevesTextView, pocketsTextView, placketsTextView;
//    View close;

    ImageView[] itemImages;
    ImageView[] itemImagesgarments;
    ButtonsModelsAdapter buttonsModelsAdapter;
    CollarModelsAdapter collarModelsAdapter;
    CuffModelsAdapter cuffModelsAdapter;
    SleevesModelAdapter sleevesModelAdapter;
    PocketsModelAdapter pocketsModelAdapter;
    PlacketsModelAdapter placketsModelAdapter;
    ShirtsModelAdapter shirtsModelAdapter;
    String colorCode;
    ImageView imgaddcart;
    RelativeLayout imgopen;
    TextView txttitle;
    public static TextView txtprice, txtdes;
    public static TextView txtcustomsubtvitle;
    BottomSheetBehavior bottomSheetBehavior, bottomSheetBehaviorColor;
    View overLay;
    //    RecyclerView customiseRv;
    public static List<String> display_imagePath = new ArrayList<>();
    public static List<String> use_imagePath = new ArrayList<>();
    public static List<String> style = new ArrayList<>();
    public static List<String> name = new ArrayList<>();
    public static List<String> id = new ArrayList<>();
    public static List<String> price = new ArrayList<>();
    public static List<String> Cat = new ArrayList<>();
    HorizontalScrollView hsc;
    FrameLayout frame;
    ImageView imgclose, imgsave;
    TextView men, women;
    View v0, v1;
    Response<ShirtResponce> response;
    int genderFlag, position = 0;
    RelativeLayout container;
    Bitmap bitmap = null;
    ImageView imgdown, imgcustom;
    RecyclerView customiseRv;
    RecyclerView garmentsRv;
    int width = 0, height = 0;
    RelativeLayout.LayoutParams parms;
    int bottomSheetBehaviorstate;
    String fabric_id = "";
    public static double total_price = 0.0, additioncharge = 200.00;
    TextView txtpay;
    String renew_shirt_path, customize_type, customize_id;
    public static String description;

    @Override
    protected void onResume() {
        super.onResume();
        adjustFontScale(getResources().getConfiguration());
        try {
            if (bottomSheetBehavior != null)
                bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        adjustFontScale(getResources().getConfiguration());
        setContentView(R.layout.activity_new_repair);
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
//        setTitle("RENEW FOR MEN");

//        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        imgsave = (ImageView) findViewById(R.id.imgsave);
        imgclose = (ImageView) findViewById(R.id.imgclose);
        men = (TextView) findViewById(R.id.men);
        women = (TextView) findViewById(R.id.women);
        v0 = findViewById(R.id.v0);
        v1 = findViewById(R.id.v1);
        container = (RelativeLayout) findViewById(R.id.container);
//        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                finish();
//            }
//        });
        txtprice = (TextView) findViewById(R.id.txtprice);
        txtdes = (TextView) findViewById(R.id.txtdes);
        txtprice.setText("" + additioncharge);
        txtdes.setText("");
        imgclose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        imgsave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (name.size() > 0) {
                    bitmap = null;
                    frame.setDrawingCacheEnabled(false);
                    ViewGroup v1 = (ViewGroup) findViewById(R.id.frame);
                    v1.setDrawingCacheEnabled(true);
                    bitmap = Bitmap.createBitmap(v1.getDrawingCache());
//                    v1.setDrawingCacheEnabled(false);
//                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
//                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
                    if (bitmap != null) {
                        ByteArrayOutputStream stream = new ByteArrayOutputStream();
                        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
                        renew_shirt_path = SaveImage(bitmap);
                        CreateRenew createRenew = new CreateRenew();
                        createRenew.execute();
                    } else {
//                        Toast.makeText(NewRenewActivity.this, "Please customize first", Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(NewRenewActivity.this, "Please customize first", Toast.LENGTH_LONG).show();

                }
            }
        });
        hsc = (HorizontalScrollView) findViewById(R.id.hsc);
        Intent in = getIntent();
        colorCode = in.getStringExtra("colorcode");
        frame = (FrameLayout) findViewById(R.id.frame);

        imgcustom = (ImageView) findViewById(R.id.imgcustom);
       /* modelsRv = (RecyclerView) findViewById(R.id.models);
        modelsRv.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        modelsRvimage = (RecyclerView) findViewById(R.id.modelsimage);
        modelsRvimage.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
*/

        fabricRv = (RecyclerView) findViewById(R.id.fabricRv);
        fabricRv.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        shirtTextView = (TextView) findViewById(R.id.shirt);
        shirtTextView.setOnClickListener(this);

        collarTextView = (TextView) findViewById(R.id.collar);
        collarTextView.setOnClickListener(this);

        cuffTextView = (TextView) findViewById(R.id.cuffs);
        cuffTextView.setOnClickListener(this);


        buttonTextView = (TextView) findViewById(R.id.buttons);
        buttonTextView.setOnClickListener(this);

        sleevesTextView = (TextView) findViewById(R.id.sleeves);
        sleevesTextView.setOnClickListener(this);

        pocketsTextView = (TextView) findViewById(R.id.pocket);
        pocketsTextView.setOnClickListener(this);

        placketsTextView = (TextView) findViewById(R.id.placket);
        placketsTextView.setOnClickListener(this);

        imgopen = (RelativeLayout) findViewById(R.id.imgopen);
        imgdown = (ImageView) findViewById(R.id.imgdown);
        imgaddcart = (ImageView) findViewById(R.id.imgaddcart);
        txttitle = (TextView) findViewById(R.id.txttitle);


        txtcustomsubtvitle = (TextView) findViewById(R.id.txtcustomsubtvitle);
        /*modelsFrame = (LinearLayout) findViewById(R.id.modelsFrame);
        close = findViewById(R.id.close);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                modelsFrame.setVisibility(View.GONE);
                modelsRvimage.setVisibility(View.GONE);
                fabricRv.setVisibility(View.VISIBLE);
                hsc.setVisibility(View.VISIBLE);
            }
        });*/
        imgdown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
            }
        });

        itemImages = new ImageView[9];
        itemImagesgarments = new ImageView[9];
        itemImages[0] = (ImageView) findViewById(R.id.i0);

        itemImages[1] = (ImageView) findViewById(R.id.i1);

        itemImages[2] = (ImageView) findViewById(R.id.i2);

        itemImages[3] = (ImageView) findViewById(R.id.i3);

        itemImages[4] = (ImageView) findViewById(R.id.i4);

        itemImages[5] = (ImageView) findViewById(R.id.i5);

        itemImages[6] = (ImageView) findViewById(R.id.i6);

        itemImages[7] = (ImageView) findViewById(R.id.i7);

        itemImages[8] = (ImageView) findViewById(R.id.i8);

        itemImagesgarments[0] = (ImageView) findViewById(R.id.imggarments0);

        itemImagesgarments[1] = (ImageView) findViewById(R.id.imggarments1);

        itemImagesgarments[2] = (ImageView) findViewById(R.id.imggarments2);

        itemImagesgarments[3] = (ImageView) findViewById(R.id.imggarments3);

        itemImagesgarments[4] = (ImageView) findViewById(R.id.imggarments4);

        itemImagesgarments[5] = (ImageView) findViewById(R.id.imggarments5);

        itemImagesgarments[6] = (ImageView) findViewById(R.id.imggarments6);

        itemImagesgarments[7] = (ImageView) findViewById(R.id.imggarments7);

        itemImagesgarments[8] = (ImageView) findViewById(R.id.imggarments8);

        display_imagePath = new ArrayList<>();
        use_imagePath = new ArrayList<>();
        name = new ArrayList<>();
        style = new ArrayList<>();
        id = new ArrayList<>();
        price = new ArrayList<>();
        Cat = new ArrayList<>();
        selected(shirtTextView);
//        initData();

        overLay = findViewById(R.id.overLay);
        customiseRv = (RecyclerView) findViewById(R.id.customiseRv);
        customiseRv.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        customiseRv.setNestedScrollingEnabled(false);
        bottomSheetBehavior = BottomSheetBehavior.from(findViewById(R.id.RelativeLayoutSheet));
        bottomSheetBehaviorColor = BottomSheetBehavior.from(findViewById(R.id.RelativeLayoutSheetcolor));
        if (bottomSheetBehaviorColor != null)
            bottomSheetBehaviorColor.setState(BottomSheetBehavior.STATE_EXPANDED);
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

        bottomSheetBehaviorColor.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                switch (newState) {
                    case BottomSheetBehavior.STATE_EXPANDED:
//                        overLay.setVisibility(View.VISIBLE);

                        break;
                    case BottomSheetBehavior.STATE_DRAGGING:
//                        overLay.setVisibility(View.VISIBLE);
                        bottomSheetBehaviorColor.setState(BottomSheetBehavior.STATE_EXPANDED);

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

        imgopen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                total_price = 0.0;
                if (name.size() > 0) {
                    bitmap = null;
                    bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                    frame.setDrawingCacheEnabled(false);
                    ViewGroup v1 = (ViewGroup) findViewById(R.id.frame);
                    v1.setDrawingCacheEnabled(true);
                    bitmap = Bitmap.createBitmap(v1.getDrawingCache());
//                    v1.setDrawingCacheEnabled(false);
//                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
//                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
                    if (bitmap != null) {
                        ByteArrayOutputStream stream = new ByteArrayOutputStream();
                        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
                        /*Glide.with(NewRenewActivity.this)
                                .load(stream.toByteArray())
                                .into(imgcustom);*/
//                        Glide.with(NewRenewActivity.this).load(new BitmapDrawable(getResources(), bitmap)).into(imgcustom);
                        imgcustom.setBackgroundDrawable(new BitmapDrawable(getResources(), bitmap));
                        renew_shirt_path = SaveImage(bitmap);
                    } else {
//                        Toast.makeText(NewRenewActivity.this, "Please customize first", Toast.LENGTH_LONG).show();
                    }
//                    imgcustom.setBackgroundDrawable(new BitmapDrawable(getResources(), bitmap));
                    for (int i = 0; i < price.size(); i++) {
                        total_price = total_price + Double.parseDouble(price.get(i));

                    }
                    total_price = total_price + additioncharge;
                    txtpay.setText("Proceed to pay ₹ " + total_price);
                    txtcustomsubtvitle.setText("" + total_price);

                    customiseRv.setAdapter(new CustomShirtAdapter(use_imagePath, display_imagePath, name, style, id, price));
//                    Intent in=new Intent(NewRenewActivity.this,CustomizeActivity.class);
//                    startActivity(in);
//                    overridePendingTransition( R.anim.slide_up, R.anim.stay);

                } else {
                    Toast.makeText(NewRenewActivity.this, "Please customize first", Toast.LENGTH_LONG).show();
                }

            }
        });
        Display display = getWindowManager().getDefaultDisplay();
//        ImageView iv = (ImageView) findViewById(R.id.left);
        width = display.getWidth(); // ((display.getWidth()*20)/100)
        width = width / 3;
        height = display.getHeight();// ((display.getHeight()*30)/100)
        height = width;
        parms = new RelativeLayout.LayoutParams(width, height);
//        iv.setLayoutParams(parms);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int height1 = displayMetrics.heightPixels;
        int width2 = displayMetrics.widthPixels;

        garmentsRv = (RecyclerView) findViewById(R.id.garmentsrv);
        garmentsRv.setLayoutManager(new GridLayoutManager(NewRenewActivity.this, 3));
        garmentsRv.setNestedScrollingEnabled(false);

        txtpay = (TextView) findViewById(R.id.txtpay);
      /*  ApiUtils.getAlterationService()
                .getRepairColor("alter/colors")*/
        if (MyRenew.customize_Details.size() > 0) {
            customize_type = "update";
            customize_id = MyRenew.customize_Id;
            for (int i = 0; i < MyRenew.customize_Details.size(); i++) {
                if (MyRenew.customize_Details.get(i).type.equalsIgnoreCase("Sleeves")) {
                    Glide.with(NewRenewActivity.this).load(MyRenew.customize_Details.get(i).useimagePath).into(itemImages[0]);
                    Glide.with(NewRenewActivity.this).load(MyRenew.customize_Details.get(i).useimagePath).into(itemImagesgarments[0]);

                    use_imagePath.add(MyRenew.customize_Details.get(i).useimagePath);
                    display_imagePath.add(MyRenew.customize_Details.get(i).displayimagePath);
                    name.add(MyRenew.customize_Details.get(i).name);
                    style.add(MyRenew.customize_Details.get(i).style);
                    id.add(MyRenew.customize_Details.get(i).id);
                    price.add(MyRenew.customize_Details.get(i).price);
                    Cat.add("Sleeves");

                } else if (MyRenew.customize_Details.get(i).type.equalsIgnoreCase("Shirt")) {
                    Glide.with(NewRenewActivity.this).load(MyRenew.customize_Details.get(i).useimagePath).into(itemImages[1]);
                    Glide.with(NewRenewActivity.this).load(MyRenew.customize_Details.get(i).useimagePath).into(itemImagesgarments[1]);

                    use_imagePath.add(MyRenew.customize_Details.get(i).useimagePath);
                    display_imagePath.add(MyRenew.customize_Details.get(i).displayimagePath);
                    name.add(MyRenew.customize_Details.get(i).name);
                    style.add(MyRenew.customize_Details.get(i).style);
                    id.add(MyRenew.customize_Details.get(i).id);
                    price.add(MyRenew.customize_Details.get(i).price);
                    Cat.add("Shirt");
                } else if (MyRenew.customize_Details.get(i).type.equalsIgnoreCase("Placket")) {
                    Glide.with(NewRenewActivity.this).load(MyRenew.customize_Details.get(i).useimagePath).into(itemImages[2]);
                    Glide.with(NewRenewActivity.this).load(MyRenew.customize_Details.get(i).useimagePath).into(itemImagesgarments[2]);

                    use_imagePath.add(MyRenew.customize_Details.get(i).useimagePath);
                    display_imagePath.add(MyRenew.customize_Details.get(i).displayimagePath);
                    name.add(MyRenew.customize_Details.get(i).name);
                    style.add(MyRenew.customize_Details.get(i).style);
                    id.add(MyRenew.customize_Details.get(i).id);
                    price.add(MyRenew.customize_Details.get(i).price);
                    Cat.add("Placket");
                } else if (MyRenew.customize_Details.get(i).type.equalsIgnoreCase("Pocket")) {
                    Glide.with(NewRenewActivity.this).load(MyRenew.customize_Details.get(i).useimagePath).into(itemImages[3]);
                    Glide.with(NewRenewActivity.this).load(MyRenew.customize_Details.get(i).useimagePath).into(itemImagesgarments[3]);

                    use_imagePath.add(MyRenew.customize_Details.get(i).useimagePath);
                    display_imagePath.add(MyRenew.customize_Details.get(i).displayimagePath);
                    name.add(MyRenew.customize_Details.get(i).name);
                    style.add(MyRenew.customize_Details.get(i).style);
                    id.add(MyRenew.customize_Details.get(i).id);
                    price.add(MyRenew.customize_Details.get(i).price);
                    Cat.add("Pocket");

                } else if (MyRenew.customize_Details.get(i).type.equalsIgnoreCase("Button")) {
                    Glide.with(NewRenewActivity.this).load(MyRenew.customize_Details.get(i).useimagePath).into(itemImages[4]);
                    Glide.with(NewRenewActivity.this).load(MyRenew.customize_Details.get(i).useimagePath).into(itemImagesgarments[4]);

                    use_imagePath.add(MyRenew.customize_Details.get(i).useimagePath);
                    display_imagePath.add(MyRenew.customize_Details.get(i).displayimagePath);
                    name.add(MyRenew.customize_Details.get(i).name);
                    style.add(MyRenew.customize_Details.get(i).style);
                    id.add(MyRenew.customize_Details.get(i).id);
                    price.add(MyRenew.customize_Details.get(i).price);
                    Cat.add("Button");
                } else if (MyRenew.customize_Details.get(i).type.equalsIgnoreCase("Cuff")) {
                    Glide.with(NewRenewActivity.this).load(MyRenew.customize_Details.get(i).useimagePath).into(itemImages[5]);
                    Glide.with(NewRenewActivity.this).load(MyRenew.customize_Details.get(i).useimagePath).into(itemImagesgarments[5]);

                    use_imagePath.add(MyRenew.customize_Details.get(i).useimagePath);
                    display_imagePath.add(MyRenew.customize_Details.get(i).displayimagePath);
                    name.add(MyRenew.customize_Details.get(i).name);
                    style.add(MyRenew.customize_Details.get(i).style);
                    id.add(MyRenew.customize_Details.get(i).id);
                    price.add(MyRenew.customize_Details.get(i).price);
                    Cat.add("Cuff");
                } else if (MyRenew.customize_Details.get(i).type.equalsIgnoreCase("Collar")) {
                    Glide.with(NewRenewActivity.this).load(MyRenew.customize_Details.get(i).useimagePath).into(itemImages[6]);
                    Glide.with(NewRenewActivity.this).load(MyRenew.customize_Details.get(i).useimagePath).into(itemImagesgarments[6]);

                    use_imagePath.add(MyRenew.customize_Details.get(i).useimagePath);
                    display_imagePath.add(MyRenew.customize_Details.get(i).displayimagePath);
                    name.add(MyRenew.customize_Details.get(i).name);
                    style.add(MyRenew.customize_Details.get(i).style);
                    id.add(MyRenew.customize_Details.get(i).id);
                    price.add(MyRenew.customize_Details.get(i).price);
                    Cat.add("Collar");
                }
            }
            selected(shirtTextView);

            bottomSheetBehaviorColor.setState(BottomSheetBehavior.STATE_COLLAPSED);
            MyRenew.customize_Details.clear();
        } else {
            customize_type = "insert";
            ApiUtils.getAlterationService()
                    .getRepairFebric("customize/fabrics")
                    .enqueue(new Callback<ColorResponce>() {
                        @Override
                        public void onResponse(Call<ColorResponce> call, Response<ColorResponce> response) {
                            try {
                                total_price = 0.0;
                                garmentsRv.setAdapter(new PreviousItemsAdapter(response.body().data));


                            } catch (Exception e) {
                                e.printStackTrace();

                            }
                        }

                        @Override
                        public void onFailure(Call<ColorResponce> call, Throwable t) {
                            t.printStackTrace();

                        }
                    });

        }
    }

    public String SaveImage(Bitmap finalBitmap) {

        String root = Environment.getExternalStorageDirectory().getAbsolutePath();
        File myDir = new File(root + "/saved_images");
        myDir.mkdirs();

        String fname = "Image-alter" + ".png";
        File file = new File(myDir, fname);
        if (file.exists()) file.delete();
        try {
            FileOutputStream out = new FileOutputStream(file);
            finalBitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
            out.flush();
            out.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return file.getAbsolutePath();
    }

    private void initData(String id) {
        ApiUtils.getAlterationService()
                .getShirtBody("alter/shirt-bodies?fabric_id=" + id)
                .enqueue(new Callback<ShirtResponce>() {
                    @Override
                    public void onResponse(Call<ShirtResponce> call, Response<ShirtResponce> response) {
                        /*try {
                            fabricRv.setAdapter(new ShirtsItemsAdapter(response.body().data.shirts.men));
                            Log.e("TAG", "" + response.body());
//                            Toast.makeText(NewRenewActivity.this,""+response.body().data.shirts.men,Toast.LENGTH_LONG).show();
                            Glide.with(NewRenewActivity.this).load(response.body().data.shirts.men.get(0).image).into(itemImages[0]);
                            Glide.with(NewRenewActivity.this).load(response.body().data.shirts.men.get(0).image).into(itemImagesgarments[0]);
                        } catch (Exception e) {
                            e.printStackTrace();

                        }*/
                        try {
                            reset();
                            NewRenewActivity.this.response = response;
                            men.setOnClickListener(NewRenewActivity.this);
                            women.setOnClickListener(NewRenewActivity.this);

                            if (position == 0) {
                                men.setTextColor(ContextCompat.getColor(NewRenewActivity.this, R.color.colorPrimary));
                                women.setTextColor(ContextCompat.getColor(NewRenewActivity.this, R.color.colorGrey));
                                v0.setVisibility(View.VISIBLE);
                                v1.setVisibility(View.INVISIBLE);
                                men.setText(Html.fromHtml("<b>Men</b>"));
                                women.setText("Women");
                            } else if (position == 1) {

                                women.setTextColor(ContextCompat.getColor(NewRenewActivity.this, R.color.colorPrimary));
                                men.setTextColor(ContextCompat.getColor(NewRenewActivity.this, R.color.colorGrey));
                                v1.setVisibility(View.VISIBLE);
                                v0.setVisibility(View.INVISIBLE);
                                women.setText(Html.fromHtml("<b>Women</b>"));
                                men.setText("Men");
                            }

                            init(position);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(Call<ShirtResponce> call, Throwable t) {
                        t.printStackTrace();

                    }
                });

    }

    private void initData() {
        ApiUtils.getAlterationService()
                .getShirtBody("alter/shirt-bodies")
                .enqueue(new Callback<ShirtResponce>() {
                    @Override
                    public void onResponse(Call<ShirtResponce> call, Response<ShirtResponce> response) {
                        /*try {
                            fabricRv.setAdapter(new ShirtsItemsAdapter(response.body().data.shirts.men));
                            Log.e("TAG", "" + response.body());
//                            Toast.makeText(NewRenewActivity.this,""+response.body().data.shirts.men,Toast.LENGTH_LONG).show();
                            Glide.with(NewRenewActivity.this).load(response.body().data.shirts.men.get(0).image).into(itemImages[0]);
                            Glide.with(NewRenewActivity.this).load(response.body().data.shirts.men.get(0).image).into(itemImagesgarments[0]);
                        } catch (Exception e) {
                            e.printStackTrace();

                        }*/
                        try {
                            reset();
                            NewRenewActivity.this.response = response;
                            men.setOnClickListener(NewRenewActivity.this);
                            women.setOnClickListener(NewRenewActivity.this);

                            if (position == 0) {
                                men.setTextColor(ContextCompat.getColor(NewRenewActivity.this, R.color.colorPrimary));
                                women.setTextColor(ContextCompat.getColor(NewRenewActivity.this, R.color.colorGrey));
                                v0.setVisibility(View.VISIBLE);
                                v1.setVisibility(View.INVISIBLE);
                                men.setText(Html.fromHtml("<b>Men</b>"));
                                women.setText("Women");
                            } else if (position == 1) {

                                women.setTextColor(ContextCompat.getColor(NewRenewActivity.this, R.color.colorPrimary));
                                men.setTextColor(ContextCompat.getColor(NewRenewActivity.this, R.color.colorGrey));
                                v1.setVisibility(View.VISIBLE);
                                v0.setVisibility(View.INVISIBLE);
                                women.setText(Html.fromHtml("<b>Women</b>"));
                                men.setText("Men");
                            }

                            init(position);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(Call<ShirtResponce> call, Throwable t) {
                        t.printStackTrace();

                    }
                });

    }

    public class ShirtsItemsAdapter extends RecyclerView.Adapter<ShirtsItemsAdapter.ShirtsViewHolder> {
        List<ShirtItems> data;

        public ShirtsItemsAdapter(List<ShirtItems> data) {
            this.data = data;
        }

        @Override
        public ShirtsItemsAdapter.ShirtsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new ShirtsItemsAdapter.ShirtsViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.repair_color_list_item, parent, false));
        }

        @Override
        public void onBindViewHolder(final ShirtsItemsAdapter.ShirtsViewHolder holder, int position) {
            try {

                final ShirtItems current = data.get(position);
                if (current != null) {
                    Glide.with(holder.itemView.getContext()).load(current.image).into(holder.img);
//                    holder.img.setTag(position);
                    holder.img.setOnClickListener(new View.OnClickListener() {

                        @Override
                        public void onClick(View v) {
                            // TODO Auto-generated method stub
//                            int pos = (Integer) v.getTag();
//                            ShirtItems h1 = data.get(pos);
                            Glide.with(holder.itemView.getContext()).load(current.image).into(itemImages[1]);
                            Glide.with(holder.itemView.getContext()).load(current.image).into(itemImagesgarments[1]);
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

        class ShirtsViewHolder extends RecyclerView.ViewHolder {
            ImageView img;

            public ShirtsViewHolder(View itemView) {
                super(itemView);
                img = (ImageView) itemView.findViewById(R.id.img);
            }
        }
    }

    @Override
    public void onClick(View view) {
        String a, b;
        /*modelsFrame.setVisibility(View.VISIBLE);
        hsc.setVisibility(View.GONE);*/
//        fabricRv.setVisibility(View.GONE);
        switch (view.getId()) {
            case R.id.sleeves:
                selected(sleevesTextView);
                fetchSleeves();
                break;
            case R.id.shirt:
                selected(shirtTextView);
                initData(fabric_id);
                break;
            case R.id.placket:
                selected(placketsTextView);
                fetchPlacket();
                break;
            case R.id.pocket:
                selected(pocketsTextView);
                fetchPocket();
                break;
            case R.id.buttons:
                selected(buttonTextView);
                fetchButtons();
                break;
            case R.id.cuffs:
                selected(cuffTextView);
                fetchCuffs();
                break;
            case R.id.collar:
                selected(collarTextView);
                fetchCollars();
                break;


            case R.id.women:
                a = "Women";
                b = "Men";
                if (genderFlag != 1)
                    showDialog(a, b, view);
                break;
            case R.id.men:
                a = "Men";
                b = "Women";

                if (genderFlag != 0)
                    showDialog(a, b, view);
                break;
        }
    }

    public void selected(TextView txtview) {
        shirtTextView.setTextColor(ContextCompat.getColor(NewRenewActivity.this, R.color.product_name_color));
        collarTextView.setTextColor(ContextCompat.getColor(NewRenewActivity.this, R.color.product_name_color));
        buttonTextView.setTextColor(ContextCompat.getColor(NewRenewActivity.this, R.color.product_name_color));
        cuffTextView.setTextColor(ContextCompat.getColor(NewRenewActivity.this, R.color.product_name_color));
        sleevesTextView.setTextColor(ContextCompat.getColor(NewRenewActivity.this, R.color.product_name_color));
        pocketsTextView.setTextColor(ContextCompat.getColor(NewRenewActivity.this, R.color.product_name_color));
        placketsTextView.setTextColor(ContextCompat.getColor(NewRenewActivity.this, R.color.product_name_color));

        txtview.setTextColor(ContextCompat.getColor(NewRenewActivity.this, R.color.colorPrimary));
    }

    private void reset() {
//        modelsFrame.setVisibility(View.VISIBLE);

//        fabricRv.setVisibility(View.GONE);
        DividerItemDecoration mDividerItemDecoration = new DividerItemDecoration(
                fabricRv.getContext(), DividerItemDecoration.HORIZONTAL
        );
        mDividerItemDecoration.setDrawable(ContextCompat.getDrawable(NewRenewActivity.this, R.drawable.divider));
        fabricRv.addItemDecoration(mDividerItemDecoration);
    }

    private void fetchSleeves() {

        ApiUtils.getAlterationService()
                .getRepairSleevesList("alter/sleeves")
                .enqueue(new Callback<SleeveResponse>() {
                    @Override
                    public void onResponse(Call<SleeveResponse> call, Response<SleeveResponse> response) {
                        try {

                            reset();
                            if (sleevesModelAdapter == null)
                                sleevesModelAdapter = new SleevesModelAdapter(response.body().data, itemImages[0], itemImagesgarments[0]);
                            fabricRv.setAdapter(sleevesModelAdapter);
                           /* modelsFrame.setVisibility(View.VISIBLE);
                            hsc.setVisibility(View.GONE);*/

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(Call<SleeveResponse> call, Throwable t) {
                        t.printStackTrace();

                    }
                });

    }


    private void fetchButtons() {

        ApiUtils.getAlterationService()
                .getRepairButtonsList("alter/buttons")
                .enqueue(new Callback<ButtonResponse>() {
                    @Override
                    public void onResponse(Call<ButtonResponse> call, Response<ButtonResponse> response) {
                        try {

                            reset();
                            if (buttonsModelsAdapter == null)
                                buttonsModelsAdapter = new ButtonsModelsAdapter(response.body().data, itemImages[4], itemImagesgarments[4]);
                            fabricRv.setAdapter(buttonsModelsAdapter);
                            /*modelsFrame.setVisibility(View.VISIBLE);
                            hsc.setVisibility(View.GONE);*/

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(Call<ButtonResponse> call, Throwable t) {
                        t.printStackTrace();

                    }
                });

    }

    private void fetchCollars() {

        Log.v("mtest", "fetchCollars");


        ApiUtils.getAlterationService()
                .getRepairCollarsList("alter/collars")
                .enqueue(new Callback<CollarResponse>() {
                    @Override
                    public void onResponse(Call<CollarResponse> call, Response<CollarResponse> response) {

                       /* Log.v("mtest", "onResponse");
                        try {

                            reset();
                            if (collarModelsAdapter == null)
                                collarModelsAdapter = new CollarModelsAdapter(response.body().data, modelsRvimage,fabricRv, itemImages[3], itemImagesgarments[3]);
                            modelsRv.setAdapter(collarModelsAdapter);
                            modelsFrame.setVisibility(View.VISIBLE);
                            hsc.setVisibility(View.GONE);

                        } catch (Exception e) {
                            e.printStackTrace();
                        }*/
                        try {

                            reset();
                            if (collarModelsAdapter == null)
                                collarModelsAdapter = new CollarModelsAdapter(response.body().data, itemImages[6], itemImagesgarments[6]);
                            fabricRv.setAdapter(collarModelsAdapter);
                           /* modelsFrame.setVisibility(View.VISIBLE);
                            hsc.setVisibility(View.GONE);*/

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(Call<CollarResponse> call, Throwable t) {
                        t.printStackTrace();

                        Log.v("mtest", "onFailure");
                    }
                });
    }

    private void fetchCuffs() {

        Log.v("mtest", "fetchCuffs");


        ApiUtils.getAlterationService()
                .getRepairCuffsList("alter/cuffs")
                .enqueue(new Callback<CuffsResponse>() {
                    @Override
                    public void onResponse(Call<CuffsResponse> call, Response<CuffsResponse> response) {

                        Log.v("mtest", "onResponse");
                        try {
                            reset();

                            if (cuffModelsAdapter == null)
                                cuffModelsAdapter = new CuffModelsAdapter(response.body().data, itemImages[5], itemImagesgarments[5]);
                            fabricRv.setAdapter(cuffModelsAdapter);

                           /* modelsFrame.setVisibility(View.VISIBLE);
                            hsc.setVisibility(View.GONE);*/
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(Call<CuffsResponse> call, Throwable t) {
                        t.printStackTrace();

                        Log.v("mtest", "onFailure");
                    }
                });
    }

    private void fetchPocket() {

        ApiUtils.getAlterationService()
                .getRepairPocketsList("alter/pockets")
                .enqueue(new Callback<PocketResponse>() {
                    @Override
                    public void onResponse(Call<PocketResponse> call, Response<PocketResponse> response) {
                        try {

                            reset();
                            if (pocketsModelAdapter == null)
                                pocketsModelAdapter = new PocketsModelAdapter(response.body().data, itemImages[3], itemImagesgarments[3]);
                            fabricRv.setAdapter(pocketsModelAdapter);
                           /* modelsFrame.setVisibility(View.VISIBLE);
                            hsc.setVisibility(View.GONE);*/

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(Call<PocketResponse> call, Throwable t) {
                        t.printStackTrace();

                    }
                });

    }

    private void fetchPlacket() {

        ApiUtils.getAlterationService()
                .getRepairPlacketsList("alter/plackets")
                .enqueue(new Callback<PlacketResponse>() {
                    @Override
                    public void onResponse(Call<PlacketResponse> call, Response<PlacketResponse> response) {
                        try {

                            reset();
                            if (placketsModelAdapter == null)
                                placketsModelAdapter = new PlacketsModelAdapter(response.body().data, itemImages[2], itemImagesgarments[2]);
                            fabricRv.setAdapter(placketsModelAdapter);
                           /* modelsFrame.setVisibility(View.VISIBLE);
                            hsc.setVisibility(View.GONE);*/

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(Call<PlacketResponse> call, Throwable t) {
                        t.printStackTrace();

                    }
                });

    }

    class CustomShirtAdapter extends RecyclerView.Adapter<CustomShirtAdapter.CustomViewHolder> {
        List<String> use_imagePath, display_imagePath, name, style, id, price;

        public CustomShirtAdapter(List<String> use_imagePath, List<String> display_imagePath, List<String> name, List<String> style, List<String> id, List<String> price) {
            this.use_imagePath = use_imagePath;
            this.display_imagePath = display_imagePath;
            this.name = name;
            this.style = style;
            this.id = id;
            this.price = price;
        }

        @Override
        public CustomShirtAdapter.CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new CustomShirtAdapter.CustomViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_design_listitem, parent, false));
        }

        @Override
        public void onBindViewHolder(final CustomShirtAdapter.CustomViewHolder holder, int position) {
            try {

                if (name != null) {
                    holder.txtcustomtitle.setText(name.get(position));
                    holder.txtcustomsubtitle.setText(price.get(position));
                    Glide.with(holder.itemView.getContext()).load(display_imagePath.get(position)).into(holder.imgcustom);
                    /*total_price = total_price + Double.parseDouble(price.get(position));
                    txtpay.setText("Proceed to pay ₹ " + total_price);*/
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

        @Override
        public int getItemCount() {
            return name != null ? name.size() : 0;
        }

        class CustomViewHolder extends RecyclerView.ViewHolder {
            ImageView imgcustom;
            TextView txtcustomtitle, txtcustomsubtitle;

            public CustomViewHolder(View itemView) {
                super(itemView);
                txtcustomtitle = (TextView) itemView.findViewById(R.id.txtcustomtitle);
                txtcustomsubtitle = (TextView) itemView.findViewById(R.id.txtcustomsubtitle);
                imgcustom = (ImageView) itemView.findViewById(R.id.imgcustom);
            }
        }
    }

    public void showDialog(String a, String b, final View view) {
        String content = "Adding alter for " + a + " in the cart would discard options selected for " + b + ". Would you like to continue ?";
        new MaterialDialog.Builder(this)
                .title("Replace Cart Item ?")
                .content(content)
                .positiveText("Yes")
                .negativeText("No")
                .positiveColor(ContextCompat.getColor(this, R.color.colorPrimary))
                .negativeColor(ContextCompat.getColor(this, R.color.colorPrimary))
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        ApiUtils.getAlterationService()
                                .clearCart(Helper.getLocalValue(NewRenewActivity.this, "userid"))
                                .enqueue(new Callback<ClearCartResponse>() {
                                    @Override
                                    public void onResponse(Call<ClearCartResponse> call, Response<ClearCartResponse> response) {
                                        try {

                                            switch (view.getId()) {
                                                case R.id.men:
                                                    if (genderFlag != 0) {
                                                        genderFlag = 0;
                                                        selected(shirtTextView);
//                                                        initData(0);


                                                    }

                                                    break;

                                                case R.id.women:

                                                    if (genderFlag != 1) {
                                                        genderFlag = 1;
                                                        selected(shirtTextView);
//                                                        initData(1);


                                                    }
                                                    break;
                                            }


                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<ClearCartResponse> call, Throwable t) {
                                        t.printStackTrace();
                                    }
                                });
                    }
                }).show()
        ;

    }

    private void init(int genderFlag) {


        if (NewRenewActivity.this.response != null) {
            try {
                /*if (genderFlag == 0) {
                    fabricRv.setAdapter(new ShirtsItemsAdapter(response.body().data.shirts.men));
                    Log.e("TAG", "" + response.body());
//                            Toast.makeText(NewRenewActivity.this,""+response.body().data.shirts.men,Toast.LENGTH_LONG).show();
                    Glide.with(NewRenewActivity.this).load(response.body().data.shirts.men.get(0).image).into(itemImages[1]);
                    Glide.with(NewRenewActivity.this).load(response.body().data.shirts.men.get(0).image).into(itemImagesgarments[1]);

                } else {
                    fabricRv.setA dapter(new ShirtsItemsAdapter(response.body().data.shirts.women));
                    Log.e("TAG", "" + response.body());
//                            Toast.makeText(NewRenewActivity.this,""+response.body().data.shirts.men,Toast.LENGTH_LONG).show();
                    Glide.with(NewRenewActivity.this).load(response.body().data.shirts.women.get(0).image).into(itemImages[1]);
                    Glide.with(NewRenewActivity.this).load(response.body().data.shirts.women.get(0).image).into(itemImagesgarments[1]);

                }*/
                /*fabricRv.setAdapter(new ShirtsItemsAdapter(response.body().data.shirts));
                Log.e("TAG", "" + response.body());
                Glide.with(NewRenewActivity.this).load(response.body().data.shirts.get(0).image).into(itemImages[1]);
                Glide.with(NewRenewActivity.this).load(response.body().data.shirts.get(0).image).into(itemImagesgarments[1]);*/

                /*total_price = Double.parseDouble(response.body().data.shirt_bodies.get(0).price) + 200;
                shirt_price = Double.parseDouble(response.body().data.shirt_bodies.get(0).price) + 200;*/


                /*txtcustomsubtvitle.setText(response.body().data.shirt_bodies.get(0).price);
                Glide.with(NewRenewActivity.this).load(response.body().data.shirt_bodies.get(0).image).into(itemImages[1]);
                Glide.with(NewRenewActivity.this).load(response.body().data.shirt_bodies.get(0).image).into(itemImagesgarments[1]);
                txtprice.setText("₹" + response.body().data.shirt_bodies.get(0).price);
                NewRenewActivity.use_imagePath.add(response.body().data.shirt_bodies.get(0).image);
                NewRenewActivity.display_imagePath.add(response.body().data.shirt_bodies.get(0).image);
                NewRenewActivity.name.add(response.body().data.shirt_bodies.get(0).fabric.name);
                NewRenewActivity.style.add(response.body().data.shirt_bodies.get(0).shirt_style);
                NewRenewActivity.id.add(response.body().data.shirt_bodies.get(0).id);
                NewRenewActivity.price.add(response.body().data.shirt_bodies.get(0).price);
                NewRenewActivity.Cat.add("Shirt");*/
                if (shirtsModelAdapter == null)
                    shirtsModelAdapter = new ShirtsModelAdapter(response.body().data.shirt_bodies, itemImages[1], itemImagesgarments[1]);
                fabricRv.setAdapter(shirtsModelAdapter);
            } catch (Exception e) {
                e.printStackTrace();

            }

//            setUpUI(dataMap);
        }
    }

    public class PreviousItemsAdapter extends RecyclerView.Adapter<PreviousItemsAdapter.PreviousViewHolder> {
        List<ColorItem> data;

        public PreviousItemsAdapter(List<ColorItem> data) {
            this.data = data;
        }

        @Override
        public PreviousItemsAdapter.PreviousViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new PreviousItemsAdapter.PreviousViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.repair_color_list_item, parent, false));
        }

        @Override
        public void onBindViewHolder(PreviousItemsAdapter.PreviousViewHolder holder, int position) {
            try {

                final ColorItem current = data.get(position);
                if (current != null) {

//                    holder.img.setBackgroundColor(Color.parseColor(current.image));
//                    Glide.with(holder.itemView.getContext()).load(current.image).into(holder.img);

                    Glide.with(NewRenewActivity.this)
                            .load(current.image)
                            /*.apply(RequestOptions.centerCropTransform())*/
                            .into(holder.img);

                    //                    URL url = new URL(current.image);
//                    Bitmap bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream());
//                    holder.img.setImageBitmap(bmp);

//                    holder.img.setTag(position);
                    holder.img.setOnClickListener(new View.OnClickListener() {

                        @Override
                        public void onClick(View v) {
                            // TODO Auto-generated method stub
//                            int pos = (Integer) v.getTag();
//                            ColorItem h1 =data.get(pos);
//                            String colorCode = h1.code;
                            /*Intent in = new Intent(SelectGarments.this, NewRenewActivity.class);
                            in.putExtra("colorcode", current.code);
                            startActivity(in);*/
                            selected(shirtTextView);
                            fabric_id = current.id;
                            /*total_price = Double.parseDouble(current.price) + 200;
                            shirt_price = Double.parseDouble(current.price) + 200;*/
                            initData(current.id);

                            bottomSheetBehaviorColor.setState(BottomSheetBehavior.STATE_COLLAPSED);
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

        class PreviousViewHolder extends RecyclerView.ViewHolder {
            ImageView img;
            RelativeLayout rlborder;

            public PreviousViewHolder(View itemView) {
                super(itemView);
                img = (ImageView) itemView.findViewById(R.id.img);
                rlborder = (RelativeLayout) itemView.findViewById(R.id.rlborder);

                rlborder.setLayoutParams(parms);
                // Set the layout parameters for TextView widget
//                RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(
//                        ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT
//                );
//                rlborder.setLayoutParams(lp);
//
//                // Get the TextView LayoutParams
//                RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) rlborder.getLayoutParams();
//
//                // Set the width of TextView widget (item of GridView)
//                params.width = getPixelsFromDPs(SelectGarments.this, 120);
//
//                // Set the TextView height (GridView item/row equal height)
//                params.height = getPixelsFromDPs(SelectGarments.this,50);
//
//                // Set the TextView layout parameters
//                rlborder.setLayoutParams(params);
//
//                // Display TextView text in center position
//                rlborder.setGravity(Gravity.CENTER);

            }
        }
    }

    @Override
    public void onBackPressed() {
        if (bottomSheetBehaviorstate == 3) {
            bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
            bottomSheetBehaviorColor.setState(BottomSheetBehavior.STATE_COLLAPSED);
        } else {
            super.onBackPressed();
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

    class CreateRenew extends AsyncTask<Void, Void, String> {
        ProgressDialog pdia;
        String message = "";

        @Override
        protected void onPreExecute() {

            super.onPreExecute();
            pdia = new ProgressDialog(NewRenewActivity.this);
            pdia.setMessage("Please wait...");
            pdia.setCancelable(false);
            pdia.show();

        }

        @Override
        protected String doInBackground(Void... params) {

            String responseString = null;

            HttpClient httpclient = new DefaultHttpClient();

//            HttpPost httppost = new HttpPost("http://35.186.158.253/api/v1/customize/create-customize");

            HttpPost httppost = new HttpPost("http://35.199.31.212/api/v1/customize/create-customize");
//            File sourceFile = new File(source_file);

            try {

                MultipartEntityBuilder builder = MultipartEntityBuilder.create();
                builder.setMode(HttpMultipartMode.STRICT);
                File file = new File(renew_shirt_path);
                FileBody fileBody = new FileBody(file);

                builder.addPart("user_id", new StringBody(Helper.getLocalValue(NewRenewActivity.this, "userid")));
                builder.addPart("image", fileBody);
                builder.addPart("type", new StringBody(customize_type));
                if (customize_type.equalsIgnoreCase("update")) {
                    builder.addPart("customize_id", new StringBody(customize_id));
                }
                JSONObject jobj_id;
                JSONArray qhis_jar = new JSONArray();
                for (int i = 0; i < name.size(); i++) {
                    jobj_id = new JSONObject(); //
                    jobj_id.put("type", Cat.get(i));
                    jobj_id.put("name", name.get(i));
                    jobj_id.put("style", style.get(i));
                    jobj_id.put("id", id.get(i));
                    jobj_id.put("price", price.get(i));
                    jobj_id.put("useimagePath", use_imagePath.get(i));
                    jobj_id.put("displayimagePath", display_imagePath.get(i));
                    qhis_jar.put(jobj_id);
                }
//                builder.addPart("renew_details", new StringBody(new Gson().toJson(qhis_jar)));
                builder.addTextBody("customize_details", qhis_jar.toString());

                HttpEntity entity = builder.build();

                httppost.setEntity(entity);

                // Making server call
                HttpResponse response = httpclient.execute(httppost);
                HttpEntity r_entity = response.getEntity();

                int statusCode = response.getStatusLine().getStatusCode();

                // Server response
                responseString = EntityUtils.toString(r_entity);
                JSONObject jobject = new JSONObject(responseString);
                String status_code = jobject.getString("status_code");

                if (status_code.equalsIgnoreCase("200")) {
                    message = jobject.getString("message");
                } else {
                    message = "Error occurs";
                }


            } catch (ClientProtocolException e) {

                responseString = e.toString();

            } catch (IOException e) {

                responseString = e.toString();
            } catch (Exception e) {
                responseString = "Error occurred! Http Status Code:";
            }

            return responseString;
        }

        @Override
        protected void onPostExecute(String result) {

            pdia.dismiss();
            super.onPreExecute();
            Toast.makeText(NewRenewActivity.this, message, Toast.LENGTH_LONG).show();

        }
    }


}
