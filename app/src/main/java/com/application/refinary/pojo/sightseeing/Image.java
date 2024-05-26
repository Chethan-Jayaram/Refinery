
package com.application.refinary.pojo.sightseeing;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Image {

    @SerializedName("placeImageFileName")
    @Expose
    private String placeImageFileName;
    @SerializedName("placeImageFilePath")
    @Expose
    private String placeImageFilePath;
    @SerializedName("placeImageFileSize")
    @Expose
    private String placeImageFileSize;
    @SerializedName("placeImageFileMimeType")
    @Expose
    private String placeImageFileMimeType;

    public String getPlaceImageFileName() {
        return placeImageFileName;
    }

    public void setPlaceImageFileName(String placeImageFileName) {
        this.placeImageFileName = placeImageFileName;
    }

    public String getPlaceImageFilePath() {
        return placeImageFilePath;
    }

    public void setPlaceImageFilePath(String placeImageFilePath) {
        this.placeImageFilePath = placeImageFilePath;
    }

    public String getPlaceImageFileSize() {
        return placeImageFileSize;
    }

    public void setPlaceImageFileSize(String placeImageFileSize) {
        this.placeImageFileSize = placeImageFileSize;
    }

    public String getPlaceImageFileMimeType() {
        return placeImageFileMimeType;
    }

    public void setPlaceImageFileMimeType(String placeImageFileMimeType) {
        this.placeImageFileMimeType = placeImageFileMimeType;
    }

}
