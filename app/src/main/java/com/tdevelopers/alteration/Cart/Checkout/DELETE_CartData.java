package com.tdevelopers.alteration.Cart.Checkout;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by USER5 on 2/28/2018.
 */

public class DELETE_CartData implements Serializable {

    @SerializedName("user_id")
    @Expose
    public String userId;
    @SerializedName("product_id")
    @Expose
    public String product_id;
    @SerializedName("product_info")
    @Expose
    public ProductInfo productInfo;
    @SerializedName("supporting_images")
    @Expose
    public List<Support_Image> supporting_images = new ArrayList<Support_Image>();
    @SerializedName("quantity")
    @Expose
    public int quantity;
}
