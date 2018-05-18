package com.tdevelopers.alteration.Cart.EditCart;

/**
 * Created by st185188 on 11/5/17.
 */
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ProductInfo {

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

}
