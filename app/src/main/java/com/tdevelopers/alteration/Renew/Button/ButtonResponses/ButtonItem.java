package com.tdevelopers.alteration.Renew.Button.ButtonResponses;

/**
 * Created by st185188 on 30/10/17.
 */


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ButtonItem {

    @SerializedName("_id")
    @Expose
    public String id;
    @SerializedName("name")
    @Expose
    public String name;
    @SerializedName("image")
    @Expose
    public String use_image;
    @SerializedName("display_image")
    @Expose
    public String display_image;

    public boolean itemFlag;
}