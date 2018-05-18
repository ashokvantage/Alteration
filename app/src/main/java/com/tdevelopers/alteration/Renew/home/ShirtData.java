package com.tdevelopers.alteration.Renew.home;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ADMIN on 28-Dec-17.
 */

public class ShirtData {
    @SerializedName("shirt-bodies")
    @Expose
    public List<ShirtItems> shirt_bodies = new ArrayList<ShirtItems>();
}
