package com.tdevelopers.alteration.UserPackage.TransactionPackage;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.tdevelopers.alteration.UserPackage.userOrder.OrderInFo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ADMIN on 18-Dec-17.
 */

public class OrderItem {
    @SerializedName("user_id")
    @Expose
    public String userId;

    @SerializedName("order_id")
    @Expose
    public String orderId;

    @SerializedName("order_info")
    @Expose
    public List<OrderInFo> orderInfo = new ArrayList<OrderInFo>();
}
