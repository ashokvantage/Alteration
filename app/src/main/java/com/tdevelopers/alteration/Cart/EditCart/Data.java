package com.tdevelopers.alteration.Cart.EditCart;

/**
 * Created by st185188 on 11/5/17.
 */
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class Data {

    @SerializedName("user_id")
    @Expose
    public String userId;
    @SerializedName("product_id")
    @Expose
    public String productId;
    @SerializedName("product_info")
    @Expose
    public ProductInfo productInfo;
    @SerializedName("items")
    @Expose
    public Object items;
    @SerializedName("quantity")
    @Expose
    public int quantity;
    @SerializedName("supporting_images")
    @Expose
    public ArrayList<String> supporting_images;
    @SerializedName("cart_items")
    @Expose
    public int cartItems;

}