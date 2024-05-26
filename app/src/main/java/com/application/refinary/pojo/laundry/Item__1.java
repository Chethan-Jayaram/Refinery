
package com.application.refinary.pojo.laundry;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Item__1 implements Parcelable {

    @SerializedName("laundryItemUUID")
    @Expose
    private String laundryItemUUID;
    @SerializedName("itemUUID")
    @Expose
    private String itemUUID;
    @SerializedName("itemName")
    @Expose
    private String itemName;
    @SerializedName("itemDescription")
    @Expose
    private Object itemDescription;
    @SerializedName("itemPrice")
    @Expose
    private String itemPrice;

    @SerializedName("minOrderQuantity")
    @Expose
    private Integer minOrderQuantity;
    @SerializedName("maxOrderQuantity")
    @Expose
    private Integer maxOrderQuantity;

    @SerializedName("itemQuantity")
    @Expose
    private Integer count=0;

    public String getItemUUID() {
        return itemUUID;
    }

    public void setItemUUID(String itemUUID) {
        this.itemUUID = itemUUID;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public String getLaundryItemUUID() {
        return laundryItemUUID;
    }

    public void setLaundryItemUUID(String laundryItemUUID) {
        this.laundryItemUUID = laundryItemUUID;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public Object getItemDescription() {
        return itemDescription;
    }

    public void setItemDescription(Object itemDescription) {
        this.itemDescription = itemDescription;
    }

    public String getItemPrice() {
        return itemPrice;
    }

    public void setItemPrice(String itemPrice) {
        this.itemPrice = itemPrice;
    }

    public Integer getMinOrderQuantity() {
        return minOrderQuantity;
    }

    public void setMinOrderQuantity(Integer minOrderQuantity) {
        this.minOrderQuantity = minOrderQuantity;
    }

    public Integer getMaxOrderQuantity() {
        return maxOrderQuantity;
    }

    public void setMaxOrderQuantity(Integer maxOrderQuantity) {
        this.maxOrderQuantity = maxOrderQuantity;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

    }
}
