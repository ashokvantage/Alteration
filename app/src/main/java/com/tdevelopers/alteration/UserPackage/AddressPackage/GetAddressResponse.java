package com.tdevelopers.alteration.UserPackage.AddressPackage;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetAddressResponse {

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