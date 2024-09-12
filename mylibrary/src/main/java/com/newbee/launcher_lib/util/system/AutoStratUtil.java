package com.newbee.launcher_lib.util.system;

import android.content.Context;
import android.os.SystemClock;
import android.text.TextUtils;
import android.util.Log;

import com.newbee.bulid_lib.util.TimeUtil;
import com.newbee.gson_lib.gson.MyGson;
import com.newbee.system_applist_lib.systemapp.StartOtherApkUtil;


public class AutoStratUtil {

    private static AutoStratUtil autoStratUtil;
    private final  String shareKey="StartDeviceAutoShareKey";
    private final  String shareKeyTime="StartDeviceAutoShareKeyTime";
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
        Log.i("kankanzhelishishenme","kankanzhelishishenme11---"+shareStr);
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
        Log.i("kankanzhelishishenme","kankanzhelishishenme1---"+shareStr);
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
        if(!checkNowTimeCanStart(context)){
            return;
        }
        isCheck=true;
        StartDeviceAutoApkBean startDeviceAutoApkBean=getShareAutoApk(context);
        if(null==startDeviceAutoApkBean||TextUtils.isEmpty(startDeviceAutoApkBean.getPckStr())){
            return;
        }
        if(!StartOtherApkUtil.getInstance().checkAppIsInstalled(context, startDeviceAutoApkBean.getPckStr())){
            //没安装也返回
            return;
        }
        if(!TextUtils.isEmpty(startDeviceAutoApkBean.getClsStr())){
            StartOtherApkUtil.getInstance().doStartApk(context,startDeviceAutoApkBean.getPckStr(),startDeviceAutoApkBean.getClsStr());
        }else {
            StartOtherApkUtil.getInstance().doStartApplicationWithPackageName(context, startDeviceAutoApkBean.getPckStr());
        }
    }

    public boolean checkNowTimeCanStart(Context context){
        String nowDeviceUpTimeStr=getNowDeviceUpTime();
        String lastTimeStr=NrmywSystemUtil.getSystemSetting(context,shareKeyTime);
        if(TextUtils.isEmpty(lastTimeStr)){
            NrmywSystemUtil.putSystemSetting(context,shareKeyTime,nowDeviceUpTimeStr);
            return true;
        }
        if(lastTimeStr.equals(nowDeviceUpTimeStr)){
            return false;
        }else {
            NrmywSystemUtil.putSystemSetting(context,shareKeyTime,nowDeviceUpTimeStr);
            return true;
        }
    }



    public String getNowDeviceUpTime(){
        long nowDeviceUpTime= System.currentTimeMillis() - SystemClock.elapsedRealtime();
        return TimeUtil.getDateStr(nowDeviceUpTime);
    }
}
