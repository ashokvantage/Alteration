package com.tdevelopers.alteration.Renew.Collar.CollarResponses;

/**
 * Created by st185188 on 30/10/17.
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Option {

    @SerializedName("collar_fabric")
    @Expose
    public CollarFabric collarFabric;
    @SerializedName("collar_image")
    @Expose
    public String collarImage;
    @SerializedName("collar_price")
    @Expose
    public String collarPrice;

    public boolean itemFlag;

}