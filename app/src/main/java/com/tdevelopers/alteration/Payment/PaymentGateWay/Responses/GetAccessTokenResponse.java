package com.tdevelopers.alteration.Payment.PaymentGateWay.Responses;

/**
 * Created by st185188 on 11/7/17.
 */


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetAccessTokenResponse {

    @SerializedName("access_token")
    @Expose
    public String accessToken;
    @SerializedName("expires_in")
    @Expose
    public int expiresIn;
    @SerializedName("token_type")
    @Expose
    public String tokenType;
    @SerializedName("scope")
    @Expose
    public String scope;

    @SerializedName("error")
    @Expose
    public String error;
}