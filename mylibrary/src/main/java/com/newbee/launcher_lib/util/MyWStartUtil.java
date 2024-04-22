package com.newbee.launcher_lib.util;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.newbee.system_applist_lib.systemapp.StartOtherApkUtil;

public class MyWStartUtil {


    public static void toOtherApk(Context context,String pck,String cls){
        if(!StartOtherApkUtil.getInstance().checkAppIsInstalled(context,pck)){
            return;
        }
        if(pck.equals("com.xiaobaifile.tv")){
           cls="com.xiaobaifile.tv.view.MainActivity";
        }
        StartOtherApkUtil.getInstance().doStartApk(context,pck,cls);
    }
}
