package com.tdevelopers.alteration.UserPackage.MeasurementsPackage;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by st185188 on 11/19/17.
 */
public class Data implements Serializable {

    private final static long serialVersionUID = 5724983702692077633L;
    @SerializedName("measurements")
    @Expose
    public List<MeasurementObject> measurements = new ArrayList<MeasurementObject>();

}
