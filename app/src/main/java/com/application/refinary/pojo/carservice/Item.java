
package com.application.refinary.pojo.carservice;

import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Item {

    @SerializedName("carTypeUUID")
    @Expose
    private String carTypeUUID;
    @SerializedName("modelName")
    @Expose
    private String modelName;
    @SerializedName("modelDescription")
    @Expose
    private String modelDescription;
    @SerializedName("modelImage")
    @Expose
    private String modelImage;
    @SerializedName("prices")
    @Expose
    private List<Price> prices = null;

    public String getCarTypeUUID() {
        return carTypeUUID;
    }

    public void setCarTypeUUID(String carTypeUUID) {
        this.carTypeUUID = carTypeUUID;
    }

    public String getModelName() {
        return modelName;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
    }

    public String getModelDescription() {
        return modelDescription;
    }

    public void setModelDescription(String modelDescription) {
        this.modelDescription = modelDescription;
    }

    public String getModelImage() {
        return modelImage;
    }

    public void setModelImage(String modelImage) {
        this.modelImage = modelImage;
    }

    public List<Price> getPrices() {
        return prices;
    }

    public void setPrices(List<Price> prices) {
        this.prices = prices;
    }

}
