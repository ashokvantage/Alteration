package com.tdevelopers.alteration.Renew.home;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by ADMIN on 08-Jan-18.
 */

public class Fabric {
    @SerializedName("_id")
    @Expose
    public String id;
    @SerializedName("name")
    @Expose
    public String name;

    @SerializedName("image")
    @Expose
    public String image;

    public boolean itemFlag;
}
