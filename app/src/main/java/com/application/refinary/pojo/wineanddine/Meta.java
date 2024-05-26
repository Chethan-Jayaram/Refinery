
package com.application.refinary.pojo.wineanddine;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Meta {

    @SerializedName("headerTitle")
    @Expose
    private Object headerTitle;
    @SerializedName("headerDescription")
    @Expose
    private Object headerDescription;

    public Object getHeaderTitle() {
        return headerTitle;
    }

    public void setHeaderTitle(Object headerTitle) {
        this.headerTitle = headerTitle;
    }

    public Object getHeaderDescription() {
        return headerDescription;
    }

    public void setHeaderDescription(Object headerDescription) {
        this.headerDescription = headerDescription;
    }

}
