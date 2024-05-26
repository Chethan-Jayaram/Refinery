
package com.application.refinary.pojo.sightseeing;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Meta {

    @SerializedName("headerTitle")
    @Expose
    private String headerTitle;
    @SerializedName("headerDescription")
    @Expose
    private String headerDescription;
    @SerializedName("page")
    @Expose
    private Integer page;
    @SerializedName("limit")
    @Expose
    private Integer limit;
    @SerializedName("itemCount")
    @Expose
    private Integer itemCount;
    @SerializedName("pageCount")
    @Expose
    private Integer pageCount;
    @SerializedName("hasPreviousPage")
    @Expose
    private Boolean hasPreviousPage;
    @SerializedName("hasNextPage")
    @Expose
    private Boolean hasNextPage;

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

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getLimit() {
        return limit;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }

    public Integer getItemCount() {
        return itemCount;
    }

    public void setItemCount(Integer itemCount) {
        this.itemCount = itemCount;
    }

    public Integer getPageCount() {
        return pageCount;
    }

    public void setPageCount(Integer pageCount) {
        this.pageCount = pageCount;
    }

    public Boolean getHasPreviousPage() {
        return hasPreviousPage;
    }

    public void setHasPreviousPage(Boolean hasPreviousPage) {
        this.hasPreviousPage = hasPreviousPage;
    }

    public Boolean getHasNextPage() {
        return hasNextPage;
    }

    public void setHasNextPage(Boolean hasNextPage) {
        this.hasNextPage = hasNextPage;
    }

}
