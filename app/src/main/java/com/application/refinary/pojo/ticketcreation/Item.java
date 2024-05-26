
package com.application.refinary.pojo.ticketcreation;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Item {

    @SerializedName("itemUUID")
    @Expose
    private String itemUUID;
    @SerializedName("itemQuantity")
    @Expose
    private Integer itemQuantity;
    @SerializedName("itemPrice")
    @Expose
    private Integer itemPrice;

    public String getItemUUID() {
        return itemUUID;
    }

    public void setItemUUID(String itemUUID) {
        this.itemUUID = itemUUID;
    }

    public Integer getItemQuantity() {
        return itemQuantity;
    }

    public void setItemQuantity(Integer itemQuantity) {
        this.itemQuantity = itemQuantity;
    }

    public Integer getItemPrice() {
        return itemPrice;
    }

    public void setItemPrice(Integer itemPrice) {
        this.itemPrice = itemPrice;
    }

}
