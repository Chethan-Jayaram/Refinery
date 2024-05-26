
package com.application.refinary.pojo.carservice;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Meta {

    @SerializedName("headerTitle")
    @Expose
    private String headerTitle;
    @SerializedName("headerDescription")
    @Expose
    private String headerDescription;

    public String getHeaderTitle() {
        return headerTitle;
    }

    public void setHeaderTitle(String headerTitle) {
        this.headerTitle = headerTitle;
    }

    public String getHeaderDescription() {
        return headerDescription;
    }

    public void setHeaderDescription(String headerDescription) {
        this.headerDescription = headerDescription;
    }

}
