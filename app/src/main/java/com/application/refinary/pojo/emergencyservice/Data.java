
package com.application.refinary.pojo.emergencyservice;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Data {

    @SerializedName("itemUUID")
    @Expose
    private String itemUUID;
    @SerializedName("itemQuantity")
    @Expose
    private Integer itemQuantity = 1;
    @SerializedName("itemName")
    @Expose
    private String itemName;
    @SerializedName("itemDescription")
    @Expose
    private String itemDescription;
    @SerializedName("itemImage")
    @Expose
    private Object itemImage;
    @SerializedName("itemPrice")
    @Expose
    private Integer price = 0;
    @SerializedName("SLAGroupUUID")
    @Expose
    private Object sLAGroupUUID;

    public Integer getItemQuantity() {
        return itemQuantity;
    }

    public void setItemQuantity(Integer itemQuantity) {
        this.itemQuantity = itemQuantity;
    }

    public String getItemUUID() {
        return itemUUID;
    }

    public void setItemUUID(String itemUUID) {
        this.itemUUID = itemUUID;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getItemDescription() {
        return itemDescription;
    }

    public void setItemDescription(String itemDescription) {
        this.itemDescription = itemDescription;
    }

    public Object getItemImage() {
        return itemImage;
    }

    public void setItemImage(Object itemImage) {
        this.itemImage = itemImage;
    }

    public Object getsLAGroupUUID() {
        return sLAGroupUUID;
    }

    public void setsLAGroupUUID(Object sLAGroupUUID) {
        this.sLAGroupUUID = sLAGroupUUID;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Object getSLAGroupUUID() {
        return sLAGroupUUID;
    }

    public void setSLAGroupUUID(Object sLAGroupUUID) {
        this.sLAGroupUUID = sLAGroupUUID;
    }

}
