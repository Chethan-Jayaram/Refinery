package com.application.refinary.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.application.refinary.pojo.emergencyservice.Data;
import com.application.refinary.pojo.housekeeping.ChildItem;
import com.application.refinary.pojo.housekeeping.WithCategory;
import com.application.refinary.pojo.laundry.Item;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ModuleSegmentModel implements Parcelable {

    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("booking")
    @Expose
    private Integer booking;


    @SerializedName("delivery_type")
    @Expose
    private String deliveryType;

    public String getDeliveryType() {
        return deliveryType;
    }

    public void setDeliveryType(String deliveryType) {
        this.deliveryType = deliveryType;
    }

    @SerializedName("room_no")
    @Expose
    private String room_no;

    public String getRoom_no() {
        return room_no;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getBooking() {
        return booking;
    }

    public void setBooking(Integer booking) {
        this.booking = booking;
    }

    public List<WithCategory> getDetails() {
        return details;
    }

    public void setDetails(List<WithCategory> details) {
        this.details = details;
    }

    public void setRoom_no(String room_no) {
        this.room_no = room_no;
    }

    @SerializedName("details")
    @Expose
    private List<WithCategory> details = null;

    @SerializedName("details")
    @Expose
    private List<Item> details1 = null;


    public List<Item> getDetails1() {
        return details1;
    }

    public void setDetails1(List<Item> details1) {
        this.details1 = details1;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

    }
}
