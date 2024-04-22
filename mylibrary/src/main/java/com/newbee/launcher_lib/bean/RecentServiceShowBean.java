package com.newbee.launcher_lib.bean;

import java.io.Serializable;

public class RecentServiceShowBean implements Serializable {

    private String appName;
    private String processName;
    private String pckName;
    private int importance;
    private int pid;
    private boolean canClear;

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getProcessName() {
        return processName;
    }

    public void setProcessName(String processName) {
        this.processName = processName;
    }

    public String getPckName() {
        return pckName;
    }

    public void setPckName(String pckName) {
        this.pckName = pckName;
    }

    public int getImportance() {
        return importance;
    }

    public void setImportance(int importance) {
        this.importance = importance;
    }

    public boolean isCanClear() {
        return canClear;
    }

    public void setCanClear(boolean canClear) {
        this.canClear = canClear;
    }


    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }

    @Override
    public String toString() {
        return "RecentServiceShowBean{" +
                "appName='" + appName + '\'' +
                ", processName='" + processName + '\'' +
                ", pckName='" + pckName + '\'' +
                ", importance=" + importance +
                ", pid=" + pid +
                ", canClear=" + canClear +
                '}';
    }
}
