
package com.application.refinary.pojo.housekeeping;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Metadata {

    @SerializedName("headerTitle")
    @Expose
    private String header;
    @SerializedName("canPlaceOrder")
    @Expose
    private Boolean canPlaceOrder;
    @SerializedName("headerDescription")
    @Expose
    private Object description;
    @SerializedName("showPrice")
    @Expose
    private Boolean showPrice;
    @SerializedName("showDescription")
    @Expose
    private Boolean showDescription;
    @SerializedName("showImage")
    @Expose
    private Boolean showImage;
    @SerializedName("serviceAvailableTo")
    @Expose
    private String serviceAvailableTo;
    @SerializedName("serviceAvailableFrom")
    @Expose
    private String serviceAvailableFrom;
    @SerializedName("SLAGroupUUID")
    @Expose
    private Object sLAGroupUUID;

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public Boolean getCanPlaceOrder() {
        return canPlaceOrder;
    }

    public void setCanPlaceOrder(Boolean canPlaceOrder) {
        this.canPlaceOrder = canPlaceOrder;
    }

    public Object getDescription() {
        return description;
    }

    public void setDescription(Object description) {
        this.description = description;
    }

    public Boolean getShowPrice() {
        return showPrice;
    }

    public void setShowPrice(Boolean showPrice) {
        this.showPrice = showPrice;
    }

    public Boolean getShowDescription() {
        return showDescription;
    }

    public void setShowDescription(Boolean showDescription) {
        this.showDescription = showDescription;
    }

    public Boolean getShowImage() {
        return showImage;
    }

    public void setShowImage(Boolean showImage) {
        this.showImage = showImage;
    }

    public String getServiceAvailableTo() {
        return serviceAvailableTo;
    }

    public void setServiceAvailableTo(String serviceAvailableTo) {
        this.serviceAvailableTo = serviceAvailableTo;
    }

    public String getServiceAvailableFrom() {
        return serviceAvailableFrom;
    }

    public void setServiceAvailableFrom(String serviceAvailableFrom) {
        this.serviceAvailableFrom = serviceAvailableFrom;
    }

    public Object getSLAGroupUUID() {
        return sLAGroupUUID;
    }

    public void setSLAGroupUUID(Object sLAGroupUUID) {
        this.sLAGroupUUID = sLAGroupUUID;
    }

}
