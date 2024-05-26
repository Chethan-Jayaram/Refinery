
package com.application.refinary.pojo.weather;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Meta {

    @SerializedName("location")
    @Expose
    private String location;
    @SerializedName("locationTimeZone")
    @Expose
    private String locationTimeZone;
    @SerializedName("currentTime")
    @Expose
    private String currentTime;

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getLocationTimeZone() {
        return locationTimeZone;
    }

    public void setLocationTimeZone(String locationTimeZone) {
        this.locationTimeZone = locationTimeZone;
    }

    public String getCurrentTime() {
        return currentTime;
    }

    public void setCurrentTime(String currentTime) {
        this.currentTime = currentTime;
    }

}
