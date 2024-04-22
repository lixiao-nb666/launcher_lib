package com.newbee.launcher_lib.util;

import android.content.ComponentName;
import android.content.Context;
import android.provider.Settings;

public class RobMoneyUtil {

    public static void openServiceSetting(Context context) {
        String enabledServicesSetting = Settings.Secure.getString(
                context.getContentResolver(),
                Settings.Secure.ENABLED_ACCESSIBILITY_SERVICES);
        String pckName="com.newbee.systemkeylisten";
        String clsName="com.newbee.systemkeylisten.service.RobMoney";
        ComponentName selfComponentName = new ComponentName(
                pckName,
                clsName);
        String flattenToString = selfComponentName.flattenToString();
        //null 表示没有任何服务
        if (enabledServicesSetting == null){
            enabledServicesSetting=flattenToString;
        }else if(!enabledServicesSetting.contains(flattenToString)) {
            enabledServicesSetting = enabledServicesSetting +":"+ flattenToString;
        }
        Settings.Secure.putString(context.getContentResolver(),
                Settings.Secure.ENABLED_ACCESSIBILITY_SERVICES,
                flattenToString);
        Settings.Secure.putInt(context.getContentResolver(),
                Settings.Secure.ACCESSIBILITY_ENABLED, 1);
    }

}
