
package com.application.refinary.pojo.ticketdetails;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OrderItem {

    @SerializedName("itemUUID")
    @Expose
    private String itemUUID;
    @SerializedName("itemQuantity")
    @Expose
    private Integer itemQuantity;
    @SerializedName("unitPrice")
    @Expose
    private String unitPrice;
    @SerializedName("grossPrice")
    @Expose
    private String grossPrice;
    @SerializedName("itemName")
    @Expose
    private String itemName;

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

    public String getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(String unitPrice) {
        this.unitPrice = unitPrice;
    }

    public String getGrossPrice() {
        return grossPrice;
    }

    public void setGrossPrice(String grossPrice) {
        this.grossPrice = grossPrice;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

}
