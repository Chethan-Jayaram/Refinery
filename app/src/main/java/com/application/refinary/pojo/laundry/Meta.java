
package com.application.refinary.pojo.laundry;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Meta {

    @SerializedName("headerTitle")
    @Expose
    private String headerTitle;
    @SerializedName("headerDescription")
    @Expose
    private Object headerDescription;
    @SerializedName("canPlaceOrder")
    @Expose
    private Boolean canPlaceOrder;
    @SerializedName("showPrice")
    @Expose
    private Boolean showPrice;
    @SerializedName("showItemImage")
    @Expose
    private Boolean showItemImage;
    @SerializedName("showItemDescription")
    @Expose
    private Boolean showItemDescription;
    @SerializedName("serviceAvailableFrom")
    @Expose
    private String serviceAvailableFrom;
    @SerializedName("serviceAvailableTo")
    @Expose
    private String serviceAvailableTo;

    public String getHeaderTitle() {
        return headerTitle;
    }

    public void setHeaderTitle(String headerTitle) {
        this.headerTitle = headerTitle;
    }

    public Object getHeaderDescription() {
        return headerDescription;
    }

    public void setHeaderDescription(Object headerDescription) {
        this.headerDescription = headerDescription;
    }

    public Boolean getCanPlaceOrder() {
        return canPlaceOrder;
    }

    public void setCanPlaceOrder(Boolean canPlaceOrder) {
        this.canPlaceOrder = canPlaceOrder;
    }

    public Boolean getShowPrice() {
        return showPrice;
    }

    public void setShowPrice(Boolean showPrice) {
        this.showPrice = showPrice;
    }

    public Boolean getShowItemImage() {
        return showItemImage;
    }

    public void setShowItemImage(Boolean showItemImage) {
        this.showItemImage = showItemImage;
    }

    public Boolean getShowItemDescription() {
        return showItemDescription;
    }

    public void setShowItemDescription(Boolean showItemDescription) {
        this.showItemDescription = showItemDescription;
    }

    public String getServiceAvailableFrom() {
        return serviceAvailableFrom;
    }

    public void setServiceAvailableFrom(String serviceAvailableFrom) {
        this.serviceAvailableFrom = serviceAvailableFrom;
    }

    public String getServiceAvailableTo() {
        return serviceAvailableTo;
    }

    public void setServiceAvailableTo(String serviceAvailableTo) {
        this.serviceAvailableTo = serviceAvailableTo;
    }

}
