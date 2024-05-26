
package com.application.refinary.pojo.loginmpin;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GuestInfo {

    @SerializedName("guestUUID")
    @Expose
    private String guestUUID;
    @SerializedName("firstName")
    @Expose
    private String firstName;
    @SerializedName("lastName")
    @Expose
    private String lastName;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("dialCode")
    @Expose
    private String dialCode;
    @SerializedName("mobile")
    @Expose
    private String mobile;
    @SerializedName("pmsID")
    @Expose
    private String pmsID;
    @SerializedName("isOTPVerified")
    @Expose
    private Boolean isOTPVerified;
    @SerializedName("isActive")
    @Expose
    private Boolean isActive;
    @SerializedName("profileImage")
    @Expose
    private String profileImage;
    @SerializedName("gender")
    @Expose
    private String gender;
    @SerializedName("maritalStatus")
    @Expose
    private String maritalStatus;
    @SerializedName("dateOfBirth")
    @Expose
    private String dateOfBirth;
    @SerializedName("spouseDateOfBirth")
    @Expose
    private String spouseDateOfBirth;
    @SerializedName("anniversaryDate")
    @Expose
    private String anniversaryDate;

    public String getGuestUUID() {
        return guestUUID;
    }

    public void setGuestUUID(String guestUUID) {
        this.guestUUID = guestUUID;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDialCode() {
        return dialCode;
    }

    public void setDialCode(String dialCode) {
        this.dialCode = dialCode;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getPmsID() {
        return pmsID;
    }

    public void setPmsID(String pmsID) {
        this.pmsID = pmsID;
    }

    public Boolean getOTPVerified() {
        return isOTPVerified;
    }

    public void setOTPVerified(Boolean OTPVerified) {
        isOTPVerified = OTPVerified;
    }

    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }

    public String getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getMaritalStatus() {
        return maritalStatus;
    }

    public void setMaritalStatus(String maritalStatus) {
        this.maritalStatus = maritalStatus;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getSpouseDateOfBirth() {
        return spouseDateOfBirth;
    }

    public void setSpouseDateOfBirth(String spouseDateOfBirth) {
        this.spouseDateOfBirth = spouseDateOfBirth;
    }

    public String getAnniversaryDate() {
        return anniversaryDate;
    }

    public void setAnniversaryDate(String anniversaryDate) {
        this.anniversaryDate = anniversaryDate;
    }
}
