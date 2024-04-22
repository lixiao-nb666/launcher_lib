package com.newbee.launcher_lib.manager.bean;

import java.io.Serializable;

public class FirstStartNeedOpenInfoBean implements Serializable {

    private String pckName;
    private String clsName;
    private boolean alway;

    public String getPckName() {
        return pckName;
    }

    public void setPckName(String pckName) {
        this.pckName = pckName;
    }

    public String getClsName() {
        return clsName;
    }

    public void setClsName(String clsName) {
        this.clsName = clsName;

    }

    public boolean isAlway() {
        return alway;
    }

    public void setAlway(boolean alway) {
        this.alway = alway;
    }

    @Override
    public String toString() {
        return "FirstStartNeedOpenInfoBean{" +
                "pckName='" + pckName + '\'' +
                ", clsName='" + clsName + '\'' +
                ", alway=" + alway +
                '}';
    }
}
