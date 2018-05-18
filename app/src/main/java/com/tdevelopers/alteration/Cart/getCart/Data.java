package com.tdevelopers.alteration.Cart.getCart;

/**
 * Created by st185188 on 11/5/17.
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.tdevelopers.alteration.Cart.Checkout.CartItem;

import java.util.ArrayList;
import java.util.List;

public class Data {

    @SerializedName("user_id")
    @Expose
    public String userId;
    @SerializedName("cart_items")
    @Expose
    public List<CartItem> cartItems = new ArrayList<CartItem>();

}