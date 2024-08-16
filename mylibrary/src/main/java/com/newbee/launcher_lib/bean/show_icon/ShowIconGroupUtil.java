package com.newbee.launcher_lib.bean.show_icon;


import android.content.Context;

import com.newbee.launcher_lib.app.BaseLauncherApp;
import com.newbee.launcher_lib.util.system.NrmywSystemUtil;


public class ShowIconGroupUtil {

    private static ShowIconGroupUtil showIconGroupUtil;
    private final String pckKey="ShowIconGroup_pck_";
    private final String indexKey="ShowIconGroup_index_";

    private ShowIconGroupUtil(){
    }

    public static ShowIconGroupUtil getInstance(){
        if(null==showIconGroupUtil){
            synchronized (ShowIconGroupUtil.class){
                if(null==showIconGroupUtil){
                    showIconGroupUtil=new ShowIconGroupUtil();
                }
            }
        }
        return showIconGroupUtil;
    }

    public void sharePck(String pck, String groupName){
        NrmywSystemUtil.putSystemSetting(BaseLauncherApp.getContext(),pckKey+pck,groupName);
    }

    public String getGroupName(String pck){
       return NrmywSystemUtil.getSystemSetting(BaseLauncherApp.getContext(),pckKey+pck);

    }



}
