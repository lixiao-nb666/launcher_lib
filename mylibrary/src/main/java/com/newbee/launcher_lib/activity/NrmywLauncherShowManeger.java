//package com.newbee.launcher_lib.activity;
//
//import com.newbee.launcher_lib.share.NrmywShare;
//
//
//public class NrmywLauncherShowManeger {
//    private static NrmywLauncherShowManeger showManeger;
//    private NrmywLauncherShowType showType=NrmywLauncherShowType.BREVITY;
//    private final String shareStrKey="NrmywLauncherShowType";
//
//    private NrmywLauncherShowManeger(){
//       try {
//           String shareStr=  NrmywShare.getInstance().getString(shareStrKey,showType.ordinal()+"");
//           int index=Integer.parseInt(shareStr);
//           showType=NrmywLauncherShowType.values()[index];
//       }catch (Exception e){}
//    }
//
//    public static NrmywLauncherShowManeger getInstance(){
//        if(null==showManeger){
//            synchronized (NrmywLauncherShowManeger.class){
//                if(null==showManeger){
//                    showManeger=new NrmywLauncherShowManeger();
//                }
//            }
//        }
//        return showManeger;
//    }
//
//    public void setShowType(int index){
//        try {
//            showType=NrmywLauncherShowType.values()[index];
//            NrmywShare.getInstance().putString(shareStrKey,showType.ordinal()+"");
//        }catch (Exception e){}
//    }
//
//    public NrmywLauncherShowType getShowType(){
//        return showType;
//    }
//
//
//}
