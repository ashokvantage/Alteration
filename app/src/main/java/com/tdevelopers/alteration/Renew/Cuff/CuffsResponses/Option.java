package com.tdevelopers.alteration.Renew.Cuff.CuffsResponses;

/**
 * Created by st185188 on 30/10/17.
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Option {

    @SerializedName("cuff_fabric")
    @Expose
    public CuffFabric cuffFabric;
    @SerializedName("cuff_image")
    @Expose
    public String cuffImage;
    @SerializedName("cuff_price")
    @Expose
    public String cuffPrice;

    public boolean itemFlag;

}
