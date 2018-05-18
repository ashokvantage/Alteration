package com.tdevelopers.alteration.Payment.PaymentGateWay.Responses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by ADMIN on 12-Dec-17.
 */

public class GetPaymentStatus {
    @SerializedName("id")
    @Expose
    public String id;
    @SerializedName("status")
    @Expose
    public String status;
    @SerializedName("amount")
    @Expose
    public String amount;
    @SerializedName("instrument_type")
    @Expose
    public String instrument_type;

}
