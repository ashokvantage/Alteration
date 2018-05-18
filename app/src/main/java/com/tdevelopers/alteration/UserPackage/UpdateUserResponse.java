package com.tdevelopers.alteration.UserPackage;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by st185188 on 11/12/17.
 */

public class UpdateUserResponse {

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
    public Data data;
}
