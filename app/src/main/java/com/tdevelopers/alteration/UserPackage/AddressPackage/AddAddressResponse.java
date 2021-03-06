package com.tdevelopers.alteration.UserPackage.AddressPackage;

/**
 * Created by st185188 on 11/17/17.
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AddAddressResponse {

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
    public Address data;

}