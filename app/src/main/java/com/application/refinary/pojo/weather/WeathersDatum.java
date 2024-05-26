
package com.application.refinary.pojo.weather;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class WeathersDatum {

    @SerializedName("weatherDate")
    @Expose
    private String weatherDate;
    @SerializedName("dayData")
    @Expose
    private DayData dayData;
    @SerializedName("astroData")
    @Expose
    private AstroData astroData;
    @SerializedName("dateType")
    @Expose
    private String dateType;

    public String getWeatherDate() {
        return weatherDate;
    }

    public void setWeatherDate(String weatherDate) {
        this.weatherDate = weatherDate;
    }

    public DayData getDayData() {
        return dayData;
    }

    public void setDayData(DayData dayData) {
        this.dayData = dayData;
    }

    public AstroData getAstroData() {
        return astroData;
    }

    public void setAstroData(AstroData astroData) {
        this.astroData = astroData;
    }

    public String getDateType() {
        return dateType;
    }

    public void setDateType(String dateType) {
        this.dateType = dateType;
    }

}
