package com.tdevelopers.alteration.Renew.Collar.CollarResponses;

/**
 * Created by st185188 on 30/10/17.
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.tdevelopers.alteration.Renew.home.Fabric;

public class CollarItem {

    @SerializedName("_id")
    @Expose
    public String id;
    @SerializedName("collar_style")
    @Expose
    public String collarStyle;
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

    public boolean itemFlag;
    public String image;

    public transient CollarFabricAdapter collarFabricAdapter;*/
}