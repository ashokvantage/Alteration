package com.tdevelopers.alteration.UserPackage.TransactionPackage;

/**
 * Created by st185188 on 11/9/17.
 */


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class SaveTransactionResponse implements Serializable {

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
    public TData data;
}

