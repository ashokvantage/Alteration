package com.tdevelopers.alteration.Cart.Checkout;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by st185188 on 11/5/17.
 */
public class ProductInfo implements Serializable {

    @SerializedName("name")
    @Expose
    public String name;
    @SerializedName("price")
    @Expose
    public String price;
    @SerializedName("image")
    @Expose
    public String image;
    @SerializedName("gender")
    @Expose
    public String gender;
    @SerializedName("garment_type")
    @Expose
    public String garmentType;
    @SerializedName("created_at")
    @Expose
    public String createdAt;
    @SerializedName("_id")
    @Expose
    public String id;
    @SerializedName("updated_at")
    @Expose
    public String updatedAt;

}
