package com.tdevelopers.alteration.Renew.Sleeve.SleeveResponses;

/**
 * Created by st185188 on 30/10/17.
 */


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.tdevelopers.alteration.Renew.home.Fabric;

public class SleeveItem {

    @SerializedName("_id")
    @Expose
    public String id;
    @SerializedName("sleeve_style")
    @Expose
    public String sleeve_style;
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