
package com.application.refinary.pojo.weather;

import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Data {

    @SerializedName("meta")
    @Expose
    private Meta meta;
    @SerializedName("weathersData")
    @Expose
    private List<WeathersDatum> weathersData = null;

    public Meta getMeta() {
        return meta;
    }

    public void setMeta(Meta meta) {
        this.meta = meta;
    }

    public List<WeathersDatum> getWeathersData() {
        return weathersData;
    }

    public void setWeathersData(List<WeathersDatum> weathersData) {
        this.weathersData = weathersData;
    }

}
