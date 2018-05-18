package com.tdevelopers.alteration.UserPackage.AddressPackage;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by st185188 on 11/17/17.
 */
public class Address implements Serializable {

    @SerializedName("address_id")
    @Expose
    public String addressId;
    @SerializedName("address_name")
    @Expose
    public String addressName;
    @SerializedName("flat_no")
    @Expose
    public String flatNo;
    @SerializedName("area_locality")
    @Expose
    public String areaLocality;
    @SerializedName("city")
    @Expose
    public String city;
    @SerializedName("state")
    @Expose
    public String state;
    @SerializedName("pincode")
    @Expose
    public String pincode;
    @SerializedName("landmark")
    @Expose
    public String landmark;
    @SerializedName("lat")
    @Expose
    public String lat;
    @SerializedName("long")
    @Expose
    public String longs;
}
