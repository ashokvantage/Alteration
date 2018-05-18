package com.tdevelopers.alteration.UserPackage;

import com.tdevelopers.alteration.UserPackage.AddressPackage.Address;
import com.tdevelopers.alteration.UserPackage.MeasurementsPackage.MeasurementObject;

import java.io.Serializable;

/**
 * Created by st185188 on 11/9/17.
 */

public class Transaction implements Serializable {

    public String paymentId;
    public String orderId;
    public String transactionId;
    public double totalPrice;
    public MeasurementObject measurement;
    public Address address;
    public String measurementId;
    /*public String addressId;*/
//    public int otherMeasurementOption;
    String payment_type;

    public Transaction(String orderID, String transactionID, String paymentID, double totalPrice, MeasurementObject measurement, Address address) {
        this.transactionId = transactionID;
        this.orderId = orderID;
        this.paymentId = paymentID;
        this.totalPrice = totalPrice;
        this.address = address;
        this.measurement = measurement;
//        this.otherMeasurementOption = otherOption;
    }

    public Transaction(String orderID, String transactionID, String paymentID, double totalPrice, String measurementId, Address address, String payment_type) {
        this.transactionId = transactionID;
        this.orderId = orderID;
        this.paymentId = paymentID;
        this.totalPrice = totalPrice;
        this.address = address;
        this.measurementId = measurementId;
//        this.otherMeasurementOption = otherOption;
        this.payment_type = payment_type;
    }

    public Transaction(String orderID, String transactionID, String paymentID, double totalPrice, MeasurementObject measurement, Address address, String payment_type) {
        this.transactionId = transactionID;
        this.orderId = orderID;
        this.paymentId = paymentID;
        this.totalPrice = totalPrice;
        this.address = address;
        this.measurement = measurement;
//        this.otherMeasurementOption = otherOption;
        this.payment_type = payment_type;
    }

}
