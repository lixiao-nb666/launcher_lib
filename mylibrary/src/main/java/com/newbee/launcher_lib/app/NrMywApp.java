//package com.newbee.launcher_lib.app;
//
//
//import android.content.IntentFilter;
//import android.util.Log;
//import com.newbee.bulid_lib.mybase.appliction.BaseApplication;
//import com.newbee.launcher_lib.BuildConfig;
//import com.newbee.launcher_lib.manager.FirstStartNeedOpenManager;
//import com.newbee.launcher_lib.manager.bean.FirstStartNeedOpenInfoBean;
//import com.newbee.launcher_lib.receiver.NrMyWOtherApkCloseReceiver;
//import com.newbee.launcher_lib.util.CheckBuildGuideUtil;
//import com.newbee.system_applist_lib.systemapp.StartOtherApkUtil;
//
//
//public class NrMywApp extends BaseApplication {
//
//    private NrMyWOtherApkCloseReceiver nrMyWOtherApkCloseReceiver=new NrMyWOtherApkCloseReceiver();
//
//    @Override
//    protected void init() {
//        /**
//         * 注册其他软件关闭的广播
//         */
//        IntentFilter otherApkCloseReceiverIntent=new IntentFilter();
//        otherApkCloseReceiverIntent.addAction(NrMyWOtherApkCloseReceiver.keyListenCloseAction);
//        otherApkCloseReceiverIntent.addAction(NrMyWOtherApkCloseReceiver.fileShareCloseAction);
//        getContext().registerReceiver(nrMyWOtherApkCloseReceiver,otherApkCloseReceiverIntent);
//        /**
//         * 启动其他软件需要的服务
//         */
//        NrMywOtherAppUtil.doOtherAppNeed(getContext());
//        if(!CheckBuildGuideUtil.isRun()){
//            String pck="com.newbee.myw_build_guide";
//            boolean kankan=StartOtherApkUtil.getInstance().checkAppIsInstalled(getContext(),pck);
//            Log.i("kankanshenmegui","kankanshenmegui1112233:"+kankan);
//            if(kankan){
//                String cls="com.newbee.myw_build_guide.activity.MainActivity";
//                StartOtherApkUtil.getInstance().doStartApk(getContext(),pck,cls);
//            }
//        }
//        switch (BuildConfig.BUILD_TYPE){
//            case "debug":
//            case "release":
//                break;
//            default:
//                FirstStartNeedOpenInfoBean openInfoBean= FirstStartNeedOpenManager.getInstance().getOpenInfoBean();
//                if(null==openInfoBean){
//                    openInfoBean=new FirstStartNeedOpenInfoBean();
//                    openInfoBean.setAlway(true);
//                    openInfoBean.setPckName("com.inno_cn.smartview");
//                    openInfoBean.setClsName("com.inno_cn.smartview.activity.WelcomeActivity");
//                    FirstStartNeedOpenManager.getInstance().setOpenInfoBean(openInfoBean);
//                }
//                break;
//        }
//
//    }
//
//    @Override
//    protected void needClear(String s) {
//    }
//
//    @Override
//    protected void close() {
//        /**
//         * 取消其他软件关闭的广播
//         */
//        getContext().unregisterReceiver(nrMyWOtherApkCloseReceiver);
//    }
//}
