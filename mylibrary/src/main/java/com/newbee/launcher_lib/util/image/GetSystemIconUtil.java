package com.newbee.launcher_lib.util.image;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.DisplayMetrics;
import android.widget.ImageView;
import android.widget.TextView;

import com.newbee.launcher_lib.R;
import com.newbee.launcher_lib.app.NrMywApp;


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

    public void setAppIconAndName(ImageView showIV,TextView showTV,String appName,String pckStr){
        try {
            if(pckStr.equals("cm.aptoidetv.pt")||appName.toLowerCase().contains("aptoide")){
                showIV.setImageResource(R.drawable.icon_myw_store);
                showTV.setText(NrMywApp.getRsString(R.string.myw_app_store));
            }else if(pckStr.equals("com.xiaobaifile.tv")){
                showIV.setImageResource(R.drawable.icon_myw_file);
                showTV.setText(NrMywApp.getRsString(R.string.myw_file));
            }else if(appName.equals("Opera")){
                showIV.setImageDrawable(getIconFromPackageName(pckStr));
                showTV.setText("Opera Browser");
            }else {
                showIV.setImageDrawable(getIconFromPackageName(pckStr));
                showTV.setText(changeName(appName));
            }
        }catch (Exception e){}
    }

    private String changeName(String name){
        if(name.contains(" TV")){
            return name.replace(" TV","");
        }else if(name.contains("TV ")){
            return name.replace("TV ","");
        }else if(name.contains("TV")){
            return name.replace("TV","");
        }
        return name;
    }


    private Drawable getIconFromPackageName(String packageName) {
        PackageManager pm = NrMywApp.getContext().getPackageManager();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH_MR1) {
            try {
                PackageInfo pi = pm.getPackageInfo(packageName, 0);
                Context otherAppCtx = NrMywApp.getContext().createPackageContext(packageName, Context.CONTEXT_IGNORE_SECURITY);
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
