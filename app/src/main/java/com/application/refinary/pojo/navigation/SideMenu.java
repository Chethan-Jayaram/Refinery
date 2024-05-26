
package com.application.refinary.pojo.navigation;

import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SideMenu {

    @SerializedName("navigationTitle")
    @Expose
    private String navigationTitle;
    @SerializedName("navigationRoute")
    @Expose
    private String navigationRoute;
    @SerializedName("navigationSmallIcon")
    @Expose
    private String navigationSmallIcon;
    @SerializedName("navigationLargeIcon")
    @Expose
    private Object navigationLargeIcon;
    @SerializedName("hasChild")
    @Expose
    private Boolean hasChild;
    @SerializedName("subMenuItems")
    @Expose
    private List<SubMenuItem> subMenuItems = null;

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

    public String getNavigationSmallIcon() {
        return navigationSmallIcon;
    }

    public void setNavigationSmallIcon(String navigationSmallIcon) {
        this.navigationSmallIcon = navigationSmallIcon;
    }

    public Object getNavigationLargeIcon() {
        return navigationLargeIcon;
    }

    public void setNavigationLargeIcon(Object navigationLargeIcon) {
        this.navigationLargeIcon = navigationLargeIcon;
    }

    public Boolean getHasChild() {
        return hasChild;
    }

    public void setHasChild(Boolean hasChild) {
        this.hasChild = hasChild;
    }

    public List<SubMenuItem> getSubMenuItems() {
        return subMenuItems;
    }

    public void setSubMenuItems(List<SubMenuItem> subMenuItems) {
        this.subMenuItems = subMenuItems;
    }

}
