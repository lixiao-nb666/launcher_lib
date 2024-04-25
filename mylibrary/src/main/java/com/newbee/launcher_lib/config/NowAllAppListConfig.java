package com.newbee.launcher_lib.config;

import android.util.Log;

import com.newbee.system_applist_lib.systemapp.bean.SystemAppInfoBean;

import java.util.List;

public class NowAllAppListConfig {
    private List<SystemAppInfoBean> apps;

    private static NowAllAppListConfig nowAllAppListConfig;

    private NowAllAppListConfig(){}

    public static NowAllAppListConfig getInstance(){
        if(null==nowAllAppListConfig){
            synchronized (NowAllAppListConfig.class){
                if(null==nowAllAppListConfig){
                    nowAllAppListConfig=new NowAllAppListConfig();
                }
            }
        }
        return nowAllAppListConfig;
    }

    public void close(){
        if(null!=apps){
            apps=null;
        }
    }

    public List<SystemAppInfoBean> getApps() {
        return apps;
    }

    public void setApps(List<SystemAppInfoBean> apps) {
        this.apps = apps;
        Log.i("kankan","kankanshenmeyixhabng:0000");
    }


    public SystemAppInfoBean getNeedAppInfo(String pck){
        if(null==apps||apps.size()==0){
            return null;
        }
        for(SystemAppInfoBean appInfoBean:apps){
            if(appInfoBean.getPakeageName().equals(pck)){
                return appInfoBean;
            }
        }
        return null;
    }



}
