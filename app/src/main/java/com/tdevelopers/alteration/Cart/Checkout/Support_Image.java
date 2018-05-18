package com.tdevelopers.alteration.Cart.Checkout;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by USER5 on 2/26/2018.
 */

public class Support_Image implements Serializable {
    @SerializedName("id")
    @Expose
    public String id;
    @SerializedName("image")
    @Expose
    public String image;
    @SerializedName("note")
    @Expose
    public String note;
}
