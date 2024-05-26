package com.application.refinary.model;

import com.application.refinary.pojo.laundry.Item__1;
import com.application.refinary.pojo.laundryticket.Item;
import com.application.refinary.pojo.laundryticket.Meta;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class LaundryModel {
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
    private List<Item__1> items = null;
    @SerializedName("meta")
    @Expose
    private com.application.refinary.pojo.laundryticket.Meta meta;

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

    public List<Item__1> getItems() {
        return items;
    }

    public void setItems(List<Item__1> items) {
        this.items = items;
    }

    public Meta getMeta() {
        return meta;
    }

    public void setMeta(Meta meta) {
        this.meta = meta;
    }


}