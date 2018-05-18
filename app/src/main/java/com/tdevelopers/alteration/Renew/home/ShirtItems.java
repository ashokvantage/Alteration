package com.tdevelopers.alteration.Renew.home;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by ADMIN on 28-Dec-17.
 */

public class ShirtItems {
    @SerializedName("_id")
    @Expose
    public String id;
    @SerializedName("shirt_style")
    @Expose
    public String shirt_style;

    @SerializedName("shirt_body_style")
    @Expose
    public String shirt_body_style;
    @SerializedName("price")
    @Expose
    public String price;
    @SerializedName("gender")
    @Expose
    public String gender;
    @SerializedName("image")
    @Expose

    public String image;
    @SerializedName("fabric")
    @Expose
    public Fabric fabric;
    public boolean itemFlag;
}