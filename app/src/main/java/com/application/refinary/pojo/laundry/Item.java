
package com.application.refinary.pojo.laundry;

import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Item {

    @SerializedName("laundryCategoryUUID")
    @Expose
    private String laundryCategoryUUID;
    @SerializedName("categoryName")
    @Expose
    private String categoryName;
    @SerializedName("categoryDescription")
    @Expose
    private Object categoryDescription;
    @SerializedName("items")
    @Expose
    private List<Item__1> items = null;

    public String getLaundryCategoryUUID() {
        return laundryCategoryUUID;
    }

    public void setLaundryCategoryUUID(String laundryCategoryUUID) {
        this.laundryCategoryUUID = laundryCategoryUUID;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public Object getCategoryDescription() {
        return categoryDescription;
    }

    public void setCategoryDescription(Object categoryDescription) {
        this.categoryDescription = categoryDescription;
    }

    public List<Item__1> getItems() {
        return items;
    }

    public void setItems(List<Item__1> items) {
        this.items = items;
    }

}
