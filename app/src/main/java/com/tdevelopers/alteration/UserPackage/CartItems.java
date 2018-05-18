package com.tdevelopers.alteration.UserPackage;

/**
 * Created by st185188 on 11/13/17.
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.tdevelopers.alteration.UserPackage.AddressPackage.Address;

public class CartItems {

    @SerializedName("address")
    @Expose
    public Address address;
    @SerializedName("measurement")
    @Expose
    public Measurement measurement;
    @SerializedName("orderId")
    @Expose
    public String orderId;
    @SerializedName("otherMeasurementOption")
    @Expose
    public int otherMeasurementOption;
    @SerializedName("paymentId")
    @Expose
    public String paymentId;
    @SerializedName("totalPrice")
    @Expose
    public int totalPrice;
    @SerializedName("transactionId")
    @Expose
    public String transactionId;

}