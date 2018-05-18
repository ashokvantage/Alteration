package com.tdevelopers.alteration.UserPackage;

/**
 * Created by st185188 on 11/13/17.
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.tdevelopers.alteration.Cart.Checkout.CartItem;

import java.util.ArrayList;
import java.util.List;

public class Datum {

    @SerializedName("user_id")
    @Expose
    public String userId;
    @SerializedName("transaction_details")
    @Expose
    public Transaction transactionDetails;
    @SerializedName("cart_items")
    @Expose
    public List<CartItem> cartItems = new ArrayList<CartItem>();
    @SerializedName("created_at")
    @Expose
    public String createdAt;
    @SerializedName("_id")
    @Expose
    public String id;

}
