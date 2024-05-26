
package com.application.refinary.pojo.alltickets;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OrderActivity {

    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("statusDescription")
    @Expose
    private String statusDescription;
    @SerializedName("remarks")
    @Expose
    private Object remarks;
    @SerializedName("createdDate")
    @Expose
    private String createdDate;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatusDescription() {
        return statusDescription;
    }

    public void setStatusDescription(String statusDescription) {
        this.statusDescription = statusDescription;
    }

    public Object getRemarks() {
        return remarks;
    }

    public void setRemarks(Object remarks) {
        this.remarks = remarks;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

}
