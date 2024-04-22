package com.newbee.launcher_lib.util;

import android.provider.Settings;

import com.newbee.launcher_lib.app.BaseLauncherApp;


public class CheckBuildGuideUtil {

    public static boolean isRun(){
        int setUp= Settings.Secure.getInt(BaseLauncherApp.getContext().getContentResolver(), "user_setup_complete_nrmyw", 0);

        if(setUp==0){
            return false;
        }else {
            return true;
        }
    }
}
