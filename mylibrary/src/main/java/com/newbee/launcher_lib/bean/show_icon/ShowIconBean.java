package com.newbee.launcher_lib.bean.show_icon;

import com.newbee.system_applist_lib.systemapp.bean.ResultSystemAppInfoBean;
import com.newbee.system_applist_lib.systemapp.bean.SystemAppInfoBean;

import java.io.Serializable;

public class ShowIconBean implements Serializable {
    private ShowIconType iconType;
    private SystemAppInfoBean systemAppInfoBean;
    private ResultSystemAppInfoBean resultSystemAppInfoBean;

    private String iconName;
    private int index;




    public ShowIconType getIconType() {
        return iconType;
    }

    public void setIconType(ShowIconType iconType) {
        this.iconType = iconType;
    }



    public String getIconName() {
        return iconName;
    }

    public void setIconName(String iconName) {
        this.iconName = iconName;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public SystemAppInfoBean getSystemAppInfoBean() {
        return systemAppInfoBean;
    }

    public void setSystemAppInfoBean(SystemAppInfoBean systemAppInfoBean) {
        this.systemAppInfoBean = systemAppInfoBean;
    }

    public ResultSystemAppInfoBean getResultSystemAppInfoBean() {
        return resultSystemAppInfoBean;
    }

    public void setResultSystemAppInfoBean(ResultSystemAppInfoBean resultSystemAppInfoBean) {
        this.resultSystemAppInfoBean = resultSystemAppInfoBean;
    }

    @Override
    public String toString() {
        return "ShowIconBean{" +
                "iconType=" + iconType +
                ", systemAppInfoBean=" + systemAppInfoBean +
                ", resultSystemAppInfoBean=" + resultSystemAppInfoBean +
                ", iconName='" + iconName + '\'' +
                ", index=" + index +
                '}';
    }
}
