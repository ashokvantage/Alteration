package com.tdevelopers.alteration.NewLogin.OTPRequest;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by st185188 on 11/6/17.
 */
public class Data {

    @SerializedName("tempOtp")
    @Expose
    public TempOtp tempOtp;

}
