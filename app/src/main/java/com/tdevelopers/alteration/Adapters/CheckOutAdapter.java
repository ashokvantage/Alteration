package com.tdevelopers.alteration.Adapters;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.tdevelopers.alteration.Cart.Checkout.CartData;
import com.tdevelopers.alteration.Cart.Checkout.CartItem;
import com.tdevelopers.alteration.Cart.Checkout.CheckoutResponse;
import com.tdevelopers.alteration.Cart.EditCart.EditCartResponse;
import com.tdevelopers.alteration.Cart.getCart.GetCartItemsResponse;
import com.tdevelopers.alteration.Helper;
import com.tdevelopers.alteration.Models.Remote.ApiUtils;
import com.tdevelopers.alteration.Payment.PaymentGateWay.Responses.CheckOutActivity;
import com.tdevelopers.alteration.R;
import com.tdevelopers.alteration.home.HomeActivity;
import com.tdevelopers.alteration.home.NewAlterActivity;
import com.tdevelopers.alteration.home.NewRefreshActivity;

import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by ADMIN on 21-Dec-17.
 */

public class CheckOutAdapter extends RecyclerView.Adapter<CheckOutAdapter.CheckViewHolder> {

    List<CartItem> data;
    public String userid;
    CheckOutActivity activity;
    String desc = "";
    int alt_quan = 0, ref_quan = 0, total_count = 0;

    public CheckOutAdapter(List<CartItem> data, String userid, CheckOutActivity activity) {
        this.data = data;
        this.userid = userid;
        this.activity = activity;
    }

    @Override
    public CheckViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new CheckViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_item, parent, false));
    }

    @Override
    public void onBindViewHolder(final CheckViewHolder holder, int position) {
        try {

            final CartItem current = data.get(position);
            if (current != null) {
                holder.name.setText(current.productInfo.name + "  x  " + current.quantity);
                holder.price.setText("₹ " + current.price);
                holder.price.setGravity(Gravity.RIGHT);
                holder.desc.setText(current.productInfo.garmentType);
                if (current.type.equalsIgnoreCase("ALTER")) {
                    holder.product_type.setText("Alter");
                } else if (current.type.equalsIgnoreCase("REFRESH")) {
                    holder.product_type.setText("Refresh");
                } else {
                    holder.product_type.setText("Old Order");
                }
                Glide.with(holder.itemView.getContext()).load(current.productInfo.image)
                        .into(holder.pic);
            }

            if (current != null) {

                holder.delete_item.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
                        builder.setMessage("Are you sure you want to delete?")
                                .setCancelable(false)
                                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        deleteCart(current, holder);
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

                if (current.quantity == 0) {
                    holder.plusMinusLayout.setVisibility(View.GONE);
                } else {
                    holder.plusMinusLayout.setVisibility(View.GONE);
                    holder.quantity.setText(current.quantity + "");
                }
//                holder.name.setText(current.name);
//
//                holder.fullName.setText(current.name);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public int getItemCount() {
        return data != null ? data.size() : 0;
    }

    class CheckViewHolder extends RecyclerView.ViewHolder {

        TextView name, price, desc, quantity, product_type;
        ImageView pic, plus, minus, delete_item;
        LinearLayout plusMinusLayout;

        public CheckViewHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.name);
            price = (TextView) itemView.findViewById(R.id.price);
            desc = (TextView) itemView.findViewById(R.id.desc);
            pic = (ImageView) itemView.findViewById(R.id.pic);
            plus = (ImageView) itemView.findViewById(R.id.plus);
            minus = (ImageView) itemView.findViewById(R.id.minus);
            quantity = (TextView) itemView.findViewById(R.id.quantity);
            product_type = (TextView) itemView.findViewById(R.id.type);
            plusMinusLayout = (LinearLayout) itemView.findViewById(R.id.plusMinusLayout);
            delete_item = (ImageView) itemView.findViewById(R.id.delete_item);
        }
    }

    private void deleteCart(final CartItem current, final CheckOutAdapter.CheckViewHolder holder) {


        HashMap<String, Object> map = new HashMap<>();
        map.put("user_id", userid);
        map.put("product_id", current.productId);
        map.put("type", current.type);
        map.put("image_id", "all");
        ApiUtils.getAlterationService()
                .deletecartItem(map)
                .enqueue(new Callback<CheckoutResponse>() {
                    @Override
                    public void onResponse(Call<CheckoutResponse> call, Response<CheckoutResponse> response) {
                        try {

                            Log.v("mtest", new Gson().toJson(response));

                            if (response.body() != null && response.body().cartData != null) {

                                current.quantity = response.body().cartData.cartItems.size();
                                holder.plusMinusLayout.setVisibility(View.GONE);
                                holder.quantity.setText(current.quantity + "");
                                HomeActivity.checkout_page = true;
                                HomeActivity.checkout_page = true;
                               /* fetchCartItems();
                                notifyDataSetChanged();*/
                                checkoutFunction();
                            } else {
                                activity.finish();
                                current.quantity = 0;
                                holder.quantity.setText(current.quantity + "");
                                holder.plusMinusLayout.setVisibility(View.GONE);
                                HomeActivity.checkout_page = true;
                                HomeActivity.checkout_page = true;
                            }


//                            fetchCartItems();
//                            notifyDataSetChanged();


                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(Call<CheckoutResponse> call, Throwable t) {
                        t.printStackTrace();
                    }
                });
    }

    public void fetchCartItems() {


        ApiUtils.getAlterationService()
                .getCartItems("user/get-cart-items?user_id=" + userid)
                .enqueue(new Callback<GetCartItemsResponse>() {
                    @Override
                    public void onResponse(Call<GetCartItemsResponse> call, final Response<GetCartItemsResponse> response) {
                        /* checkoutFunction();*/
                        /*try {

                            if (response.body() != null && response.body().data != null && response.body().data.cartItems != null && response.body().data.cartItems.size() != 0) {
                                {
//                                    for (List<Image> i : map.values()) {

//                                    for (int i = 0; i < response.body().data.cartItems.size(); i++) {
                                    ItemQuantity=0;
                                    for (CartItem cartItem : response.body().data.cartItems) {
                                        ItemQuantity = ItemQuantity + cartItem.quantity;
                                    }
//                                    }
                                    txtitemsquantity.setText("" + ItemQuantity);
                                    txtitemsquantity.setVisibility(View.VISIBLE);
                                    checkout.setVisibility(View.VISIBLE);
                                    checkout.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            checkoutFunction();
                                        }
                                    });

                                }


                            } else {

                                txtitemsquantity.setText("0");
                                ItemQuantity=0;
                                txtitemsquantity.setVisibility(View.GONE);
                                checkout.setVisibility(View.GONE);
                                checkout.setOnClickListener(null);
                            }
                        } catch (
                                Exception e)

                        {
                            e.printStackTrace();
                        }*/
                    }

                    @Override
                    public void onFailure(Call<GetCartItemsResponse> call, Throwable t) {
                        t.printStackTrace();
                    }
                });


    }

    private void checkoutFunction() {

        ApiUtils.getAlterationService()
                .checkOut("user/checkout", userid)
                .enqueue(new Callback<CheckoutResponse>() {
                    @Override
                    public void onResponse(Call<CheckoutResponse> call, Response<CheckoutResponse> response) {
                        try {

                            if (response.body() != null && response.body().cartData != null) {

//                                Intent intent = new Intent(activity, CheckOutActivity.class);
//                                intent.putExtra("data", response.body().cartData);
//                                activity.startActivity(intent);
                                CheckOutActivity.cartData = (CartData) response.body().cartData;

//                                CheckOutActivity.cartRv.setAdapter(new CheckOutAdapter(CheckOutActivity.cartData.cartItems, Helper.getLocalValue(activity, "userid"), activity));
                                if (CheckOutActivity.cartData != null) {
                                    if (!CheckOutActivity.expertPrice.getText().toString().equalsIgnoreCase("")) {
                                        CheckOutActivity.totalPay = CheckOutActivity.cartData.grandTotal + CheckOutActivity.cartData.extra_charges.delivery;
                                    } else {
                                        CheckOutActivity.totalPay = CheckOutActivity.cartData.grandTotal;
                                    }
                                    if (CheckOutActivity.totalPay < 2000) {
                                        CheckOutActivity.deliveryLayout.setVisibility(View.VISIBLE);
                                        CheckOutActivity.deliveryPrice.setText("₹ " + CheckOutActivity.cartData.extra_charges.delivery_cutoff_price);
                                        CheckOutActivity.totalPay = (CheckOutActivity.totalPay + CheckOutActivity.cartData.extra_charges.delivery_cutoff_price);
                                    } else {
                                        CheckOutActivity.deliveryLayout.setVisibility(View.GONE);
                                        CheckOutActivity.deliveryPrice.setText("");
                                    }
                                   /* CheckOutActivity.txttotalprice.setText("₹ " + String.format("%.2f", CheckOutActivity.cartData.productTotalPrice));
                                    CheckOutActivity.txttotalitem.setText("Altration x " + CheckOutActivity.cartData.cartItems.size());
                                    CheckOutActivity.txttotalpricewithgst.setText("₹ " + CheckOutActivity.totalPay);
                                    CheckOutActivity.txtgstprice.setText("₹ " + String.format("%.2f", (CheckOutActivity.cartData.grandTotal - CheckOutActivity.cartData.productTotalPrice)));*/


                                    CheckOutActivity.txttotalprice.setText("₹ " + String.format("%.2f", CheckOutActivity.cartData.productTotalPrice));
                                    CheckOutActivity.txttotalitem.setText("Altration x " + CheckOutActivity.cartData.cartItems.size());

                                    for (int i = 0; i < CheckOutActivity.cartData.cartItems.size(); i++) {

                                        if (CheckOutActivity.cartData.cartItems.get(i).type.equalsIgnoreCase("ALTER")) {
                                            alt_quan = alt_quan + CheckOutActivity.cartData.cartItems.get(i).quantity;
                                        } else if (CheckOutActivity.cartData.cartItems.get(i).type.equalsIgnoreCase("REFRESH")) {
                                            ref_quan = ref_quan + CheckOutActivity.cartData.cartItems.get(i).quantity;
                                        }
                                    }

                                    for (int i = 0; i < CheckOutActivity.cartData.cartItems.size(); i++) {
                                        if (i == 0) {
                                            desc = CheckOutActivity.cartData.cartItems.get(i).productInfo.name;
                                        } else {
                                            desc = desc + ", " + CheckOutActivity.cartData.cartItems.get(i).productInfo.name;
                                        }
                                    }
                                    total_count = alt_quan + ref_quan - 1;
                                    if (CheckOutActivity.cartData.cartItems.size() > 1) {
                                        if (desc.substring(0, desc.indexOf(",")).length() > 13) {
                                            CheckOutActivity.txttotalitem.setText(desc.substring(0, 13) + ".. & " + total_count + " more");

                                        } else {
                                            CheckOutActivity.txttotalitem.setText(desc.substring(0, desc.indexOf(",") - 1) + ".. & " + total_count + " more");

                                        }
                                    } else {
                                        CheckOutActivity.txttotalitem.setText(desc);
                                    }

                                    CheckOutActivity.txttotalpricewithgst.setText("₹ " + CheckOutActivity.totalPay);
                                    CheckOutActivity.txtgstprice.setText("₹ " + String.format("%.2f", (CheckOutActivity.cartData.grandTotal - CheckOutActivity.cartData.productTotalPrice)));
                                    /*if (CheckOutActivity.otherMeasurementOption == 1) {
                                        CheckOutActivity.totalPay = CheckOutActivity.cartData.grandTotal + CheckOutActivity.cartData.extra_charges.delivery;
                                        CheckOutActivity.txttotalpricewithgst.setText("₹ " + CheckOutActivity.totalPay);
                                    }*/
                                    CheckOutActivity.cartRv.setAdapter(new CheckOutAdapter(CheckOutActivity.cartData.cartItems, Helper.getLocalValue(activity, "userid"), activity));


                                } else {
                                    activity.finish();
                                }
                            } else {
                                activity.finish();
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(Call<CheckoutResponse> call, Throwable t) {
                        t.printStackTrace();
                    }
                });
    }

}
