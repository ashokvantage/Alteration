package com.tdevelopers.alteration.UserPackage.RenewPackage;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ADMIN on 29-Jan-18.
 */

public class RData {
    @SerializedName("user_id")
    @Expose
    public String user_id;
    @SerializedName("image")
    @Expose
    public String image;
    @SerializedName("customize_id")
    @Expose
    public String customize_id;
    @SerializedName("customize_details")
    @Expose
    public List<RCustomize_details> customize_details = new ArrayList<>();
}
