package com.application.refinary.model;

import com.application.refinary.pojo.emergencyservice.Data;
import com.application.refinary.pojo.housekeeping.ChildItem;
import com.application.refinary.pojo.ticketcreation.Meta;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class EmergencyServiceModel {
    @SerializedName("requestType")
    @Expose
    private String requestType;
    @SerializedName("guestUUID")
    @Expose
    private String guestUUID;
    @SerializedName("locationUUID")
    @Expose
    private String locationUUID;
    @SerializedName("bookingConfNo")
    @Expose
    private String bookingConfNo;
    @SerializedName("roomNumber")
    @Expose
    private Integer roomNumber;
    @SerializedName("items")
    @Expose
    private List<Data> serviceDetails = null;
    @SerializedName("meta")
    @Expose
    private Meta meta;


    public List<Data> getServiceDetails() {
        return serviceDetails;
    }

    public void setServiceDetails(List<Data> serviceDetails) {
        this.serviceDetails = serviceDetails;
    }

    public String getRequestType() {
        return requestType;
    }

    public void setRequestType(String requestType) {
        this.requestType = requestType;
    }

    public String getGuestUUID() {
        return guestUUID;
    }

    public void setGuestUUID(String guestUUID) {
        this.guestUUID = guestUUID;
    }

    public String getLocationUUID() {
        return locationUUID;
    }

    public void setLocationUUID(String locationUUID) {
        this.locationUUID = locationUUID;
    }

    public String getBookingConfNo() {
        return bookingConfNo;
    }

    public void setBookingConfNo(String bookingConfNo) {
        this.bookingConfNo = bookingConfNo;
    }

    public Integer getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(Integer roomNumber) {
        this.roomNumber = roomNumber;
    }



    public Meta getMeta() {
        return meta;
    }

    public void setMeta(Meta meta) {
        this.meta = meta;
    }
}
