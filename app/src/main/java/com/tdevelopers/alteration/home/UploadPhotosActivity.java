package com.tdevelopers.alteration.home;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.os.StrictMode;
import android.os.Vibrator;
import android.provider.MediaStore;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatDialog;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.tdevelopers.alteration.Cart.Checkout.DeleteCartResponce;
import com.tdevelopers.alteration.Cart.Checkout.Support_Image;
import com.tdevelopers.alteration.DBHelper;
import com.tdevelopers.alteration.Helper;
import com.tdevelopers.alteration.Models.Objects.Image;
import com.tdevelopers.alteration.Models.Remote.ApiUtils;
import com.tdevelopers.alteration.MyApp;
import com.tdevelopers.alteration.R;
import com.tdevelopers.alteration.UserPackage.AddressPackage.Address;
import com.tdevelopers.alteration.UserPackage.MeasurementsPackage.AddMeasurementResponse;
import com.tdevelopers.alteration.Utility;
import com.tdevelopers.alteration.myaccount.AddressActivity;

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

public class UploadPhotosActivity extends AppCompatActivity {
    RecyclerView photosRv;
    RelativeLayout rl_submit;
    TextView add_image_quantity, txt_title;
    private GridLayoutManager layoutManager;
    int s_i_count;
    public CustomAdapter customAdapter;
    private String realPath;
    private int REQUEST_CAMERA = 1;
    File source_file = null;
    String image_path;
    List<File> Source_file_List = new ArrayList<>();
    DBHelper mydb;
    Cursor c;
    ArrayList<String> p_id_array_list;
    CustomAdapter.CustomViewHolder cur_holder;
    int clickedPosition;
    String action_type, type;

    String note_list = "", note_list_new = "";
    TextView txttype;
    AppCompatDialog progressDialog;
    Vibrator vibe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_photos);
        photosRv = (RecyclerView) findViewById(R.id.photosRv);
        rl_submit = (RelativeLayout) findViewById(R.id.rl_submit);
        add_image_quantity = (TextView) findViewById(R.id.add_image_quantity);
        txt_title = (TextView) findViewById(R.id.txt_title);
        txttype = (TextView) findViewById(R.id.txttype);
        mydb = new DBHelper(this);
        progressDialog = new AppCompatDialog(UploadPhotosActivity.this);
        progressDialog.setCancelable(false);
        progressDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        progressDialog.setContentView(R.layout.progress_loading);
        progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        progressDialog.setCancelable(true);
        progressDialog.setCanceledOnTouchOutside(true);
        vibe = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        rl_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                p_id_array_list = mydb.getalldata(HomeActivity.cur_image.id);
                if (p_id_array_list.size() < 1) {
                    Toast.makeText(UploadPhotosActivity.this, "Please add at least one image", Toast.LENGTH_SHORT).show();
                } else {
//                    uploadpictureBootomSheet.setState(BottomSheetBehavior.STATE_COLLAPSED);
                    UploadPhoto uploadProfilePhoto = new UploadPhoto();
                    uploadProfilePhoto.execute();
                }
            }
        });
        s_i_count = getIntent().getIntExtra("quantity", 0);
        action_type = getIntent().getStringExtra("action_type");
        type = getIntent().getStringExtra("type");
        p_id_array_list = mydb.getalldata(HomeActivity.cur_image.id);
        if (action_type.equalsIgnoreCase("Add")) {
            for (int i = 0; i < s_i_count; i++) {
                Drawable myDrawable = getResources().getDrawable(R.mipmap.pic);
                Bitmap myLogo = ((BitmapDrawable) myDrawable).getBitmap();
                HomeActivity.pics.add(myLogo);
                HomeActivity.places.add("first");
            }
//            txttype.setText("UPLOAD IMAGES");
            txttype.setText("Continue");
            add_image_quantity.setText("" + p_id_array_list.size());
        } else {
//            txttype.setText("UPDATE IMAGES");
            txttype.setText("Continue");
            add_image_quantity.setText("" + p_id_array_list.size());
        }

        layoutManager = new GridLayoutManager(this, 2);


        photosRv.setHasFixedSize(true);

        photosRv.setLayoutManager(layoutManager);

        // specify an adapter (see also next example)
        if (type.equalsIgnoreCase("ALTER")) {
            txt_title.setText("Upload " + HomeActivity.cur_image.garment_type + " Photos you want to Get Altered.");
        } else {
            /*txt_title.setText("Upload " + HomeActivity.cur_image.garment_type + " Photos you want to Get Repaired..");*/
            txt_title.setText("Upload photos of clothes you are getting repaired.");
        }

        customAdapter = new CustomAdapter(HomeActivity.pics, HomeActivity.places, s_i_count);
        photosRv.setAdapter(customAdapter);
    }

    public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.CustomViewHolder> {

        public int count;
        public ArrayList<Bitmap> image;
        public ArrayList<String> places;

        public CustomAdapter(ArrayList<Bitmap> image, ArrayList<String> places, int count) {
            this.image = image;
            this.places = places;
            this.count = count;
        }

        @Override
        public CustomAdapter.CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new CustomAdapter.CustomViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_city_card, parent, false));
        }

        @Override
        public void onBindViewHolder(final CustomAdapter.CustomViewHolder holder, final int position) {
            try {

                BitmapDrawable ob = new BitmapDrawable(Resources.getSystem(), image.get(position));
                holder.slider_imageView.setBackgroundDrawable(ob);
                if (places.get(position).equalsIgnoreCase("first")) {
                    holder.slide_edt.setText("");
                } else {
                    if (places.get(position).equalsIgnoreCase("NA")) {
                        holder.slide_edt.setText("");
                    } else {
                        holder.slide_edt.setText(places.get(position));
                    }
                }

                /* txt_title.setText("Upload "+cur_image.name +" Photos you want to Get Altered..");*/
                if (action_type.equalsIgnoreCase("Update")) {
                    p_id_array_list = mydb.getalldata(HomeActivity.cur_image.id);
                    if (p_id_array_list.size() > position) {
                        holder.img_close.setVisibility(View.VISIBLE);
                        holder.img_camera.setVisibility(View.GONE);
                        holder.txt_p_type.setVisibility(View.GONE);
                        holder.slide_edt.setHint("Any Instructions for us? (Optional)");
                    } else {
                        holder.img_close.setVisibility(View.GONE);
                        holder.img_camera.setVisibility(View.VISIBLE);
                        holder.txt_p_type.setVisibility(View.VISIBLE);
                        holder.txt_p_type.setText("Add Image");// + HomeActivity.cur_image.garment_type + " Image");
                        holder.slide_edt.setHint("Enter specifications (Optional)");
                    }
                } else {
                    p_id_array_list = mydb.getalldatawithposition(HomeActivity.cur_image.id, position);
                    if (p_id_array_list.size() > 0) {
                        holder.img_close.setVisibility(View.VISIBLE);
                        holder.img_camera.setVisibility(View.GONE);
                        holder.txt_p_type.setVisibility(View.GONE);
                        holder.slide_edt.setHint("Any Instructions for us? (Optional)");
                    } else {
                        holder.img_close.setVisibility(View.GONE);
                        holder.img_camera.setVisibility(View.VISIBLE);
                        holder.txt_p_type.setVisibility(View.VISIBLE);
                        holder.txt_p_type.setText("Add Image");// + HomeActivity.cur_image.garment_type + " Image");
                        holder.slide_edt.setHint("Enter specifications (Optional)");
                    }
                }


                holder.slider_imageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        if (holder.img_close.getVisibility() == View.GONE) {
                            cur_holder = holder;
                            clickedPosition = position;
                            selectImage(HomeActivity.cur_image, holder);
                        } else {
                            Toast.makeText(UploadPhotosActivity.this, "Please delete first", Toast.LENGTH_LONG).show();
                        }
                    }
                });

                holder.img_close.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(UploadPhotosActivity.this);
                        builder.setMessage("Do you want to delete the image?")
                                .setCancelable(false)
                                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        clickedPosition = position;
                                        if (HomeActivity.cur_image.product_id != null) {
                                            delete_image(HomeActivity.cur_image.product_id, HomeActivity.cur_image.supporting_images.get(position).id);
                                        } else {
                                            Drawable myDrawable = getResources().getDrawable(R.mipmap.pic);
                                            Bitmap myLogo = ((BitmapDrawable) myDrawable).getBitmap();
                                            HomeActivity.pics.set(clickedPosition, myLogo);
                                            HomeActivity.places.set(clickedPosition, "first");
                          /*  sliderAdapter = new NewAlterActivity.ForecastAdapter(pics, places, s_i_count);
                            initRecyclerView();
                            recyclerView.smoothScrollToPosition(clickedPosition);
                            recyclerView.scrollToPosition(clickedPosition);*/

                                            int a = mydb.deleteImagePath(HomeActivity.cur_image.id, clickedPosition);
                                            p_id_array_list = mydb.getalldata(HomeActivity.cur_image.id);
                                            add_image_quantity.setText("" + p_id_array_list.size());
                                            Source_file_List.clear();
                                            if (p_id_array_list.size() > 0) {
                                                c = mydb.getallAlterData(HomeActivity.cur_image.id);
                                                if (c != null) {
                                                    c.moveToFirst();
                                                }
                                                try {
                                                    do {
                                                        image_path = c.getString(c.getColumnIndex("p_imageurl"));
                                                        File file = new File(image_path);

                                                        Source_file_List.add(file);
                                                    } while (c.moveToNext());
                                                    customAdapter = new CustomAdapter(HomeActivity.pics, HomeActivity.places, s_i_count);
                                                    photosRv.setAdapter(customAdapter);

                                                } catch (Exception e) {

                                                }
                                            } else {
                                                customAdapter = new CustomAdapter(HomeActivity.pics, HomeActivity.places, s_i_count);
                                                photosRv.setAdapter(customAdapter);
                                            }
                                        }
                                    }
                                })
                                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        dialog.cancel();
                                    }
                                });
                        AlertDialog alert = builder.create();
                        alert.show();
                    }
                });

                holder.slide_edt.addTextChangedListener(new TextWatcher() {

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                    }

                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                    }

                    @Override
                    public void afterTextChanged(Editable s) {
                /*if (firstName.getText().toString().length <= 0) {
                    firstName.setError("Enter FirstName");
                } else {
                    firstName.setError(null);
                }*/
                        HomeActivity.places.set(position, holder.slide_edt.getText().toString());
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

        @Override
        public int getItemCount() {
            return count;
        }

        class CustomViewHolder extends RecyclerView.ViewHolder {

            ImageView slider_imageView, img_close, img_camera;
            EditText slide_edt;
            TextView txt_p_type;

            public CustomViewHolder(View itemView) {
                super(itemView);
                slider_imageView = (ImageView) itemView.findViewById(R.id.slider_image);
                img_close = (ImageView) itemView.findViewById(R.id.img_close);
                img_camera = (ImageView) itemView.findViewById(R.id.img_camera);
                slide_edt = (EditText) itemView.findViewById(R.id.slider_edt);
                txt_p_type = (TextView) itemView.findViewById(R.id.txt_p_type);
            }
        }
    }

    private void selectImage(final Image current, CustomAdapter.CustomViewHolder s_holder) {
        boolean result = Utility.checkPermission(UploadPhotosActivity.this);
        HomeActivity.cur_image = current;
        cameraIntent();

    }

    private void cameraIntent() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, REQUEST_CAMERA);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        realPath = null;
        if (resultCode == Activity.RESULT_OK) {
            Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
            Drawable drawable = new BitmapDrawable(getResources(), thumbnail);
            cur_holder.slider_imageView.setBackgroundDrawable(drawable);
            realPath = getCameraPath(data);
            onCaptureImageResult(data, realPath);
        }
    }

    public String getCameraPath(Intent data) {
        Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        thumbnail.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        source_file = new File(Environment.getExternalStorageDirectory(),
                System.currentTimeMillis() + ".jpg");

        FileOutputStream fo;
        try {
            source_file.createNewFile();
            fo = new FileOutputStream(source_file);
            fo.write(bytes.toByteArray());
            fo.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return source_file.toString();
    }

    private void onCaptureImageResult(Intent data, String realPath) {

        Source_file_List.clear();
        Bitmap bm = null;
        if (data != null) {
            try {
                bm = (Bitmap) data.getExtras().get("data");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        HomeActivity.pics.set(clickedPosition, bm);
        HomeActivity.places.set(clickedPosition, "");

        mydb.insertImagePath(HomeActivity.cur_image.id, realPath, clickedPosition);
        p_id_array_list = mydb.getalldata(HomeActivity.cur_image.id);
        add_image_quantity.setText("" + p_id_array_list.size());

       /* BitmapDrawable ob = new BitmapDrawable(Resources.getSystem(), realPath);
        cur_holder.slider_imageView.setBackgroundDrawable(ob);*/
//                    cur_alt_holder.slide_edt.setText(places.get(position));
        cur_holder.slide_edt.setText(HomeActivity.places.get(clickedPosition));
        txt_title.setText("Upload " + HomeActivity.cur_image.garment_type + " Photos you want to Get Altered");
        /* if (p_id_array_list.size() > clickedPosition) {*/
        cur_holder.img_close.setVisibility(View.VISIBLE);
        cur_holder.img_camera.setVisibility(View.GONE);
        cur_holder.txt_p_type.setVisibility(View.GONE);
        cur_holder.slide_edt.setHint("Any Instructions for us? (Optional)");

       /* c = mydb.getallAlterData(NewAlterActivity.cur_image.id);
        if (c != null) {
            c.moveToFirst();
        }
        try {
            do {
                image_path = c.getString(c.getColumnIndex("p_imageurl"));
               *//* File file = new File(image_path);

                Source_file_List.add(file);*//*

                BitmapDrawable ob = new BitmapDrawable(Resources.getSystem(), image_path);
                cur_alt_holder.slider_imageView.setBackgroundDrawable(ob);
//                    cur_alt_holder.slide_edt.setText(places.get(position));
                cur_alt_holder.slide_edt.setText(NewAlterActivity.places.get(clickedPosition));
                txt_title.setText("Upload " + NewAlterActivity.cur_image.garment_type + " Photos you want to Get Altered");
                *//* if (p_id_array_list.size() > clickedPosition) {*//*
                cur_alt_holder.img_close.setVisibility(View.VISIBLE);
                cur_alt_holder.img_camera.setVisibility(View.GONE);
                cur_alt_holder.txt_p_type.setVisibility(View.GONE);
                cur_alt_holder.slide_edt.setHint("Any Instructions for us? (Optional)");
                *//*} else {
                    cur_alt_holder.img_close.setVisibility(View.GONE);
                    cur_alt_holder.img_camera.setVisibility(View.VISIBLE);
                    cur_alt_holder.txt_p_type.setVisibility(View.VISIBLE);
                    cur_alt_holder.txt_p_type.setText("Add " + NewAlterActivity.cur_image.garment_type + " Image");
                    cur_alt_holder.slide_edt.setHint("Enter specifications (Optional)");
                }*//*

            } while (c.moveToNext());

        } catch (Exception e) {

        }*/
    }

    class UploadPhoto extends AsyncTask<Void, Void, String> {

        @Override
        protected void onPreExecute() {

            super.onPreExecute();
            if (progressDialog != null)
                progressDialog.show();
        }

        @Override
        protected String doInBackground(Void... params) {

            String responseString = null;
            HttpPost httppost;
            note_list = "";
            note_list_new = "";
            HttpClient httpclient = new DefaultHttpClient();
            if (action_type.equalsIgnoreCase("Add")) {
                httppost = new HttpPost("http://35.199.31.212/api/v1/user/add-to-cart");
            } else {
                httppost = new HttpPost("http://35.199.31.212/api/v1/user/update-to-cart");
            }
            try {

                MultipartEntityBuilder builder = MultipartEntityBuilder.create();
                builder.setMode(HttpMultipartMode.STRICT);

                if (action_type.equalsIgnoreCase("Add")) {
                    Source_file_List.clear();
                    c = mydb.getallAlterData(HomeActivity.cur_image.id);
                    if (c != null) {
                        c.moveToFirst();
                    }
                    try {
                        do {
                            image_path = c.getString(c.getColumnIndex("p_imageurl"));
                            File file = new File(image_path);

                            Source_file_List.add(file);

                        } while (c.moveToNext());

                    } catch (Exception e) {

                    }


                    for (int i = 0; i < Source_file_List.size(); i++) {
                        FileBody fileBody = new FileBody(Source_file_List.get(i));
//                    list[i]=fileBody;
                        builder.addPart("supporting_images[]", fileBody);
                    }
                    for (int i = 0; i < HomeActivity.places.size(); i++) {
                        if (HomeActivity.places.get(i).equalsIgnoreCase("first")) {

                        } else {
                            if (note_list.length() > 0) {
                                if (HomeActivity.places.get(i).equalsIgnoreCase(""))
                                    note_list = note_list + ",NA";
                                else
                                    note_list = note_list + "," + HomeActivity.places.get(i);
                            } else {
                                if (HomeActivity.places.get(i).equalsIgnoreCase(""))
                                    note_list = "NA";
                                else
                                    note_list = HomeActivity.places.get(i);
                            }
                        }
                    }
                    builder.addPart("supporting_notes", new StringBody(note_list.toString()));
                } else if (action_type.equalsIgnoreCase("Update")) {
                    JSONObject Images = new JSONObject();
                    try {
                        for (int i = 0; i < HomeActivity.cur_image.supporting_images.size(); i++) {
                            Images.put(HomeActivity.cur_image.supporting_images.get(i).id, HomeActivity.places.get(i));
                            if (note_list.length() > 0) {
                                if (HomeActivity.places.get(i).equalsIgnoreCase(""))
                                    note_list = note_list + ",NA";
                                else
                                    note_list = note_list + "," + HomeActivity.places.get(i);
                            } else {
                                if (HomeActivity.places.get(i).equalsIgnoreCase(""))
                                    note_list = "NA";
                                else
                                    note_list = HomeActivity.places.get(i);
                            }
                        }
                        for (int i = HomeActivity.cur_image.supporting_images.size(); i < Source_file_List.size(); i++) {
                            FileBody fileBody = new FileBody(Source_file_List.get(i));
//                    list[i]=fileBody;
                            builder.addPart("supporting_images[]", fileBody);
                            if (note_list.length() > 0) {
                                if (HomeActivity.places.get(i).equalsIgnoreCase(""))
                                    note_list = note_list + ",NA";
                                else
                                    note_list = note_list + "," + HomeActivity.places.get(i);
                            } else {
                                if (HomeActivity.places.get(i).equalsIgnoreCase(""))
                                    note_list = "NA";
                                else
                                    note_list = HomeActivity.places.get(i);
                            }

                            if (note_list_new.length() > 0) {
                                if (HomeActivity.places.get(i).equalsIgnoreCase(""))
                                    note_list_new = note_list_new + ",NA";
                                else
                                    note_list_new = note_list_new + "," + HomeActivity.places.get(i);
                            } else {
                                if (HomeActivity.places.get(i).equalsIgnoreCase(""))
                                    note_list_new = "NA";
                                else
                                    note_list_new = HomeActivity.places.get(i);
                            }


                        }
                        builder.addPart("image_id", new StringBody(Images.toString()));
                    } catch (JSONException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                    builder.addPart("supporting_notes", new StringBody(note_list_new.toString()));
                    builder.addPart("supporting_notes_all", new StringBody(note_list.toString()));
                }
//                builder.addPart("supporting_notes", new StringBody(note_list.toString()));
                builder.addPart("product_id", new StringBody(HomeActivity.cur_image.id));
                builder.addPart("user_id", new StringBody(Helper.getLocalValue(UploadPhotosActivity.this, "userid")));
                builder.addPart("type", new StringBody(type));
//                builder.addPart("action", new StringBody(cart_action));

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
                    String data = jobject.getString("data");
                    JSONObject jobject2 = new JSONObject(data);
                    if (jobject2.has("quantity")) {
                        String quantity = jobject2.getString("quantity");
                        HomeActivity.cur_image.quantitiy = Integer.parseInt(quantity);
                    } else {
                        HomeActivity.cur_image.quantitiy = 0;
                    }


                } else {
                    HomeActivity.cur_image.quantitiy = 0;
                }


                /*fetchCartItems();*/

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
            progressDialog.dismiss();
//            vibe.vibrate(250);
            vibe.vibrate(250);
            Source_file_List.clear();
            UploadPhotosActivity.this.finish();
            HomeActivity.checkout_page = true;
//            cur_alt_holder.quantity.setText(cur_image.quantitiy + "");
//            updateScreen(alters);
            super.onPreExecute();

        }
    }

    public void delete_image(String product_id, String image_id) {
        /*if (progressDialog != null)
            progressDialog.show();*/
        HashMap<String, Object> map = new HashMap<>();
        map.put("user_id", Helper.getLocalValue(UploadPhotosActivity.this, "userid"));
        map.put("product_id", product_id);
        map.put("type", type);
        map.put("image_id", image_id);
        ApiUtils.getAlterationService()
                .deleteImage(map)
                .enqueue(new Callback<DeleteCartResponce>() {
                    @Override
                    public void onResponse(Call<DeleteCartResponce> call, Response<DeleteCartResponce> response) {
                        try {

                            if (response.body() != null) {
                                /*Toast.makeText(holder.itemView.getContext(), response.body().message, Toast.LENGTH_SHORT).show();
                                data.remove(current);
                                notifyDataSetChanged();*/
                                HomeActivity.cur_image.quantitiy = response.body().d_cartData.quantity;
                                HomeActivity.cur_image.supporting_images = response.body().d_cartData.supporting_images;
                            } else {
                                HomeActivity.cur_image.quantitiy = 0;
                                HomeActivity.cur_image.supporting_images = new ArrayList<Support_Image>();
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        } finally {
                            /*if (NewAlterActivity.cur_image.quantitiy == 0)
                                cur_alt_holder.quantity.setText("+");
                            else
                                cur_alt_holder.quantity.setText(NewAlterActivity.cur_image.quantitiy + "");*/

                            int a = mydb.deleteImagePath(HomeActivity.cur_image.id, clickedPosition + 1);
                            p_id_array_list = mydb.getalldata(HomeActivity.cur_image.id);


                            /*updateScreen(alters);*/
                            UploadPhotosActivity.this.finish();
                            HomeActivity.checkout_page = true;
                        }
                    }

                    @Override
                    public void onFailure(Call<DeleteCartResponce> call, Throwable t) {
                        t.printStackTrace();

                    }
                });
    }

    @Override
    public void onBackPressed() {
        p_id_array_list = mydb.getalldata(HomeActivity.cur_image.id);
        if (p_id_array_list.size() > 0) {
            if (action_type.equalsIgnoreCase("Update")) {
                onSuperBackPressed();
            } else {
                AlertDialog.Builder builder = new AlertDialog.Builder(UploadPhotosActivity.this);
                builder.setMessage("Are you sure you want to exit uploading photos?")
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                onSuperBackPressed();
                                p_id_array_list = mydb.getalldata(HomeActivity.cur_image.id);
                                if (action_type.equalsIgnoreCase("Add")) {
                                    if (p_id_array_list.size() > 0) {
                                        mydb.deleteImage(HomeActivity.cur_image.id);
                                    }
                                }
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });
                AlertDialog alert = builder.create();
                alert.show();
            }

        } else {
            onSuperBackPressed();
        }
    }

    public void onSuperBackPressed() {
        super.onBackPressed();
    }
}
