package com.tdevelopers.alteration.Cart.Checkout;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by st185188 on 11/5/17.
 */
public class CartData implements Serializable {

    @SerializedName("user_id")
    @Expose
    public String userId;
    @SerializedName("cart_items")
    @Expose
    public List<CartItem> cartItems = new ArrayList<CartItem>();
    @SerializedName("productTotalPrice")
    @Expose
    public double productTotalPrice;
    @SerializedName("centralGST")
    @Expose
    public double centralGST;
    @SerializedName("stateGST")
    @Expose
    public double stateGST;
    @SerializedName("discount")
    @Expose
    public double discount;
    @SerializedName("grandTotal")
    @Expose
    public double grandTotal;
    @SerializedName("extra_charges")
    @Expose
    public ExtraCharges extra_charges;
}
