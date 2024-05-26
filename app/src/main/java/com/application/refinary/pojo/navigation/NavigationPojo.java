
package com.application.refinary.pojo.navigation;

import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class NavigationPojo {

    @SerializedName("sideMenu")
    @Expose
    private List<SideMenu> sideMenu = null;
    @SerializedName("mainMenu")
    @Expose
    private List<MainMenu> mainMenu = null;

    public List<SideMenu> getSideMenu() {
        return sideMenu;
    }

    public void setSideMenu(List<SideMenu> sideMenu) {
        this.sideMenu = sideMenu;
    }

    public List<MainMenu> getMainMenu() {
        return mainMenu;
    }

    public void setMainMenu(List<MainMenu> mainMenu) {
        this.mainMenu = mainMenu;
    }

}
