package com.newbee.launcher_lib.util.system;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.newbee.gson_lib.gson.MyGson;
import com.newbee.system_applist_lib.systemapp.StartOtherApkUtil;


public class AutoStratUtil {

    private static AutoStratUtil autoStratUtil;
    private final  String shareKey="StartDeviceAutoShareKey";

    private AutoStratUtil(){

    }

    public static AutoStratUtil getInstance(){
        if(null==autoStratUtil){
            synchronized (AutoStratUtil.class){
                if(null==autoStratUtil){
                    autoStratUtil=new AutoStratUtil();
                }
            }
        }
        return autoStratUtil;
    }

    public void shareApk(Context context,String pckStr, String clsStr){
        if(TextUtils.isEmpty(pckStr)){
            NrmywSystemUtil.putSystemSetting(context,shareKey,"");

        }else {
            StartDeviceAutoApkBean startDeviceAutoApkBean=new StartDeviceAutoApkBean();
            startDeviceAutoApkBean.setPckStr(pckStr);
            startDeviceAutoApkBean.setClsStr(clsStr);
            NrmywSystemUtil.putSystemSetting(context,shareKey, MyGson.getInstance().toGsonStr(startDeviceAutoApkBean));
        }
    }

    public String getShareAutoApkJsonStr(Context context){
       return NrmywSystemUtil.getSystemSetting(context,shareKey);
    }


    public StartDeviceAutoApkBean getShareAutoApk(Context context){
        String shareStr=NrmywSystemUtil.getSystemSetting(context,shareKey);
        if(TextUtils.isEmpty(shareStr)){
            return null;
        }
        try {
            StartDeviceAutoApkBean startDeviceAutoApkBean=MyGson.getInstance().fromJson(shareStr,StartDeviceAutoApkBean.class);
            return startDeviceAutoApkBean;
        }catch (Exception e){
            return null;
        }
    }

    public String getShareAutoApkPckStr(Context context){
        String shareStr=NrmywSystemUtil.getSystemSetting(context,shareKey);
        if(TextUtils.isEmpty(shareStr)){
            return "";
        }
        try {
            StartDeviceAutoApkBean startDeviceAutoApkBean=MyGson.getInstance().fromJson(shareStr,StartDeviceAutoApkBean.class);
            return startDeviceAutoApkBean.getPckStr();
        }catch (Exception e){
            return "";
        }
    }


    public String getShareAutoApkClsStr(Context context){
        String shareStr=NrmywSystemUtil.getSystemSetting(context,shareKey);
        if(TextUtils.isEmpty(shareStr)){
            return "";
        }
        try {
            StartDeviceAutoApkBean startDeviceAutoApkBean=MyGson.getInstance().fromJson(shareStr,StartDeviceAutoApkBean.class);
            return startDeviceAutoApkBean.getClsStr();
        }catch (Exception e){
            return "";
        }
    }


    private boolean isCheck;
    public void checkToStart(Context context){
        if(isCheck){
            return;
        }
        isCheck=true;
        StartDeviceAutoApkBean startDeviceAutoApkBean=getShareAutoApk(context);
        Log.i("kankanshenmeguiauto","kankanzhidebuauto:"+startDeviceAutoApkBean);
        if(null==startDeviceAutoApkBean||TextUtils.isEmpty(startDeviceAutoApkBean.getPckStr())){
            return;
        }
        if(!StartOtherApkUtil.getInstance().checkAppIsInstalled(context, startDeviceAutoApkBean.getPckStr())){
            //没安装也返回
            Log.i("kankanshenmeguiauto","kankanzhidebuauto:111");
            return;
        }

        if(!TextUtils.isEmpty(startDeviceAutoApkBean.getClsStr())){
            Log.i("kankanshenmeguiauto","kankanzhidebuauto:222");
            StartOtherApkUtil.getInstance().doStartApk(context,startDeviceAutoApkBean.getPckStr(),startDeviceAutoApkBean.getClsStr());
        }else {
            Log.i("kankanshenmeguiauto","kankanzhidebuauto:333");
            StartOtherApkUtil.getInstance().doStartApplicationWithPackageName(context, startDeviceAutoApkBean.getPckStr());
        }

    }
}
