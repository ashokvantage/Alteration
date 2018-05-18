package com.tdevelopers.alteration.Adapters;

import android.graphics.Color;
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

import java.util.List;


/**
 * Created by st185188 on 11/8/17.
 */

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder> {

    List<CartItem> data;
    String type;

    public CartAdapter(List<CartItem> data, String type) {
        this.data = data;
        this.type = type;
    }

    @Override
    public CartViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new CartViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_item, parent, false));
    }

    @Override
    public void onBindViewHolder(CartViewHolder holder, int position) {
        try {

            CartItem current = data.get(position);
            if (current != null) {
                holder.name.setText(current.productInfo.name + "  x  " + current.quantity);
                if (type.equalsIgnoreCase("failed")) {
                    holder.price.setText("Failed");
                    holder.price.setTextColor(Color.WHITE);
                    holder.price.setBackgroundColor(Color.RED);
                } else if (type.equalsIgnoreCase("success")) {
                    holder.price.setText("Success");
                    holder.price.setTextColor(Color.WHITE);
                    holder.price.setBackgroundColor(Color.parseColor("#40c5aa"));
                    holder.track.setVisibility(View.GONE);
                } else {
                    holder.price.setText("₹ " + current.price);
                    holder.price.setGravity(Gravity.RIGHT);

                }
                holder.desc.setText(current.productInfo.garmentType);
                if (current.type.equalsIgnoreCase("ALTER")) {
                    holder.product_type.setText("Alter");
                } else if (current.type.equalsIgnoreCase("REFRESH")) {
                    holder.product_type.setText("Refresh");
                }
                else
                {
                    holder.product_type.setText("Old Order");
                }
                Glide.with(holder.itemView.getContext()).load(current.productInfo.image)
                        .into(holder.pic);
                holder.track.setTag(position);
                holder.track.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        // TODO Auto-generated method stub
                        int pos = (Integer) v.getTag();
                        CartItem h1 = (CartItem) data.get(pos);
//                        String Order_ID = h1.getOrderId();
//                        String Order_date = h1.getOrdered_date();
//                        Intent in = new Intent(getActivity(), OrderHistory.class);
//                        in.putExtra("orderId", Order_ID);
//                        in.putExtra("order_date", Order_date);
//                        in.putExtra("orderInfo", h1.getOrderInfo());
//                        in.putExtra("producttotalprice", h1.getProductTotalPrice());
//                        in.putExtra("grandTotal", h1.getGrandTotal());
//                        in.putExtra("totalquantity", h1.getOrder_product_quantity());
//                        in.putExtra("address", h1.getTransactionDetails());
//                        startActivity(in);
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

    class CartViewHolder extends RecyclerView.ViewHolder {

        TextView name, price, desc, track,product_type;
        ImageView pic;

        public CartViewHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.name);
            price = (TextView) itemView.findViewById(R.id.price);
            desc = (TextView) itemView.findViewById(R.id.desc);
            pic = (ImageView) itemView.findViewById(R.id.pic);
            track = (TextView) itemView.findViewById(R.id.track);
            product_type=(TextView)itemView.findViewById(R.id.type);
        }
    }
}
