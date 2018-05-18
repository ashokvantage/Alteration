package com.tdevelopers.alteration.Models.Objects;

/**
 * Created by st185188 on 23/10/17.
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.tdevelopers.alteration.Cart.Checkout.Support_Image;

import java.util.ArrayList;
import java.util.List;

public class Image {

    @SerializedName("_id")
    @Expose
    public String id;
    @SerializedName("created_at")
    @Expose
    public String createdAt;
    @SerializedName("gender")
    @Expose
    public String gender;
    @SerializedName("image")
    @Expose
    public String image;
    @SerializedName("name")
    @Expose
    public String name;
    @SerializedName("price")
    @Expose
    public String price;
    @SerializedName("updated_at")
    @Expose
    public String updatedAt;
    @SerializedName("garment_type")
    @Expose
    public String garment_type;
    public int quantitiy;

    @SerializedName("supporting_images")
    @Expose
    public List<Support_Image> supporting_images = new ArrayList<Support_Image>();
    @SerializedName("product_id")
    @Expose
    public String product_id;
}