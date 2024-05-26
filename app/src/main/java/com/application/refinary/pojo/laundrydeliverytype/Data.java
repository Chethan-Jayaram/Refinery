
package com.application.refinary.pojo.laundrydeliverytype;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Data implements Parcelable {


    @SerializedName("deliveryTypeUUID")
    @Expose
    private String deliveryTypeUUID;
    @SerializedName("deliveryTypeName")
    @Expose
    private String deliveryTypeName;
    @SerializedName("deliveryTypeDescription")
    @Expose
    private String deliveryTypeDescription;
    @SerializedName("availableDays")
    @Expose
    private List<String> availableDays;
    @SerializedName("availableFrom")
    @Expose
    private String availableFrom;
    @SerializedName("availableTo")
    @Expose
    private String availableTo;
    @SerializedName("minOrderAmount")
    @Expose
    private String minOrderAmount;
    @SerializedName("surchargePercentage")
    @Expose
    private Integer surchargePercentage;

    @SerializedName("setSelected")
    @Expose
    private Boolean Selected =false;

    public Boolean getSelected() {
        return Selected;
    }

    public void setSelected(Boolean selected) {
        Selected = selected;
    }

    public String getDeliveryTypeUUID() {
        return deliveryTypeUUID;
    }

    public void setDeliveryTypeUUID(String deliveryTypeUUID) {
        this.deliveryTypeUUID = deliveryTypeUUID;
    }

    public String getDeliveryTypeName() {
        return deliveryTypeName;
    }

    public void setDeliveryTypeName(String deliveryTypeName) {
        this.deliveryTypeName = deliveryTypeName;
    }

    public String getDeliveryTypeDescription() {
        return deliveryTypeDescription;
    }

    public void setDeliveryTypeDescription(String deliveryTypeDescription) {
        this.deliveryTypeDescription = deliveryTypeDescription;
    }

    public List<String> getAvailableDays() {
        return availableDays;
    }

    public void setAvailableDays(List<String> availableDays) {
        this.availableDays = availableDays;
    }

    public String getAvailableFrom() {
        return availableFrom;
    }

    public void setAvailableFrom(String availableFrom) {
        this.availableFrom = availableFrom;
    }

    public String getAvailableTo() {
        return availableTo;
    }

    public void setAvailableTo(String availableTo) {
        this.availableTo = availableTo;
    }

    public String getMinOrderAmount() {
        return minOrderAmount;
    }

    public void setMinOrderAmount(String minOrderAmount) {
        this.minOrderAmount = minOrderAmount;
    }

    public Integer getSurchargePercentage() {
        return surchargePercentage;
    }

    public void setSurchargePercentage(Integer surchargePercentage) {
        this.surchargePercentage = surchargePercentage;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

    }
}
