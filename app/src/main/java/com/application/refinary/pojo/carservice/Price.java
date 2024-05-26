
package com.application.refinary.pojo.carservice;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Price {

    @SerializedName("rateType")
    @Expose
    private String rateType;
    @SerializedName("destinationName")
    @Expose
    private String destinationName;
    @SerializedName("pickupRate")
    @Expose
    private String pickupRate;
    @SerializedName("dropRate")
    @Expose
    private Object dropRate;

    public String getRateType() {
        return rateType;
    }

    public void setRateType(String rateType) {
        this.rateType = rateType;
    }

    public String getDestinationName() {
        return destinationName;
    }

    public void setDestinationName(String destinationName) {
        this.destinationName = destinationName;
    }

    public String getPickupRate() {
        return pickupRate;
    }

    public void setPickupRate(String pickupRate) {
        this.pickupRate = pickupRate;
    }

    public Object getDropRate() {
        return dropRate;
    }

    public void setDropRate(Object dropRate) {
        this.dropRate = dropRate;
    }

}
