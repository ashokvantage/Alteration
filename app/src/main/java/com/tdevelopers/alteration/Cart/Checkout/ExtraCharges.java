package com.tdevelopers.alteration.Cart.Checkout;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by ADMIN on 19-Feb-18.
 */

public class ExtraCharges implements Serializable {

    @SerializedName("delivery")
    @Expose
    public double delivery;
    @SerializedName("delivery_cutoff_price")
    @Expose
    public double delivery_cutoff_price;
}
