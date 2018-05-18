package com.tdevelopers.alteration.UserPackage.TransactionPackage;

/**
 * Created by st185188 on 11/9/17.
 */


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class TData implements Serializable {

    @SerializedName("user_id")
    @Expose
    public String userId;
    @SerializedName("order_id")
    @Expose
    public String orderId;

}
