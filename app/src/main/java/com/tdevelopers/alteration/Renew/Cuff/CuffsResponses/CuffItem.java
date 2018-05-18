package com.tdevelopers.alteration.Renew.Cuff.CuffsResponses;

/**
 * Created by st185188 on 30/10/17.
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.tdevelopers.alteration.Renew.home.Fabric;

public class CuffItem {

    @SerializedName("_id")
    @Expose
    public String id;
    @SerializedName("cuff_style")
    @Expose
    public String cuffStyle;
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
    /*@SerializedName("options")
    @Expose
    public List<Option> options = new ArrayList<Option>();
    public String image;
    public boolean itemFlag;*/

}