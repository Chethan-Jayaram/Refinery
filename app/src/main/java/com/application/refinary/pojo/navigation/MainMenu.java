
package com.application.refinary.pojo.navigation;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MainMenu {

    @SerializedName("navigationTitle")
    @Expose
    private String navigationTitle;
    @SerializedName("navigationRoute")
    @Expose
    private String navigationRoute;
    @SerializedName("navigationSmallIcon")
    @Expose
    private Object navigationSmallIcon;
    @SerializedName("navigationLargeIcon")
    @Expose
    private Object navigationLargeIcon;

    public String getNavigationTitle() {
        return navigationTitle;
    }

    public void setNavigationTitle(String navigationTitle) {
        this.navigationTitle = navigationTitle;
    }

    public String getNavigationRoute() {
        return navigationRoute;
    }

    public void setNavigationRoute(String navigationRoute) {
        this.navigationRoute = navigationRoute;
    }

    public Object getNavigationSmallIcon() {
        return navigationSmallIcon;
    }

    public void setNavigationSmallIcon(Object navigationSmallIcon) {
        this.navigationSmallIcon = navigationSmallIcon;
    }

    public Object getNavigationLargeIcon() {
        return navigationLargeIcon;
    }

    public void setNavigationLargeIcon(Object navigationLargeIcon) {
        this.navigationLargeIcon = navigationLargeIcon;
    }

}
