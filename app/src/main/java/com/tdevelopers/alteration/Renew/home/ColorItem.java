package com.tdevelopers.alteration.Renew.home;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by ADMIN on 28-Dec-17.
 */

public class ColorItem {
    @SerializedName("_id")
    @Expose
    public String id;
    @SerializedName("name")
    @Expose
    public String name;
    @SerializedName("image")
    @Expose
    public String image;
    @SerializedName("price")
    @Expose
    public String price;
   /* @SerializedName("code")
    @Expose
    public String code;*/
}
