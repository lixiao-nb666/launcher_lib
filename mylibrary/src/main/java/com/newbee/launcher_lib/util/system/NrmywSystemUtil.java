package com.newbee.launcher_lib.util.system;

import android.content.Context;
import android.provider.Settings;

import java.lang.reflect.Method;

public class NrmywSystemUtil {

    public static void setProperty(String key, String value) {
        try {
            Class<?> c = Class.forName("android.os.SystemProperties");
            Method set = c.getMethod("set", String.class, String.class);
            set.invoke(c, key, value );
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Object getProperty(String key) {
        try {
            Class<?> c = Class.forName("android.os.SystemProperties");
            Method set = c.getMethod("get", String.class, String.class);
            return set.invoke(c, key);
        } catch (Exception e) {
            return null;
        }

    }





    public static void setBle(){
        setProperty("persist.sys.glassbluetooth",1+"");
    }






    public static void setShowBatteryPercent(Context context){
        boolean showPercentage=true;
//        Settings.System.putInt(mContext.getContentResolver(), SHOW_BATTERY_PERCENT,
//                showPercentage ? 1 : 0);
//        Settings.System.putInt(context.getContentResolver(),"status_bar_show_battery_percent",
//                showPercentage ? 1 : 0);
        Settings.System.putInt(context.getContentResolver(),"status_bar_show_battery_percent",
                showPercentage ? 1 : 0);
    }

    public static boolean putSystemSetting(Context context, String name, String value) {
        try {
            return Settings.System.putString(context.getContentResolver(), name, value);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    public static String getSystemSetting(Context context, String name) {
        try {
            return Settings.System.getString(context.getContentResolver(), name);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    public static boolean putSystemSettingInt(Context context, String name, int value) {
        try {
            return Settings.System.putInt(context.getContentResolver(), name, value);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    public static int getSystemSettingInt(Context context, String name) {
        try {
            return Settings.System.getInt(context.getContentResolver(), name);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static int getSystemSettingInt(Context context, String name,int def) {
        try {
            return Settings.System.getInt(context.getContentResolver(), name,def);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

}
