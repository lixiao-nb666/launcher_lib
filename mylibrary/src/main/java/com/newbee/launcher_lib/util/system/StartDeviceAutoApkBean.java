package com.newbee.launcher_lib.util.system;

import java.io.Serializable;

public class StartDeviceAutoApkBean implements Serializable {
    private String pckStr;
    private String clsStr;

    public String getPckStr() {
        return pckStr;
    }

    public void setPckStr(String pckStr) {
        this.pckStr = pckStr;
    }

    public String getClsStr() {
        return clsStr;
    }

    public void setClsStr(String clsStr) {
        this.clsStr = clsStr;
    }

    @Override
    public String toString() {
        return "StartDeviceAutoApkBean{" +
                "pckStr='" + pckStr + '\'' +
                ", clsStr='" + clsStr + '\'' +
                '}';
    }
}
