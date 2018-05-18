package com.tdevelopers.alteration.UserPackage;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by st185188 on 11/8/17.
 */
public class Measurement implements Serializable {

    @SerializedName("name")
    @Expose
    public String name;
    @SerializedName("gender")
    @Expose
    public String gender;
    @SerializedName("trouserlength")
    @Expose
    public String trouserlength;
    @SerializedName("waist")
    @Expose
    public String waist;
    @SerializedName("thigh")
    @Expose
    public String thigh;
    @SerializedName("ankle")
    @Expose
    public String ankle;
    @SerializedName("crotch")
    @Expose
    public String crotch;
    @SerializedName("hip-w")
    @Expose
    public String hipW;
    @SerializedName("shortlength")
    @Expose
    public String shortlength;
    @SerializedName("shoulder")
    @Expose
    public String shoulder;
    @SerializedName("sleevelength")
    @Expose
    public String sleevelength;
    @SerializedName("chest")
    @Expose
    public String chest;
    @SerializedName("tummy")
    @Expose
    public String tummy;
    @SerializedName("hip")
    @Expose
    public String hip;
    @SerializedName("neck")
    @Expose
    public String neck;
    @SerializedName("bicep")
    @Expose
    public String bicep;
    @SerializedName("forearm")
    @Expose
    public String forearm;


    @SerializedName("troso")
    @Expose

    public String troso;


    public Measurement(String name, String gender, String trouserlength, String waist, String thigh, String ankle, String crotch, String hipW, String shortlength, String shoulder, String sleevelength, String chest, String tummy, String hip, String neck, String bicep, String forearm, String troso) {
        this.name = name;
        this.gender = gender;
        this.trouserlength = trouserlength;
        this.waist = waist;
        this.thigh = thigh;
        this.ankle = ankle;
        this.crotch = crotch;
        this.hipW = hipW;
        this.shortlength = shortlength;
        this.shoulder = shoulder;
        this.sleevelength = sleevelength;
        this.chest = chest;
        this.tummy = tummy;
        this.hip = hip;
        this.neck = neck;
        this.bicep = bicep;
        this.forearm = forearm;
        this.troso = troso;
    }


}
