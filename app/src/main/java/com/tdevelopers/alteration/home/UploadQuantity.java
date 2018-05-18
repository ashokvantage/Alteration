package com.tdevelopers.alteration.home;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.tdevelopers.alteration.R;

public class UploadQuantity extends AppCompatActivity implements View.OnClickListener {
    RelativeLayout rl_inc, rl_dec, rlnext;
    TextView txtquantity, txttitle;
    int quantity = 1;
    String type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_quantity);
        rl_inc = (RelativeLayout) findViewById(R.id.rlinc);
        rl_dec = (RelativeLayout) findViewById(R.id.rldec);
        rlnext = (RelativeLayout) findViewById(R.id.rlnext);
        txtquantity = (TextView) findViewById(R.id.txtquantity);
        txttitle = (TextView) findViewById(R.id.txttitle);
        rl_inc.setOnClickListener(this);
        rl_dec.setOnClickListener(this);
        rlnext.setOnClickListener(this);
        type = getIntent().getStringExtra("type");
        if (type.equalsIgnoreCase("ALTER")) {
            txttitle.setText("How many " + HomeActivity.cur_image.garment_type + " do you want to get Altered?");
        } else {
            /*txttitle.setText("How many " + HomeActivity.cur_image.garment_type + " do you want to get Repaired ?");*/
            txttitle.setText("How many clothes are you getting repaired?");
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rlinc:
                quantity = quantity + 1;
                txtquantity.setText("" + quantity);
                break;
            case R.id.rldec:
                if (quantity > 1) {
                    quantity = quantity - 1;
                    txtquantity.setText("" + quantity);
                }
                break;
            case R.id.rlnext:
                Intent in = new Intent(UploadQuantity.this, UploadPhotosActivity.class);
                in.putExtra("quantity", quantity);
                in.putExtra("action_type", "Add");
                in.putExtra("type", type);
                startActivity(in);
                UploadQuantity.this.finish();
                break;
        }

    }
}
