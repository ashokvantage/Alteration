package com.tdevelopers.alteration.Renew.Cuff.CuffsResponses;

/**
 * Created by st185188 on 30/10/17.
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class CuffsResponse {

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
    public List<CuffItem> data = new ArrayList<CuffItem>();

}
