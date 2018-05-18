package com.tdevelopers.alteration.Renew.home;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.tdevelopers.alteration.Models.Remote.ApiUtils;
import com.tdevelopers.alteration.R;
import com.tdevelopers.alteration.home.NewRenewActivity;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SelectGarments extends AppCompatActivity {
    RecyclerView garmentsRv;
    int width = 0, height = 0;
    RelativeLayout.LayoutParams parms;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_garments);
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
//        setTitle("Renew");
//        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
//        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                finish();
//            }
//        });

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
        garmentsRv.setLayoutManager(new GridLayoutManager(SelectGarments.this, 3));
        garmentsRv.setNestedScrollingEnabled(false);

        ApiUtils.getAlterationService()
                .getRepairColor("alter/colors")
                .enqueue(new Callback<ColorResponce>() {
                    @Override
                    public void onResponse(Call<ColorResponce> call, Response<ColorResponce> response) {
                        try {
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

//                    holder.img.setBackgroundColor(Color.parseColor(current.code));
//                    holder.img.setTag(position);
                    holder.img.setOnClickListener(new View.OnClickListener() {

                        @Override
                        public void onClick(View v) {
                            // TODO Auto-generated method stub
//                            int pos = (Integer) v.getTag();
//                            ColorItem h1 =data.get(pos);
//                            String colorCode = h1.code;
                            Intent in = new Intent(SelectGarments.this, NewRenewActivity.class);
//                            in.putExtra("colorcode", current.code);
                            startActivity(in);
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
    protected void onResume() {
        super.onResume();
    }

    // Method for converting DP value to pixels
    public static int getPixelsFromDPs(Activity activity, int dps) {
        Resources r = activity.getResources();
        int px = (int) (TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, dps, r.getDisplayMetrics()));
        return px;
    }
}
