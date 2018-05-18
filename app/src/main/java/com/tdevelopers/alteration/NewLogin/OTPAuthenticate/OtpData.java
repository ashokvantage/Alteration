package com.tdevelopers.alteration.NewLogin.OTPAuthenticate;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by st185188 on 11/5/17.
 */

public class OtpData {

    @SerializedName("user_id")
    @Expose
    public String userId;
    @SerializedName("mobile")
    @Expose
    public String mobile;
    @SerializedName("auth_key")
    @Expose
    public String authKey;

}