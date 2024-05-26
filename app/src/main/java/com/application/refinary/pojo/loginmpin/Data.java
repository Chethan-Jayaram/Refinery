
package com.application.refinary.pojo.loginmpin;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Data {

    @SerializedName("access_token")
    @Expose
    private String accessToken;
    @SerializedName("guestInfo")
    @Expose
    private GuestInfo guestInfo;
    @SerializedName("meta")
    @Expose
    private Meta meta;
    @SerializedName("reservationInfo")
    @Expose
    private ReservationInfo reservationInfo;

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public GuestInfo getGuestInfo() {
        return guestInfo;
    }

    public void setGuestInfo(GuestInfo guestInfo) {
        this.guestInfo = guestInfo;
    }

    public Meta getMeta() {
        return meta;
    }

    public void setMeta(Meta meta) {
        this.meta = meta;
    }

    public ReservationInfo getReservationInfo() {
        return reservationInfo;
    }

    public void setReservationInfo(ReservationInfo reservationInfo) {
        this.reservationInfo = reservationInfo;
    }

}
