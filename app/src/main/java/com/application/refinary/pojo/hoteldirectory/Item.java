
package com.application.refinary.pojo.hoteldirectory;

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

}
