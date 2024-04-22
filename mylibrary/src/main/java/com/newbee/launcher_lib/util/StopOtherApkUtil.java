package com.newbee.launcher_lib.util;

import android.app.ActivityManager;

import java.lang.reflect.Method;

public class StopOtherApkUtil {

    public static void needStop(ActivityManager am,String pck ){
        try {
            Method forceStopPackage = am.getClass().getDeclaredMethod("forceStopPackage", String.class);
            forceStopPackage.setAccessible(true);
            forceStopPackage.invoke(am, pck);
        }catch (Exception e){}

    }
}
