package com.newbee.launcher_lib.bean;

import android.text.TextUtils;

import com.newbee.system_applist_lib.systemapp.bean.ResultSystemAppInfoBean;
import com.newbee.system_applist_lib.systemapp.bean.SystemAppInfoBean;

import java.io.Serializable;

public class BrevityIconBean implements Serializable {
    private BrevityIconType iconType;
    private SystemAppInfoBean systemAppInfoBean;
    private ResultSystemAppInfoBean resultSystemAppInfoBean;
    private String groupIconName;


    public BrevityIconType getIconType() {
        if(null==iconType){
            iconType=BrevityIconType.none;
        }
        return iconType;
    }

    public void setIconType(BrevityIconType iconType) {
        this.iconType = iconType;
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

    public String getGroupIconName() {
        return groupIconName;
    }

    public void setGroupIconName(String groupIconName) {
        this.groupIconName = groupIconName;
    }

    @Override
    public String toString() {
        return "BrevityIconBean{" +
                "iconType=" + iconType +
                ", systemAppInfoBean=" + systemAppInfoBean +
                ", resultSystemAppInfoBean=" + resultSystemAppInfoBean +
                ", groupIconName='" + groupIconName + '\'' +
                '}';
    }
}
