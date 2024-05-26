
package com.application.refinary.pojo.mystay;

import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Data {

    @SerializedName("upcoming")
    @Expose
    private List<Upcoming> upcoming = null;
    @SerializedName("current")
    @Expose
    private List<Current> current = null;
    @SerializedName("past")
    @Expose
    private List<Past> past = null;

    public List<Upcoming> getUpcoming() {
        return upcoming;
    }

    public void setUpcoming(List<Upcoming> upcoming) {
        this.upcoming = upcoming;
    }

    public List<Current> getCurrent() {
        return current;
    }

    public void setCurrent(List<Current> current) {
        this.current = current;
    }

    public List<Past> getPast() {
        return past;
    }

    public void setPast(List<Past> past) {
        this.past = past;
    }

}
