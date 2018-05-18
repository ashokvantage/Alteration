package com.tdevelopers.alteration.UserPackage.TransactionPackage;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.tdevelopers.alteration.Cart.Checkout.CartItem;
import com.tdevelopers.alteration.Cart.Checkout.ExtraCharges;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ADMIN on 13-Feb-18.
 */

public class OrderInfo {
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
    public Double grandTotal;
    @SerializedName("productTotalPrice")
    @Expose
    public double productTotalPrice;

    @SerializedName("user_id")
    @Expose
    public String user_id;
    @SerializedName("cart_items")
    @Expose
    public List<CartItem> cart_items = new ArrayList<CartItem>();
    @SerializedName("extra_charges")
    @Expose
    public ExtraCharges extra_charges = new ExtraCharges();

}
