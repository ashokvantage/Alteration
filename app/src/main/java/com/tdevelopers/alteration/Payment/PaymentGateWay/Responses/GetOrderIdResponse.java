package com.tdevelopers.alteration.Payment.PaymentGateWay.Responses;

/**
 * Created by st185188 on 11/7/17.
 */


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetOrderIdResponse {

    @SerializedName("order_id")
    @Expose
    public String orderId;
    @SerializedName("name")
    @Expose
    public String name;
    @SerializedName("email")
    @Expose
    public String email;
    @SerializedName("phone")
    @Expose
    public String phone;
    @SerializedName("amount")
    @Expose
    public String amount;
    @SerializedName("status")
    @Expose
    public Boolean status;

}