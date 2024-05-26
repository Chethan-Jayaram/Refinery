
package com.application.refinary.pojo.housekeeping;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ChildItem implements Parcelable  {

    @SerializedName("itemUUID")
    @Expose
    private String itemUUID;
    @SerializedName("itemName")
    @Expose
    private String itemName;
    @SerializedName("itemDescription")
    @Expose
    private String itemDescription;
    @SerializedName("itemImage")
    @Expose
    private Object itemImage;
    @SerializedName("minQuantity")
    @Expose
    private Integer minQuantity;
    @SerializedName("maxQuantity")
    @Expose
    private Integer maxQuantity;
    @SerializedName("price")
    @Expose
    private String price;
    @SerializedName("isSingleItemOrder")
    @Expose
    private Integer isSingleItemOrder;
    @SerializedName("isIncludeOtherItems")
    @Expose
    private Integer isIncludeOtherItems;

    @SerializedName("itemQuantity")
    @Expose
    private Integer count=0;

    public Integer getItemPrice() {
        return itemPrice;
    }

    public void setItemPrice(Integer itemPrice) {
        this.itemPrice = itemPrice;
    }

    @SerializedName("itemPrice")
    @Expose
    private Integer itemPrice=0;

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
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

    public Integer getMinQuantity() {
        return minQuantity;
    }

    public void setMinQuantity(Integer minQuantity) {
        this.minQuantity = minQuantity;
    }

    public Integer getMaxQuantity() {
        return maxQuantity;
    }

    public void setMaxQuantity(Integer maxQuantity) {
        this.maxQuantity = maxQuantity;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public Integer getIsSingleItemOrder() {
        return isSingleItemOrder;
    }

    public void setIsSingleItemOrder(Integer isSingleItemOrder) {
        this.isSingleItemOrder = isSingleItemOrder;
    }

    public Integer getIsIncludeOtherItems() {
        return isIncludeOtherItems;
    }

    public void setIsIncludeOtherItems(Integer isIncludeOtherItems) {
        this.isIncludeOtherItems = isIncludeOtherItems;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

    }
}
