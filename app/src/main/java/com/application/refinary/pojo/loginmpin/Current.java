
package com.application.refinary.pojo.loginmpin;

import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Current {

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
    private List<RoomDetail__1> roomDetails = null;
    @SerializedName("propertyInfo")
    @Expose
    private PropertyInfo__1 propertyInfo;

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

    public List<RoomDetail__1> getRoomDetails() {
        return roomDetails;
    }

    public void setRoomDetails(List<RoomDetail__1> roomDetails) {
        this.roomDetails = roomDetails;
    }

    public PropertyInfo__1 getPropertyInfo() {
        return propertyInfo;
    }

    public void setPropertyInfo(PropertyInfo__1 propertyInfo) {
        this.propertyInfo = propertyInfo;
    }

}