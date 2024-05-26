
package com.application.refinary.pojo.notification;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Data {

    @SerializedName("notificationUUID")
    @Expose
    private String notificationUUID;
    @SerializedName("notificationTitle")
    @Expose
    private String notificationTitle;
    @SerializedName("displayContent")
    @Expose
    private String displayContent;
    @SerializedName("notificationImage")
    @Expose
    private String notificationImage;
    @SerializedName("createdDate")
    @Expose
    private String createdDate;

    public String getNotificationUUID() {
        return notificationUUID;
    }

    public void setNotificationUUID(String notificationUUID) {
        this.notificationUUID = notificationUUID;
    }

    public String getNotificationTitle() {
        return notificationTitle;
    }

    public void setNotificationTitle(String notificationTitle) {
        this.notificationTitle = notificationTitle;
    }

    public String getDisplayContent() {
        return displayContent;
    }

    public void setDisplayContent(String displayContent) {
        this.displayContent = displayContent;
    }

    public String getNotificationImage() {
        return notificationImage;
    }

    public void setNotificationImage(String notificationImage) {
        this.notificationImage = notificationImage;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

}
