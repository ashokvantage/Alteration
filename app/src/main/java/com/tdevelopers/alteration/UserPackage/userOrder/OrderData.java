package com.tdevelopers.alteration.UserPackage.userOrder;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.tdevelopers.alteration.UserPackage.TransactionPackage.OrderInfo;
import com.tdevelopers.alteration.UserPackage.TransactionPackage.TransactionDetails;

import java.io.Serializable;

/**
 * Created by ADMIN on 13-Feb-18.
 */

public class OrderData implements Serializable {

    @SerializedName("user_id")
    @Expose
    public String userId;
    @SerializedName("order_id")
    @Expose
    public String order_id;
    @SerializedName("ordered_date")
    @Expose
    public String ordered_date;
    @SerializedName("transaction_details")
    @Expose
    public TransactionDetails transaction_details = new TransactionDetails();
    @SerializedName("order_info")
    @Expose
    public OrderInfo order_info = new OrderInfo();
    @SerializedName("otherMeasurementOption")
    @Expose
    public int otherMeasurementOption;
    @SerializedName("cod")
    @Expose
    public Boolean cod;
    @SerializedName("order_status")
    @Expose
    public String order_status;
    @SerializedName("payment_status")
    @Expose
    public String payment_status;
    @SerializedName("remarks")
    @Expose
    public String remarks;
}
