package com.tdevelopers.alteration.Cart.Checkout;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by st185188 on 11/5/17.
 */
public class CartItem implements Serializable {

    @SerializedName("_id")
    @Expose
    public String id;
    @SerializedName("product_id")
    @Expose
    public String productId;
    @SerializedName("type")
    @Expose
    public String type;
    @SerializedName("product_info")
    @Expose
    public ProductInfo productInfo;
    /*@SerializedName("items")
    @Expose
    public Object items;*/
    @SerializedName("quantity")
    @Expose
    public int quantity;
    @SerializedName("price")
    @Expose
    public int price;

    @SerializedName("supporting_images")
    @Expose
    public List<Support_Image> supporting_images = new ArrayList<Support_Image>();


   /* @SerializedName("supporting_images")
    @Expose
    public List<String> supporting_images = new ArrayList<String>();*/
}
