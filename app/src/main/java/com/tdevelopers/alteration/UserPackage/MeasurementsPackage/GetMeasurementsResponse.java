package com.tdevelopers.alteration.UserPackage.MeasurementsPackage;

/**
 * Created by st185188 on 11/19/17.
 */


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class GetMeasurementsResponse implements Serializable {

    private final static long serialVersionUID = 3692429295358458181L;
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

