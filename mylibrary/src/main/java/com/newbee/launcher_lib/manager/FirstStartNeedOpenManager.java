package com.newbee.launcher_lib.manager;

import android.content.Context;
import android.text.TextUtils;

import com.newbee.bulid_lib.mybase.share.MyShare;
import com.newbee.gson_lib.gson.MyGson;
import com.newbee.launcher_lib.manager.bean.FirstStartNeedOpenInfoBean;
import com.newbee.system_applist_lib.systemapp.StartOtherApkUtil;


public class FirstStartNeedOpenManager {

    private static FirstStartNeedOpenManager manager;
    private final String key="FirstStartNeedOpenManager";
    private FirstStartNeedOpenInfoBean openInfoBean;


    private FirstStartNeedOpenManager(){
        String ss= MyShare.getInstance().getString(key);
        if(!TextUtils.isEmpty(ss)){
           openInfoBean= MyGson.getInstance().fromJson(ss,FirstStartNeedOpenInfoBean.class);
        }
    }

    public static FirstStartNeedOpenManager getInstance(){
        if(null==manager){
            synchronized (FirstStartNeedOpenManager.class){
                if(null==manager){
                    manager=new FirstStartNeedOpenManager();
                }
            }
        }
        return manager;
    }

    public FirstStartNeedOpenInfoBean getOpenInfoBean() {
        return openInfoBean;
    }

    public void setOpenInfoBean(FirstStartNeedOpenInfoBean openInfoBean) {
        this.openInfoBean = openInfoBean;
        if(this.openInfoBean==null){
            MyShare.getInstance().putString(key,"");
        }else {
            MyShare.getInstance().putString(key,MyGson.getInstance().toGsonStr(this.openInfoBean));
        }
    }

    public FirstStartNeedOpenInfoBean useOpenInfo(Context context){
        if(null!=openInfoBean){
            if( StartOtherApkUtil.getInstance().checkAppIsInstalled(context,openInfoBean.getPckName())){
                StartOtherApkUtil.getInstance().doStartApk(context,openInfoBean.getPckName(),openInfoBean.getClsName());
            }
            if(!openInfoBean.isAlway()){
                openInfoBean=null;
                MyShare.getInstance().putString(key,"");
            }



        }
        return openInfoBean;
    }
}
