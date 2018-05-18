package com.tdevelopers.alteration.UserPackage.AddressPackage;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by st185188 on 11/17/17.
 */
public class Data {

    @SerializedName("address")
    @Expose
    public List<Address> address = new ArrayList<Address>();

}
