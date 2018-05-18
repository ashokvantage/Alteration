package com.tdevelopers.alteration.NewLogin.OTPAuthenticate;

/**
 * Created by st185188 on 11/5/17.
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OTPAuthenticateResponse {

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
    public OtpData data;

}

