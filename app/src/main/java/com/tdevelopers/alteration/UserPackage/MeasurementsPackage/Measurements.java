package com.tdevelopers.alteration.UserPackage.MeasurementsPackage;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by ADMIN on 13-Feb-18.
 */

public class Measurements implements Serializable {
    @SerializedName("measurement_id")
    @Expose
    public String measurement_id;
    @SerializedName("measurement")
    @Expose
    public Measurementttt measurements = new Measurementttt();
}
