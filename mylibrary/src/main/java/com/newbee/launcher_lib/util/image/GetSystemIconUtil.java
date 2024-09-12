package com.newbee.launcher_lib.util.image;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.ArrayMap;
import android.util.DisplayMetrics;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.newbee.launcher_lib.R;
import com.newbee.launcher_lib.app.BaseLauncherApp;
import com.newbee.launcher_lib.config.NowAllAppListConfig;
import com.newbee.system_applist_lib.systemapp.bean.SystemAppInfoBean;

import java.util.Map;


public class GetSystemIconUtil {
    private static GetSystemIconUtil  getSystemIconUtil;
    private GetSystemIconUtil(){}

    public static GetSystemIconUtil getInstance(){
        if(null==getSystemIconUtil){
            synchronized (GetSystemIconUtil.class){
                if(null==getSystemIconUtil){
                    getSystemIconUtil=new GetSystemIconUtil();
                }
            }
        }
        return getSystemIconUtil;
    }

    public void close(){

    }


    public void setAppIconAndNameByAppList(ImageView showIV, TextView showTV, String appName,String pck){
//        try {
            SystemAppInfoBean appInfoBean= NowAllAppListConfig.getInstance().getNeedAppInfo(pck);
            if(null==appInfoBean){
                showTV.setText(appName);
                showIV.setImageDrawable(getIconFromPackageName(pck));
            }else {
                setAppIconAndName(showIV,showTV,appInfoBean);
            }

    }


   private void setAppIconAndName(ImageView showIV, TextView showTV, SystemAppInfoBean appInfo){
        try {
            showTV.setText(appInfo.getName());
            if(appInfo.getIconRs()==0||appInfo.getIconRs()==-1){
                showIV.setImageDrawable(getIconFromPackageName(appInfo.getPakeageName()));
            }else {
                showIV.setImageResource(appInfo.getIconRs());
            }
//            if(pckStr.equals("cm.aptoidetv.pt")||appName.toLowerCase().contains("aptoide")){
//                showIV.setImageResource(R.drawable.icon_myw_store);
//                showTV.setText(BaseLauncherApp.getRsString(R.string.myw_app_store));
//            }else if(pckStr.equals("com.xiaobaifile.tv")){
//
//                showTV.setText(BaseLauncherApp.getRsString(R.string.myw_file));
//            }else if(appName.equals("Opera")){
//
//                showTV.setText("Opera Browser");
//            }else {
//                showIV.setImageDrawable(getIconFromPackageName(pckStr));
//            }
        }catch (Exception e){}
    }


    public void setAppIcon(ImageView showIV, SystemAppInfoBean appInfo){
        try {

            if(appInfo.getIconRs()==0||appInfo.getIconRs()==-1){
                showIV.setImageDrawable(getIconFromPackageName(appInfo.getPakeageName()));
            }else {
                showIV.setImageResource(appInfo.getIconRs());
            }
//            if(pckStr.equals("cm.aptoidetv.pt")||appName.toLowerCase().contains("aptoide")){
//                showIV.setImageResource(R.drawable.icon_myw_store);
//                showTV.setText(BaseLauncherApp.getRsString(R.string.myw_app_store));
//            }else if(pckStr.equals("com.xiaobaifile.tv")){
//
//                showTV.setText(BaseLauncherApp.getRsString(R.string.myw_file));
//            }else if(appName.equals("Opera")){
//
//                showTV.setText("Opera Browser");
//            }else {
//                showIV.setImageDrawable(getIconFromPackageName(pckStr));
//            }
        }catch (Exception e){}
    }

//    private String changeName(String name){
//        if(name.contains(" TV")){
//            return name.replace(" TV","");
//        }else if(name.contains("TV ")){
//            return name.replace("TV ","");
//        }else if(name.contains("TV")){
//            return name.replace("TV","");
//        }
//        return name;
//    }


    private Drawable getIconFromPackageName(String packageName) {
        PackageManager pm =BaseLauncherApp.getContext().getPackageManager();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH_MR1) {
            try {
                PackageInfo pi = pm.getPackageInfo(packageName, 0);
                Context otherAppCtx = BaseLauncherApp.getContext().createPackageContext(packageName, Context.CONTEXT_IGNORE_SECURITY);
                int displayMetrics[] = {DisplayMetrics.DENSITY_XXXHIGH, DisplayMetrics.DENSITY_XXHIGH, DisplayMetrics.DENSITY_XHIGH, DisplayMetrics.DENSITY_HIGH, DisplayMetrics.DENSITY_TV};
                for (int displayMetric : displayMetrics) {
                    try {
                        Drawable d = otherAppCtx.getResources().getDrawableForDensity(pi.applicationInfo.icon, displayMetric);
                        if (d != null) {
                            return d;
                        }
                    } catch (Resources.NotFoundException e) {
                        continue;
                    }
                }
            } catch (Exception e) {
                // Handle Error here
            }
        }
        ApplicationInfo appInfo = null;
        try {
            appInfo = pm.getApplicationInfo(packageName, 0);
        } catch (PackageManager.NameNotFoundException e) {
            return null;
        }
        return appInfo.loadIcon(pm);
    }
}
