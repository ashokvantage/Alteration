package com.tdevelopers.alteration.UserPackage;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.tdevelopers.alteration.UserPackage.AddressPackage.Address;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by st185188 on 11/8/17.
 */
public class Data implements Serializable {

    @SerializedName("address")
    @Expose
    public List<Address> address = new ArrayList<Address>();
    @SerializedName("created_at")
    @Expose
    public String createdAt;
    @SerializedName("email")
    @Expose
    public String email;
    @SerializedName("gender")
    @Expose
    public String gender;

    @SerializedName("measurements")
    @Expose
    public List<Measurement> measurements = new ArrayList<Measurement>();
    @SerializedName("name")
    @Expose
    public String name;
    @SerializedName("phone")
    @Expose
    public String phone;
    @SerializedName("mobile")
    @Expose
    public String mobile;
    @SerializedName("updated_at")
    @Expose
    public String updatedAt;
    @SerializedName("user_id")
    @Expose
    public String userId;

}
