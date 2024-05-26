
package com.application.refinary.pojo.ticketdetailssocketpojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TicketDetailsSocketPojo {

    @SerializedName("orderInfo")
    @Expose
    private OrderInfo orderInfo;

    public OrderInfo getOrderInfo() {
        return orderInfo;
    }

    public void setOrderInfo(OrderInfo orderInfo) {
        this.orderInfo = orderInfo;
    }

}
