package com.tdevelopers.alteration.UserPackage.userOrder;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.tdevelopers.alteration.Cart.Checkout.CartItem;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ADMIN on 18-Dec-17.
 */

public class OrderInFo implements Serializable {

    @SerializedName("user_id")
    @Expose
    public String user_id;
    @SerializedName("discount")
    @Expose
    public String discount;
    @SerializedName("grandTotal")
    @Expose
    public String grandTotal;
    @SerializedName("productTotalPrice")
    @Expose
    public String productTotalPrice;
    @SerializedName("stateGST")
    @Expose
    public String stateGST;
    @SerializedName("centralGST")
    @Expose
    public String centralGST;

    @SerializedName("cart_items")
    @Expose
    public List<CartItem> cartItems = new ArrayList<CartItem>();

}
