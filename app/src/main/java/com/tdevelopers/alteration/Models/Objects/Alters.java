package com.tdevelopers.alteration.Models.Objects;

/**
 * Created by st185188 on 23/10/17.
 */


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class Alters {

    @SerializedName("men")
    @Expose
    public List<Image> men = new ArrayList<Image>();
    @SerializedName("women")
    @Expose
    public List<Image> women = new ArrayList<Image>();

}