
package com.application.refinary.pojo.sightseeing;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Place {

    @SerializedName("placeUUID")
    @Expose
    private String placeUUID;
    @SerializedName("placeName")
    @Expose
    private String placeName;
    @SerializedName("placeLocation")
    @Expose
    private String placeLocation;
    @SerializedName("placeDescription")
    @Expose
    private String placeDescription;
    @SerializedName("placeTimings")
    @Expose
    private String placeTimings;
    @SerializedName("distanceFromHotel")
    @Expose
    private String distanceFromHotel;
    @SerializedName("images")
    @Expose
    private List<Image> images = null;

    @SerializedName("geoCodes")
    @Expose
    private GeoCodes geoCodes;

    public GeoCodes getGeoCodes() {
        return geoCodes;
    }

    public void setGeoCodes(GeoCodes geoCodes) {
        this.geoCodes = geoCodes;
    }

    public String getPlaceUUID() {
        return placeUUID;
    }

    public void setPlaceUUID(String placeUUID) {
        this.placeUUID = placeUUID;
    }

    public String getPlaceName() {
        return placeName;
    }

    public void setPlaceName(String placeName) {
        this.placeName = placeName;
    }

    public String getPlaceLocation() {
        return placeLocation;
    }

    public void setPlaceLocation(String placeLocation) {
        this.placeLocation = placeLocation;
    }

    public String getPlaceDescription() {
        return placeDescription;
    }

    public void setPlaceDescription(String placeDescription) {
        this.placeDescription = placeDescription;
    }

    public String getPlaceTimings() {
        return placeTimings;
    }

    public void setPlaceTimings(String placeTimings) {
        this.placeTimings = placeTimings;
    }

    public String getDistanceFromHotel() {
        return distanceFromHotel;
    }

    public void setDistanceFromHotel(String distanceFromHotel) {
        this.distanceFromHotel = distanceFromHotel;
    }

    public List<Image> getImages() {
        return images;
    }

    public void setImages(List<Image> images) {
        this.images = images;
    }

}
