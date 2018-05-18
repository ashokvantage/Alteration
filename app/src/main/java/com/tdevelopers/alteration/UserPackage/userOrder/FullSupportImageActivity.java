package com.tdevelopers.alteration.UserPackage.userOrder;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.tdevelopers.alteration.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FullSupportImageActivity extends Activity {
    ImageView img;
    String Imagepath;
    TextView txtclose;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_support_image);
        getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        img = (ImageView) findViewById(R.id.img);
        txtclose = (TextView) findViewById(R.id.txtclose);
        Imagepath = getIntent().getStringExtra("imagepath");
        Glide.with(FullSupportImageActivity.this).load(Imagepath).into(img);
        txtclose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FullSupportImageActivity.this.finish();
            }
        });
    }
}
