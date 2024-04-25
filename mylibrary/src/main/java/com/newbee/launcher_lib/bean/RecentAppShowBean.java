package com.newbee.launcher_lib.bean;

import android.text.TextUtils;

import java.io.Serializable;

public class RecentAppShowBean implements Serializable {

    private int taskId;
    private String appName;
    private String pckName;
    private String needStartCls;
    private boolean isRun;

    public int getTaskId() {
        return taskId;
    }

    public void setTaskId(int taskId) {
        this.taskId = taskId;
    }

    public String getAppName() {
        if (TextUtils.isEmpty(appName)) {
            return pckName;
        }
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getPckName() {
        return pckName;
    }

    public void setPckName(String pckName) {
        this.pckName = pckName;
    }

    public String getNeedStartCls() {
        return needStartCls;
    }

    public void setNeedStartCls(String needStartCls) {
        this.needStartCls = needStartCls;
    }

    public boolean isRun() {
        return isRun;
    }

    public void setRun(boolean run) {
        isRun = run;
    }

    @Override
    public String toString() {
        return "RecentAppShowBean{" +
                "taskId=" + taskId +
                ", appName='" + appName + '\'' +
                ", pckName='" + pckName + '\'' +
                ", needStartCls='" + needStartCls + '\'' +
                ", isRun=" + isRun +
                '}';
    }
}
