package com.tdevelopers.alteration.Renew.Placket.PlacketResponses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.tdevelopers.alteration.Renew.home.Fabric;

/**
 * Created by ADMIN on 11-Jan-18.
 */

public class PlacketItem {

    @SerializedName("_id")
    @Expose
    public String id;
    @SerializedName("placket_style")
    @Expose
    public String placket_style;
    @SerializedName("image")
    @Expose
    public String use_image;
    @SerializedName("display_image")
    @Expose
    public String display_image;
    @SerializedName("price")
    @Expose
    public String price;
    @SerializedName("fabric")
    @Expose
    public Fabric fabric;
    public boolean itemFlag;
   /* @SerializedName("name")
    @Expose
    public String name;
    @SerializedName("image")
    @Expose
    public String image;

    public boolean itemFlag;*/

}
