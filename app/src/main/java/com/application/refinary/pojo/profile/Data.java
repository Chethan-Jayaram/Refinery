
package com.application.refinary.pojo.profile;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Data {

    @SerializedName("firstName")
    @Expose
    private String firstName;
    @SerializedName("lastName")
    @Expose
    private String lastName;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("profileImage")
    @Expose
    private String profileImage;
    @SerializedName("gender")
    @Expose
    private Object gender;
    @SerializedName("maritalStatus")
    @Expose
    private Object maritalStatus;
    @SerializedName("dateOfBirth")
    @Expose
    private Object dateOfBirth;
    @SerializedName("spouseDateOfBirth")
    @Expose
    private Object spouseDateOfBirth;
    @SerializedName("anniversaryDate")
    @Expose
    private Object anniversaryDate;

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

    public String getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }

    public Object getGender() {
        return gender;
    }

    public void setGender(Object gender) {
        this.gender = gender;
    }

    public Object getMaritalStatus() {
        return maritalStatus;
    }

    public void setMaritalStatus(Object maritalStatus) {
        this.maritalStatus = maritalStatus;
    }

    public Object getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Object dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public Object getSpouseDateOfBirth() {
        return spouseDateOfBirth;
    }

    public void setSpouseDateOfBirth(Object spouseDateOfBirth) {
        this.spouseDateOfBirth = spouseDateOfBirth;
    }

    public Object getAnniversaryDate() {
        return anniversaryDate;
    }

    public void setAnniversaryDate(Object anniversaryDate) {
        this.anniversaryDate = anniversaryDate;
    }
}