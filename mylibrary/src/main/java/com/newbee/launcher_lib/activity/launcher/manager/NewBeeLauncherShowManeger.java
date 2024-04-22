package com.newbee.launcher_lib.activity.launcher.manager;


import com.newbee.launcher_lib.activity.launcher.type.NewBeeLauncherShowType;
import com.newbee.launcher_lib.share.NrmywShare;


public class NewBeeLauncherShowManeger {
    private static NewBeeLauncherShowManeger showManeger;
    private NewBeeLauncherShowType showType=NewBeeLauncherShowType.BREVITY;
    private final String shareStrKey="NrmywLauncherShowType";

    private NewBeeLauncherShowManeger(){
       try {
           String shareStr=  NrmywShare.getInstance().getString(shareStrKey,showType.ordinal()+"");
           int index=Integer.parseInt(shareStr);
           showType=NewBeeLauncherShowType.values()[index];
       }catch (Exception e){}
    }

    public static NewBeeLauncherShowManeger getInstance(){
        if(null==showManeger){
            synchronized (NewBeeLauncherShowManeger.class){
                if(null==showManeger){
                    showManeger=new NewBeeLauncherShowManeger();
                }
            }
        }
        return showManeger;
    }

    public void setShowType(int index){
        try {
            showType=NewBeeLauncherShowType.values()[index];
            NrmywShare.getInstance().putString(shareStrKey,showType.ordinal()+"");
        }catch (Exception e){}
    }

    public NewBeeLauncherShowType getShowType(){
        return showType;
    }


}
