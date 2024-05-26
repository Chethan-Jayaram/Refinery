
package com.application.refinary.pojo.mystay;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RoomDetail {

    @SerializedName("roomNumber")
    @Expose
    private Integer roomNumber;
    @SerializedName("roomTypeCode")
    @Expose
    private String roomTypeCode;

    public Integer getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(Integer roomNumber) {
        this.roomNumber = roomNumber;
    }

    public String getRoomTypeCode() {
        return roomTypeCode;
    }

    public void setRoomTypeCode(String roomTypeCode) {
        this.roomTypeCode = roomTypeCode;
    }

}
