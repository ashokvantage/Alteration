package com.tdevelopers.alteration.myaccount;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.tdevelopers.alteration.Helper;
import com.tdevelopers.alteration.NewLogin.NewLoginActivity;
import com.tdevelopers.alteration.NewLogin.SplashScreen;
import com.tdevelopers.alteration.R;
import com.tdevelopers.alteration.home.HomeActivity;
import com.tdevelopers.alteration.home.UploadPhotosActivity;

import java.util.ArrayList;
import java.util.List;

public class ContactUs extends AppCompatActivity {
    TextView txtcall, txtemail, txtlogout, txtterms;
    private static final int MAKE_CALL_PERMISSION_REQUEST_CODE = 1;
    ImageView back;
    public static final int REQUEST_ID_MULTIPLE_PERMISSIONS = 1;
    boolean result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_us);
        txtcall = (TextView) findViewById(R.id.txtcall);
        txtemail = (TextView) findViewById(R.id.txtemail);
        txtterms = (TextView) findViewById(R.id.txtterms);
        txtlogout = (TextView) findViewById(R.id.txtlogout);
        back = (ImageView) findViewById(R.id.back);
        result = checkAndRequestPermissions();
        txtcall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(ContactUs.this);
                builder.setMessage("Call Support Number ?")
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                try {
                                    result = checkAndRequestPermissions();
                                    if (result) {
                                        String dial = "tel:" + "9949521197";
                                        startActivity(new Intent(Intent.ACTION_CALL, Uri.parse(dial)));
                                    } else {
                                        Toast.makeText(ContactUs.this, "Permission Call Phone denied", Toast.LENGTH_SHORT).show();
                                    }
                                } catch (SecurityException e) {

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
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        txtterms.setPaintFlags(txtterms.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        txtterms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ContactUs.this, TermsConditions.class));
            }
        });
        txtemail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                final PackageManager pm = getPackageManager();
                final List<ResolveInfo> matches = pm.queryIntentActivities(intent, 0);
                ResolveInfo best = null;
                for (final ResolveInfo info : matches)
                    if (info.activityInfo.packageName.endsWith(".gm") ||
                            info.activityInfo.name.toLowerCase().contains("gmail")) best = info;
                if (best != null)
                    intent.setClassName(best.activityInfo.packageName, best.activityInfo.name);
                intent.putExtra(Intent.EXTRA_EMAIL, new String[]{"nireeshreddy@gmail.com"});
                intent.putExtra(Intent.EXTRA_SUBJECT, "Alterations Support   {" + Helper.getLocalValue(ContactUs.this, "username") + "," + Helper.getLocalValue(ContactUs.this, "userphone") + "}");
                intent.putExtra(Intent.EXTRA_TEXT, "");
                startActivity(intent);
            }
        });
        txtlogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Helper.storeLocally(ContactUs.this, "userid", null);
                Helper.storeLocally(ContactUs.this, "username", null);
                Helper.storeLocally(ContactUs.this, "userphone", null);
                Helper.storeLocally(ContactUs.this, "useremail", null);
                Helper.storeLocally(ContactUs.this, "usergender", null);
                startActivity(new Intent(ContactUs.this, NewLoginActivity.class));
                ContactUs.this.finishAffinity();
            }
        });
        if (checkPermission(android.Manifest.permission.CALL_PHONE)) {
        } else {
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.CALL_PHONE}, MAKE_CALL_PERMISSION_REQUEST_CODE);
        }
    }

    private boolean checkPermission(String permission) {
        return ContextCompat.checkSelfPermission(this, permission) == PackageManager.PERMISSION_GRANTED;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case MAKE_CALL_PERMISSION_REQUEST_CODE:
                if (grantResults.length > 0 && (grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
//                    Toast.makeText(this, "You can call the number by clicking on the button", Toast.LENGTH_SHORT).show();
                }
                return;
        }
    }

    private boolean checkAndRequestPermissions() {
        int phonecall = ContextCompat.checkSelfPermission(ContactUs.this, android.Manifest.permission.CALL_PHONE);
        List<String> listPermissionsNeeded = new ArrayList<>();

        if (phonecall != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.CALL_PHONE);
        }
        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(ContactUs.this, listPermissionsNeeded.toArray
                    (new String[listPermissionsNeeded.size()]), REQUEST_ID_MULTIPLE_PERMISSIONS);
            return false;
        }
        return true;
    }

}
