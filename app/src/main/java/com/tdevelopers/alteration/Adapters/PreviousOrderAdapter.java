package com.tdevelopers.alteration.Adapters;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.tdevelopers.alteration.R;
import com.tdevelopers.alteration.UserPackage.TransactionPackage.OrderInfo;
import com.tdevelopers.alteration.UserPackage.userOrder.OrderHistory;
import com.tdevelopers.alteration.myaccount.MyAccountActivity;
import com.tdevelopers.alteration.UserPackage.userOrder.OrderData;

import java.util.List;

/**
 * Created by ADMIN on 20-Feb-18.
 */

public class PreviousOrderAdapter extends RecyclerView.Adapter<PreviousOrderAdapter.PreviousViewHolder> {
    List<OrderData> data;
    MyAccountActivity activity;
    String desc = "";
    int alt_quan = 0, ref_quan = 0, ren_quan = 0, total_count = 0;

    public PreviousOrderAdapter(List<OrderData> data, MyAccountActivity activity) {
        this.data = data;
        this.activity = activity;
    }

    @Override
    public PreviousOrderAdapter.PreviousViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new PreviousOrderAdapter.PreviousViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.previous_item_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(PreviousOrderAdapter.PreviousViewHolder holder, int position) {
        try {

            final OrderInfo current = data.get(position).order_info;
            if (current != null) {
                alt_quan = 0;
                ref_quan = 0;
                ren_quan = 0;
                total_count = 0;
                for (int i = 0; i < current.cart_items.size(); i++) {

                    if (current.cart_items.get(i).type.equalsIgnoreCase("ALTER")) {
                        alt_quan = alt_quan + current.cart_items.get(i).quantity;
                    } else if (current.cart_items.get(i).type.equalsIgnoreCase("REFRESH")) {
                        ref_quan = ref_quan + current.cart_items.get(i).quantity;
                    } else {
                        ren_quan = ren_quan + current.cart_items.get(i).quantity;
                    }
                }
                if (alt_quan > 0 && ref_quan > 0) {
                    holder.txttype.setText("(Alter x " + alt_quan + ", Refresh x " + ref_quan + ")");
                } else if (alt_quan > 0) {
                    holder.txttype.setText("(Alter x " + alt_quan + ")");
                } else if (ref_quan > 0) {
                    holder.txttype.setText("(Refresh x " + ref_quan + ")");
                } else {
                    holder.txttype.setText("Old Order");
                }

                if (data.get(position).otherMeasurementOption == 1) {
                    if ((current.grandTotal + current.extra_charges.delivery) < 2000) {
                        holder.price.setText("₹ " + (current.grandTotal + current.extra_charges.delivery + current.extra_charges.delivery_cutoff_price));
                    } else {
                        holder.price.setText("₹ " + (current.grandTotal + current.extra_charges.delivery));
                    }
                } else {
                    holder.price.setText("₹ " + current.grandTotal);

                    if (current.grandTotal < 2000) {
                        holder.price.setText("₹ " + (current.grandTotal + current.extra_charges.delivery_cutoff_price));
                    } else {
                        holder.price.setText("₹ " + current.grandTotal);
                    }
                }
                holder.status.setText(data.get(position).order_status);

                for (int i = 0; i < current.cart_items.size(); i++) {
                    if (i == 0) {
                        desc = current.cart_items.get(i).productInfo.name;
                    } else {
                        desc = desc + ", " + current.cart_items.get(i).productInfo.name;
                    }
                }
                holder.desc.setText(desc);
                total_count = alt_quan + ref_quan - 1;
                if (current.cart_items.size() > 1) {
                    if (desc.substring(0, desc.indexOf(",")).length() > 13) {
                        holder.name.setText(desc.substring(0, 13) + ".. & " + total_count + " more");

                    } else {
                        holder.name.setText(desc.substring(0, desc.indexOf(",") - 1) + ".. & " + total_count + " more");

                    }
                } else {
                    holder.name.setText(desc);
                }
                    /*if (orderinfolistfirst.getItem_gender().equalsIgnoreCase("male"))
                        holder.desc.setText(orderinfolistfirst.getItem_type() + ", " + orderinfolistfirst.getItem_name());
                    else
                        holder.desc.setText(orderinfolistfirst.getItem_type() + ", " + orderinfolistfirst.getItem_name());*/

                Glide.with(holder.itemView.getContext()).load(data.get(position).order_info.cart_items.get(0).productInfo.image)
                        .into(holder.pic);
                holder.track.setTag(position);
                holder.track.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        // TODO Auto-generated method stub
                        int pos = (Integer) v.getTag();
                        Intent in = new Intent(activity, OrderHistory.class);
                        in.putExtra("orderId", data.get(pos).order_id);
                        activity.startActivity(in);
                    }
                });
                holder.rltop.setTag(position);
                holder.rltop.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        // TODO Auto-generated method stub
                        int pos = (Integer) v.getTag();
                        Intent in = new Intent(activity, OrderHistory.class);
                        in.putExtra("orderId", data.get(pos).order_id);
                        activity.startActivity(in);
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
        TextView name, price, desc, status, txttype;
        ImageView pic, track;
        RelativeLayout rltop;

        public PreviousViewHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.name);
            price = (TextView) itemView.findViewById(R.id.price);
            desc = (TextView) itemView.findViewById(R.id.desc);
            pic = (ImageView) itemView.findViewById(R.id.pic);
            track = (ImageView) itemView.findViewById(R.id.track);
            rltop = (RelativeLayout) itemView.findViewById(R.id.rltop);
            status = (TextView) itemView.findViewById(R.id.status);
            txttype = (TextView) itemView.findViewById(R.id.txttype);
        }
    }
}