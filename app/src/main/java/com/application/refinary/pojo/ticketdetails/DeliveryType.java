
package com.application.refinary.pojo.ticketdetails;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DeliveryType {

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
    private List<String> availableDays = null;
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

}
