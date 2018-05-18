package com.tdevelopers.alteration.myaccount;

import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.tdevelopers.alteration.Helper;
import com.tdevelopers.alteration.Models.Remote.ApiUtils;
import com.tdevelopers.alteration.R;
import com.tdevelopers.alteration.home.NewAlterActivity;
import com.tdevelopers.alteration.home.NewRenewActivity;
import com.tdevelopers.alteration.UserPackage.RenewPackage.GetRenewResponce;
import com.tdevelopers.alteration.UserPackage.RenewPackage.RCustomize_details;
import com.tdevelopers.alteration.UserPackage.RenewPackage.RData;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyRenew extends AppCompatActivity {

    RecyclerView renewRv;
    TextView addnew;
    ImageView back;
    AppCompatDialog progressDialog;
    public static List<RCustomize_details> customize_Details = new ArrayList<>();
    public static String customize_Id;
    String event_rype, ImagePath;
    TextView txtempty;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        adjustFontScale(getResources().getConfiguration());
        setContentView(R.layout.activity_my_renew);
        renewRv = (RecyclerView) findViewById(R.id.renewRv);
        renewRv.setLayoutManager(new LinearLayoutManager(this));
        addnew = (TextView) findViewById(R.id.addnew);
        back = (ImageView) findViewById(R.id.back);
        txtempty = (TextView) findViewById(R.id.txtempty);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        progressDialog = new AppCompatDialog(MyRenew.this);
        progressDialog.setCancelable(false);
        progressDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        progressDialog.setContentView(R.layout.progress_loading);
        progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));



      /*  DividerItemDecoration mDividerItemDecoration = new DividerItemDecoration(this,
                LinearLayoutManager.VERTICAL);
        renewRv.addItemDecoration(mDividerItemDecoration);*/

        addnew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(MyRenew.this, NewRenewActivity.class);
                in.putExtra("fromaddress", true);
                startActivity(in);
                MyRenew.this.finish();
            }
        });
        loadAddresses();
    }

    @Override
    protected void onResume() {
        super.onResume();
        adjustFontScale(getResources().getConfiguration());
    }

    public void loadAddresses() {


        ApiUtils.getAlterationService()
                .getRenew("customize/list-customizes?user_id=" + Helper.getLocalValue(this, "userid"))
                .enqueue(new Callback<GetRenewResponce>() {
                    @Override
                    public void onResponse(Call<GetRenewResponce> call, Response<GetRenewResponce> response) {
                        try {

                            if (response.body() != null) {
                                if (response.body().data != null) {


                                    if (response.body().data != null && response.body().data.size() != 0) {

                                        renewRv.setAdapter(null);
                                        renewRv.setAdapter(new RenewListAdapter(response.body().data));
                                    }

                                }
                                txtempty.setVisibility(View.GONE);
                            } else {
                                txtempty.setVisibility(View.VISIBLE);
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(Call<GetRenewResponce> call, Throwable t) {

                        t.printStackTrace();
                    }
                });


    }


    public class RenewListAdapter extends RecyclerView.Adapter<RenewListAdapter.RenewViewHolder> {

        List<RData> data;

        public RenewListAdapter(List<RData> address) {
            this.data = address;
        }

        @Override
        public RenewViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new RenewViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.renw_list_item, parent, false));
        }

        @Override
        public void onBindViewHolder(final RenewViewHolder holder, int position) {
            try {

                final RData current = data.get(position);
                if (current != null) {
                    if (Helper.getLocalValue(MyRenew.this, "username") != null)
                        holder.name.setText(Helper.getLocalValue(MyRenew.this, "username") + "'s Renew #" + (position + 1));
                    else
                        holder.name.setText("Renew #" + (position + 1));

                    holder.txtprice.setText(" ₹ 600");
                    String description = "";
                    int count = 0;
                    for (int i = 0; i < current.customize_details.size(); i++) {
                        if (i == 0) {
                            description = current.customize_details.get(i).style;
                        } else if (i < 3) {
                            description = description + ", " + current.customize_details.get(i).style;
                        } else {
                            count = count + 1;
                        }

                    }
                    if (count > 0)
                        holder.desc.setText("Red Stripes with " + description + " & " + count + " more");
                    else
                        holder.desc.setText("Red Stripes with " + description);

                    if (data.size() == position + 1) {
                        holder.listdivider.setVisibility(View.GONE);
                    } else {
                        holder.listdivider.setVisibility(View.VISIBLE);
                    }
                    Glide.with(holder.itemView.getContext())
                            .load(current.image)
                            /*.apply(new RequestOptions()
                                    .placeholder(R.mipmap.launch_icon)
                                    .centerCrop()
                                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                                    .skipMemoryCache(true))*/
                            .into(holder.imgpic);

                    holder.imgshare.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                           /* final Intent intent = new Intent(Intent.ACTION_SEND);
                            intent.setType("text/plain");
                            final PackageManager pm = getPackageManager();
                            final List<ResolveInfo> matches = pm.queryIntentActivities(intent, 0);
                            ResolveInfo best = null;
                            for (final ResolveInfo info : matches)
                                if (info.activityInfo.packageName.endsWith(".gm") ||
                                        info.activityInfo.name.toLowerCase().contains("gmail"))
                                    best = info;
                            if (best != null)
                                intent.setClassName(best.activityInfo.packageName, best.activityInfo.name);
                            intent.putExtra(Intent.EXTRA_EMAIL, new String[]{});
                            intent.putExtra(Intent.EXTRA_SUBJECT, "Alteration Customizw Image");
                            intent.putExtra(Intent.EXTRA_TEXT, current.image);
                            startActivity(intent);*/

                            ImagePath = current.image;
                            event_rype = "share";
                            new DownloadTask()
                                    .execute(stringToURL(current.image));


                        }
                    });
                    holder.imgdownload.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
//                            Intent intent = new Intent(v.getContext(), EditAddressActivity.class);
//                            intent.putExtra("id", current);
//                            v.getContext().startActivity(intent);
                            event_rype = "download";
                            new DownloadTask()
                                    .execute(stringToURL(current.image));

                        }
                    });
                    holder.imgedit.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            customize_Details = current.customize_details;
                            customize_Id = current.customize_id;
                            Intent intent = new Intent(MyRenew.this, NewRenewActivity.class);
                            startActivity(intent);
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

        class RenewViewHolder extends RecyclerView.ViewHolder {

            TextView name, txttype, desc, txtprice;
            ImageView imgshare, imgdownload, imgedit, imgpic;
            View listdivider;

            public RenewViewHolder(View itemView) {
                super(itemView);
                name = (TextView) itemView.findViewById(R.id.name);
                txttype = (TextView) itemView.findViewById(R.id.txttype);
                desc = (TextView) itemView.findViewById(R.id.desc);
                txtprice = (TextView) itemView.findViewById(R.id.txtprice);
                imgshare = (ImageView) itemView.findViewById(R.id.imgshare);
                imgdownload = (ImageView) itemView.findViewById(R.id.imgdownload);
                imgedit = (ImageView) itemView.findViewById(R.id.imgedit);
                imgpic = (ImageView) itemView.findViewById(R.id.pic);
                listdivider = (View) itemView.findViewById(R.id.listdivider);
            }
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

    private class DownloadTask extends AsyncTask<URL, Void, Bitmap> {
        // Before the tasks execution
        protected void onPreExecute() {
            // Display the progress dialog on async task start
//            mProgressDialog.show();
            if (!event_rype.equalsIgnoreCase("share"))
                progressDialog.show();
        }

        // Do the task in background/non UI thread
        protected Bitmap doInBackground(URL... urls) {
            URL url = urls[0];
            HttpURLConnection connection = null;

            try {
                // Initialize a new http url connection
                connection = (HttpURLConnection) url.openConnection();

                // Connect the http url connection
                connection.connect();

                // Get the input stream from http url connection
                InputStream inputStream = connection.getInputStream();

                /*
                    BufferedInputStream
                        A BufferedInputStream adds functionality to another input stream-namely,
                        the ability to buffer the input and to support the mark and reset methods.
                */
                /*
                    BufferedInputStream(InputStream in)
                        Creates a BufferedInputStream and saves its argument,
                        the input stream in, for later use.
                */
                // Initialize a new BufferedInputStream from InputStream
                BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);

                /*
                    decodeStream
                        Bitmap decodeStream (InputStream is)
                            Decode an input stream into a bitmap. If the input stream is null, or
                            cannot be used to decode a bitmap, the function returns null. The stream's
                            position will be where ever it was after the encoded data was read.

                        Parameters
                            is InputStream : The input stream that holds the raw data
                                              to be decoded into a bitmap.
                        Returns
                            Bitmap : The decoded bitmap, or null if the image data could not be decoded.
                */
                // Convert BufferedInputStream to Bitmap object
                Bitmap bmp = BitmapFactory.decodeStream(bufferedInputStream);

                // Return the downloaded bitmap
                return bmp;

            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                // Disconnect the http url connection
                connection.disconnect();
            }
            return null;
        }

        // When all async task done
        protected void onPostExecute(Bitmap result) {
            // Hide the progress dialog
//            mProgressDialog.dismiss();
            progressDialog.dismiss();
            if (result != null) {
                // Display the downloaded image into ImageView
//                mImageView.setImageBitmap(result);

                // Save bitmap to internal storage
                Uri imageInternalUri = saveImageToInternalStorage(result);
                // Set the ImageView image from internal storage
//                mImageViewInternal.setImageURI(imageInternalUri);
                if (event_rype.equalsIgnoreCase("share")) {
                   /* String root = Environment.getExternalStorageDirectory().getAbsolutePath();
                    File myDir = new File(root + "/saved_images");
                    myDir.mkdirs();

                    String fname = "Image-alter" + ".jpg";
                    File file = new File(myDir, fname);*/


                    Intent intent = new Intent(Intent.ACTION_SEND);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
                    intent.setType("image/*");
                    intent.putExtra(Intent.EXTRA_EMAIL, new String[]{});
                    intent.putExtra(Intent.EXTRA_SUBJECT, "Alteration Customize Image");
                    intent.putExtra(Intent.EXTRA_TEXT, ImagePath);
//                    intent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(file));
                    intent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(new File(imageInternalUri.toString())));

                    intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                    try {
                        startActivity(Intent.createChooser(intent, "Share Image Using"));
                    } catch (android.content.ActivityNotFoundException ex) {
//                                ToastHelper.MakeShortText("Whatsapp have not been installed.");
                    }
                }
            } else {
                // Notify user that an error occurred while downloading image
                Toast.makeText(MyRenew.this, "Error", Toast.LENGTH_LONG).show();
            }
        }
    }

    // Custom method to convert string to url
    protected URL stringToURL(String urlString) {
        try {
            URL url = new URL(urlString);
            return url;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Custom method to save a bitmap into internal storage
    protected Uri saveImageToInternalStorage(Bitmap bitmap) {
        // Initialize ContextWrapper
       /* ContextWrapper wrapper = new ContextWrapper(getApplicationContext());

        // Initializing a new file
        // The bellow line return a directory in internal storage
        File file = wrapper.getDir("Images", MODE_PRIVATE);

        // Create a file to save the image
        file = new File(file, "UniqueFileName" + ".jpg");
*/
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);


        String root = Environment.getExternalStorageDirectory().getAbsolutePath();
        File myDir = new File(root + "/Download");
        myDir.mkdirs();
        long millis = System.currentTimeMillis();
        String fname = "alter" + "_" + millis + ".png";
        File file = new File(myDir, fname);


        try {
            // Initialize a new OutputStream
            OutputStream out = null;

            // If the output file exists, it can be replaced or appended to it
            out = new FileOutputStream(file);

            // Compress the bitmap
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, out);

            // Flushes the stream
            out.flush();

            // Closes the stream
            out.close();

        } catch (IOException e) // Catch the exception
        {
            e.printStackTrace();
        }

        // Parse the gallery image url to uri
        Uri savedImageURI = Uri.parse(file.getAbsolutePath());

        // Return the saved image Uri
        Toast.makeText(MyRenew.this, "Successfully downloaded \n" + savedImageURI, Toast.LENGTH_LONG).show();
        return savedImageURI;
    }
}

