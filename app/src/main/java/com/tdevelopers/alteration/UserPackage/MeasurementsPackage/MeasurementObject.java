package com.tdevelopers.alteration.UserPackage.MeasurementsPackage;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.tdevelopers.alteration.UserPackage.Measurement;

import java.io.Serializable;

/**
 * Created by st185188 on 11/19/17.
 */
public class MeasurementObject implements Serializable {

    @SerializedName("measurement_id")
    @Expose
    public String measurementId;
    @SerializedName("measurement")
    @Expose
    public Measurement measurement;

}
