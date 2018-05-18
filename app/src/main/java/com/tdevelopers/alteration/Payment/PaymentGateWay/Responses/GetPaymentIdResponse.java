package com.tdevelopers.alteration.Payment.PaymentGateWay.Responses;

/**
 * Created by st185188 on 11/7/17.
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class GetPaymentIdResponse {

    @SerializedName("id")
    @Expose
    public String id;
    @SerializedName("user")
    @Expose
    public String user;
    @SerializedName("phone")
    @Expose
    public String phone;
    @SerializedName("email")
    @Expose
    public String email;
    @SerializedName("buyer_name")
    @Expose
    public String buyerName;
    @SerializedName("amount")
    @Expose
    public String amount;
    @SerializedName("purpose")
    @Expose
    public String purpose;
    @SerializedName("status")
    @Expose
    public String status;
    @SerializedName("expires_at")
    @Expose
    public Object expiresAt;
    @SerializedName("payments")
    @Expose
    public List<Object> payments = new ArrayList<Object>();
    @SerializedName("send_sms")
    @Expose
    public boolean sendSms;
    @SerializedName("send_email")
    @Expose
    public boolean sendEmail;
    @SerializedName("sms_status")
    @Expose
    public Object smsStatus;
    @SerializedName("email_status")
    @Expose
    public Object emailStatus;
    @SerializedName("shorturl")
    @Expose
    public Object shorturl;
    @SerializedName("longurl")
    @Expose
    public String longurl;
    @SerializedName("redirect_url")
    @Expose
    public Object redirectUrl;
    @SerializedName("webhook")
    @Expose
    public Object webhook;
    @SerializedName("scheduled_at")
    @Expose
    public Object scheduledAt;
    @SerializedName("created_at")
    @Expose
    public String createdAt;
    @SerializedName("modified_at")
    @Expose
    public String modifiedAt;
    @SerializedName("resource_uri")
    @Expose
    public String resourceUri;
    @SerializedName("allow_repeated_payments")
    @Expose
    public boolean allowRepeatedPayments;
    @SerializedName("mark_fulfilled")
    @Expose
    public boolean markFulfilled;

}
