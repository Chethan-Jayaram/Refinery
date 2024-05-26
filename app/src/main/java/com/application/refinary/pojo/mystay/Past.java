
package com.application.refinary.pojo.mystay;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Past implements Parcelable {

    @SerializedName("bookingConfNo")
    @Expose
    private String bookingConfNo;
    @SerializedName("resStatus")
    @Expose
    private String resStatus;
    @SerializedName("checkInDate")
    @Expose
    private String checkInDate;
    @SerializedName("checkOutDate")
    @Expose
    private String checkOutDate;
    @SerializedName("roomDetails")
    @Expose
    private List<RoomDetail__2> roomDetails = null;
    @SerializedName("propertyInfo")
    @Expose
    private PropertyInfo__2 propertyInfo;

    public String getBookingConfNo() {
        return bookingConfNo;
    }

    public void setBookingConfNo(String bookingConfNo) {
        this.bookingConfNo = bookingConfNo;
    }

    public String getResStatus() {
        return resStatus;
    }

    public void setResStatus(String resStatus) {
        this.resStatus = resStatus;
    }

    public String getCheckInDate() {
        return checkInDate;
    }

    public void setCheckInDate(String checkInDate) {
        this.checkInDate = checkInDate;
    }

    public String getCheckOutDate() {
        return checkOutDate;
    }

    public void setCheckOutDate(String checkOutDate) {
        this.checkOutDate = checkOutDate;
    }

    public List<RoomDetail__2> getRoomDetails() {
        return roomDetails;
    }

    public void setRoomDetails(List<RoomDetail__2> roomDetails) {
        this.roomDetails = roomDetails;
    }

    public PropertyInfo__2 getPropertyInfo() {
        return propertyInfo;
    }

    public void setPropertyInfo(PropertyInfo__2 propertyInfo) {
        this.propertyInfo = propertyInfo;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

    }
}
