package com.newbee.launcher_lib.app;

import android.content.Context;

import com.newbee.bulid_lib.util.StartServiceUtil;
import com.newbee.launcher_lib.util.RobMoneyUtil;
import com.newbee.launcher_lib.util.camera.CameraUtil;


public class NrMywOtherAppUtil {


    public static void doOtherAppNeed(Context context){
        /**
         * 設置系統相機支持遙控器
         */
        CameraUtil.init(context);
        /**
         * 启动文件管理的服务
         */
        startFileShareService(context);
        /**
         * 打开按键监听
         */
        robMoneyOpenServiceSetting(context);
    }

    //**自定义按键监听
    public static void robMoneyOpenServiceSetting(Context context){
        RobMoneyUtil.openServiceSetting(context);
    }

    public static void startFileShareService(Context context){
        StartServiceUtil.startOtherService(context,"com.newbee.andserver","com.newbee.andserver.start");
    }





}
