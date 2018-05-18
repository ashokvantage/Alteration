package com.tdevelopers.alteration.Renew.home;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by ADMIN on 28-Dec-17.
 */

public class ShirtResponce {
    @SerializedName("response")
    @Expose
    public String response;
    @SerializedName("status_code")
    @Expose
    public Integer statusCode;
    @SerializedName("message")
    @Expose
    public String message;
    @SerializedName("data")
    @Expose
   public ShirtData data;
}
