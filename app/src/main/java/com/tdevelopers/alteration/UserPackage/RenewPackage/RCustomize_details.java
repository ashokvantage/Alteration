package com.tdevelopers.alteration.UserPackage.RenewPackage;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by ADMIN on 29-Jan-18.
 */

public class RCustomize_details {
    @SerializedName("type")
    @Expose
    public String type;
    @SerializedName("id")
    @Expose
    public String id;
    @SerializedName("name")
    @Expose
    public String name;
    @SerializedName("price")
    @Expose
    public String price;
    @SerializedName("style")
    @Expose
    public String style;
    @SerializedName("useimagePath")
    @Expose
    public String useimagePath;
    @SerializedName("displayimagePath")
    @Expose
    public String displayimagePath;
}
