
package com.application.refinary.pojo.mystay;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PropertyInfo__1 {

    @SerializedName("locationUUID")
    @Expose
    private String locationUUID;
    @SerializedName("propertyName")
    @Expose
    private String propertyName;
    @SerializedName("propertyImage")
    @Expose
    private String propertyImage;
    @SerializedName("propertyLocation")
    @Expose
    private String propertyLocation;
    @SerializedName("latitude")
    @Expose
    private String latitude;
    @SerializedName("longitude")
    @Expose
    private String longitude;
    @SerializedName("timeZone")
    @Expose
    private String timeZone;

    public String getLocationUUID() {
        return locationUUID;
    }

    public void setLocationUUID(String locationUUID) {
        this.locationUUID = locationUUID;
    }

    public String getPropertyName() {
        return propertyName;
    }

    public void setPropertyName(String propertyName) {
        this.propertyName = propertyName;
    }

    public String getPropertyImage() {
        return propertyImage;
    }

    public void setPropertyImage(String propertyImage) {
        this.propertyImage = propertyImage;
    }

    public String getPropertyLocation() {
        return propertyLocation;
    }

    public void setPropertyLocation(String propertyLocation) {
        this.propertyLocation = propertyLocation;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getTimeZone() {
        return timeZone;
    }

    public void setTimeZone(String timeZone) {
        this.timeZone = timeZone;
    }

}
