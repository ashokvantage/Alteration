package com.tdevelopers.alteration.Models.Objects;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by st185188 on 23/10/17.
 */

public class AlterItem {

    @SerializedName("garment_type")
    @Expose
    public String garmentType;
    @SerializedName("image")
    @Expose
    public List<Image> image = new ArrayList<Image>();
}
