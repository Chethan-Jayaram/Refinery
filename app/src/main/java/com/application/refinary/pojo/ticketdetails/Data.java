
package com.application.refinary.pojo.ticketdetails;

import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Data {

    @SerializedName("orderUUID")
    @Expose
    private String orderUUID;
    @SerializedName("orderNumber")
    @Expose
    private String orderNumber;
    @SerializedName("requestType")
    @Expose
    private String requestType;
    @SerializedName("roomNumber")
    @Expose
    private Integer roomNumber;
    @SerializedName("totalBeforeTax")
    @Expose
    private String totalBeforeTax;
    @SerializedName("totalAfterTax")
    @Expose
    private String totalAfterTax;
    @SerializedName("totalTax")
    @Expose
    private String totalTax;
    @SerializedName("surchargePercentage")
    @Expose
    private Integer surchargePercentage;
    @SerializedName("specialInstructions")
    @Expose
    private String specialInstructions;
    @SerializedName("createdDate")
    @Expose
    private String createdDate;
    @SerializedName("orderStatus")
    @Expose
    private String orderStatus;
    @SerializedName("acceptedDate")
    @Expose
    private Object acceptedDate;
    @SerializedName("deliveryDate")
    @Expose
    private Object deliveryDate;
    @SerializedName("deliveredDate")
    @Expose
    private Object deliveredDate;
    @SerializedName("deliveredUserUUID")
    @Expose
    private Object deliveredUserUUID;
    @SerializedName("showPrice")
    @Expose
    private Boolean showPrice;
    @SerializedName("showQuanity")
    @Expose
    private Boolean showQuanity;
    @SerializedName("deliveryType")
    @Expose
    private DeliveryType deliveryType;
    @SerializedName("orderItems")
    @Expose
    private List<OrderItem> orderItems = null;
    @SerializedName("orderActivities")
    @Expose
    private List<OrderActivity> orderActivities = null;

    public String getOrderUUID() {
        return orderUUID;
    }

    public void setOrderUUID(String orderUUID) {
        this.orderUUID = orderUUID;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public String getRequestType() {
        return requestType;
    }

    public void setRequestType(String requestType) {
        this.requestType = requestType;
    }

    public Integer getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(Integer roomNumber) {
        this.roomNumber = roomNumber;
    }

    public String getTotalBeforeTax() {
        return totalBeforeTax;
    }

    public void setTotalBeforeTax(String totalBeforeTax) {
        this.totalBeforeTax = totalBeforeTax;
    }

    public String getTotalAfterTax() {
        return totalAfterTax;
    }

    public void setTotalAfterTax(String totalAfterTax) {
        this.totalAfterTax = totalAfterTax;
    }

    public String getTotalTax() {
        return totalTax;
    }

    public void setTotalTax(String totalTax) {
        this.totalTax = totalTax;
    }

    public Integer getSurchargePercentage() {
        return surchargePercentage;
    }

    public void setSurchargePercentage(Integer surchargePercentage) {
        this.surchargePercentage = surchargePercentage;
    }

    public String getSpecialInstructions() {
        return specialInstructions;
    }

    public void setSpecialInstructions(String specialInstructions) {
        this.specialInstructions = specialInstructions;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public Object getAcceptedDate() {
        return acceptedDate;
    }

    public void setAcceptedDate(Object acceptedDate) {
        this.acceptedDate = acceptedDate;
    }

    public Object getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(Object deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public Object getDeliveredDate() {
        return deliveredDate;
    }

    public void setDeliveredDate(Object deliveredDate) {
        this.deliveredDate = deliveredDate;
    }

    public Object getDeliveredUserUUID() {
        return deliveredUserUUID;
    }

    public void setDeliveredUserUUID(Object deliveredUserUUID) {
        this.deliveredUserUUID = deliveredUserUUID;
    }

    public Boolean getShowPrice() {
        return showPrice;
    }

    public void setShowPrice(Boolean showPrice) {
        this.showPrice = showPrice;
    }

    public Boolean getShowQuanity() {
        return showQuanity;
    }

    public void setShowQuanity(Boolean showQuanity) {
        this.showQuanity = showQuanity;
    }

    public DeliveryType getDeliveryType() {
        return deliveryType;
    }

    public void setDeliveryType(DeliveryType deliveryType) {
        this.deliveryType = deliveryType;
    }

    public List<OrderItem> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }

    public List<OrderActivity> getOrderActivities() {
        return orderActivities;
    }

    public void setOrderActivities(List<OrderActivity> orderActivities) {
        this.orderActivities = orderActivities;
    }

}
