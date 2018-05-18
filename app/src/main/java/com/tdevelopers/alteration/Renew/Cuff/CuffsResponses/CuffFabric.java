package com.tdevelopers.alteration.Renew.Cuff.CuffsResponses;

/**
 * Created by st185188 on 30/10/17.
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CuffFabric {

    @SerializedName("_id")
    @Expose
    public String id;
    @SerializedName("color")
    @Expose
    public String color;
    @SerializedName("composition")
    @Expose
    public String composition;
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
    @SerializedName("occasion")
    @Expose
    public String occasion;
    @SerializedName("pattern")
    @Expose
    public String pattern;
    @SerializedName("ply")
    @Expose
    public String ply;
    @SerializedName("price")
    @Expose
    public String price;
    @SerializedName("price_range")
    @Expose
    public String priceRange;
    @SerializedName("updated_at")
    @Expose
    public String updatedAt;
    @SerializedName("weave")
    @Expose
    public String weave;
    @SerializedName("weight")
    @Expose
    public String weight;

}