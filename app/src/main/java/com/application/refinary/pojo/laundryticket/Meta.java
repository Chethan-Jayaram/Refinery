
package com.application.refinary.pojo.laundryticket;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Meta {

    @SerializedName("deliveryTypeUUID")
    @Expose
    private String deliveryTypeUUID;
    @SerializedName("surchargePercentage")
    @Expose
    private Integer surchargePercentage;
    @SerializedName("specialInstructions")
    @Expose
    private String specialInstructions;

    public String getDeliveryTypeUUID() {
        return deliveryTypeUUID;
    }

    public void setDeliveryTypeUUID(String deliveryTypeUUID) {
        this.deliveryTypeUUID = deliveryTypeUUID;
    }

    public Integer getSurchargePercentage() {
        return surchargePercentage;
    }

    public void setSurchargePercentage(Integer surchargePercentage) {
        this.surchargePercentage = surchargePercentage;
    }

    public String getSpecialInstructions() {
        return specialInstructions;
    }

    public void setSpecialInstructions(String specialInstructions) {
        this.specialInstructions = specialInstructions;
    }

}
