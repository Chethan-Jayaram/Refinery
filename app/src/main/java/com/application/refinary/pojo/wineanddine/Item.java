
package com.application.refinary.pojo.wineanddine;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Item {

    @SerializedName("itemUUID")
    @Expose
    private String itemUUID;
    @SerializedName("itemName")
    @Expose
    private String itemName;
    @SerializedName("itemDescription")
    @Expose
    private String itemDescription;
    @SerializedName("itemImage")
    @Expose
    private String itemImage;
    @SerializedName("helpLineText")
    @Expose
    private String helpLineText;
    @SerializedName("locatationText")
    @Expose
    private Object locatationText;
    @SerializedName("timingText")
    @Expose
    private Object timingText;
    @SerializedName("externalLink")
    @Expose
    private String externalLink;

    public String getItemUUID() {
        return itemUUID;
    }

    public void setItemUUID(String itemUUID) {
        this.itemUUID = itemUUID;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getItemDescription() {
        return itemDescription;
    }

    public void setItemDescription(String itemDescription) {
        this.itemDescription = itemDescription;
    }

    public String getItemImage() {
        return itemImage;
    }

    public void setItemImage(String itemImage) {
        this.itemImage = itemImage;
    }

    public String getHelpLineText() {
        return helpLineText;
    }

    public void setHelpLineText(String helpLineText) {
        this.helpLineText = helpLineText;
    }

    public Object getLocatationText() {
        return locatationText;
    }

    public void setLocatationText(Object locatationText) {
        this.locatationText = locatationText;
    }

    public Object getTimingText() {
        return timingText;
    }

    public void setTimingText(Object timingText) {
        this.timingText = timingText;
    }

    public String getExternalLink() {
        return externalLink;
    }

    public void setExternalLink(String externalLink) {
        this.externalLink = externalLink;
    }

}
