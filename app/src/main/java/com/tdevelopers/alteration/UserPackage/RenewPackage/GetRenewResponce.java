package com.tdevelopers.alteration.UserPackage.RenewPackage;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ADMIN on 29-Jan-18.
 */

public class GetRenewResponce {
    @SerializedName("response")
    @Expose
    public String response;
    @SerializedName("status_code")
    @Expose
    public int statusCode;
    @SerializedName("message")
    @Expose
    public String message;
    @SerializedName("data")
    @Expose
    public List<RData> data = new ArrayList<RData>();
}
