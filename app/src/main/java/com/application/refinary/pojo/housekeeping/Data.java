
package com.application.refinary.pojo.housekeeping;

import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Data {

    @SerializedName("meta")
    @Expose
    private Metadata metadata;
    @SerializedName("withCategory")
    @Expose
    private List<WithCategory> withCategory = null;

    public Metadata getMetadata() {
        return metadata;
    }

    public void setMetadata(Metadata metadata) {
        this.metadata = metadata;
    }

    @SerializedName("withoutCategory")
    @Expose
    private List<Object> withoutCategory = null;

    public List<WithCategory> getWithCategory() {
        return withCategory;
    }

    public void setWithCategory(List<WithCategory> withCategory) {
        this.withCategory = withCategory;
    }

    public List<Object> getWithoutCategory() {
        return withoutCategory;
    }

    public void setWithoutCategory(List<Object> withoutCategory) {
        this.withoutCategory = withoutCategory;
    }

}
