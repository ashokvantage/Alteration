package com.tdevelopers.alteration.UserPackage.TransactionPackage;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.tdevelopers.alteration.UserPackage.AddressPackage.Address;
import com.tdevelopers.alteration.UserPackage.MeasurementsPackage.Measurements;

/**
 * Created by ADMIN on 13-Feb-18.
 */

public class TransactionDetails {
    @SerializedName("address")
    @Expose
    public Address address = new Address();

    @SerializedName("measurement")
    @Expose
    public Measurements measurements = new Measurements();
}
