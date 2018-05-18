package com.tdevelopers.alteration.Adapters;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.tdevelopers.alteration.Cart.Checkout.CartItem;
import com.tdevelopers.alteration.R;
import com.tdevelopers.alteration.UserPackage.userOrder.FullSupportImageActivity;
import com.tdevelopers.alteration.UserPackage.userOrder.OrderHistory;

import java.util.List;

/**
 * Created by ADMIN on 20-Feb-18.
 */

public class PreviousOrderHistoryAdapter extends RecyclerView.Adapter<PreviousOrderHistoryAdapter.CartViewHolder> {

    List<CartItem> cartItem;
    OrderHistory activity;

    public PreviousOrderHistoryAdapter(List<CartItem> data, OrderHistory activity) {
        this.cartItem = data;
        this.activity = activity;
    }

    @Override
    public PreviousOrderHistoryAdapter.CartViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new PreviousOrderHistoryAdapter.CartViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_item, parent, false));
    }

    @Override
    public void onBindViewHolder(PreviousOrderHistoryAdapter.CartViewHolder holder, final int position) {
        try {

            if (cartItem.size() > 0) {
                holder.txttype.setVisibility(View.VISIBLE);
                holder.supp_image.setVisibility(View.VISIBLE);
                holder.delete_item.setVisibility(View.GONE);
                holder.name.setText(cartItem.get(position).productInfo.name + "  x  " + cartItem.get(position).quantity);
                holder.price.setText("₹ " + cartItem.get(position).productInfo.price);
                holder.price.setGravity(Gravity.RIGHT);
                if (cartItem.get(position).productInfo.gender.equalsIgnoreCase("male"))
                    holder.desc.setText("Mens, " + cartItem.get(position).productInfo.garmentType + ", " + cartItem.get(position).productInfo.name);
                else
                    holder.desc.setText("Womens, " + cartItem.get(position).productInfo.garmentType + ", " + cartItem.get(position).productInfo.name);
                if (cartItem.get(position).type.equalsIgnoreCase("ALTER")) {
                    holder.txttype.setText("Alter");
                } else if (cartItem.get(position).type.equalsIgnoreCase("REFRESH")) {
                    holder.txttype.setText("Refresh");
                } else {
                    holder.txttype.setText("Old Order");
                }
                Glide.with(holder.itemView.getContext()).load(cartItem.get(position).productInfo.image)
                        .into(holder.pic);
                Glide.with(holder.itemView.getContext()).load(cartItem.get(position).supporting_images.get(0).image)
                        .into(holder.supp_image);
                holder.itemView.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        // TODO Auto-generated method stub
                        Intent in = new Intent(activity, FullSupportImageActivity.class);
                        in.putExtra("imagepath", cartItem.get(position).supporting_images.get(0).image);
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
        return cartItem.size();
    }

    class CartViewHolder extends RecyclerView.ViewHolder {

        TextView name, price, desc, txttype;
        ImageView pic, supp_image,delete_item;

        public CartViewHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.name);
            price = (TextView) itemView.findViewById(R.id.price);
            desc = (TextView) itemView.findViewById(R.id.desc);
            pic = (ImageView) itemView.findViewById(R.id.pic);
            txttype = (TextView) itemView.findViewById(R.id.type);
            supp_image = (ImageView) itemView.findViewById(R.id.supp_image);
            delete_item=(ImageView)itemView.findViewById(R.id.delete_item);
        }
    }
}
