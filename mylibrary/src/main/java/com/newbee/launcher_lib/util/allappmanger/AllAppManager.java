package com.newbee.launcher_lib.util.allappmanger;

import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.util.Log;


import com.newbee.launcher_lib.app.NrMywApp;
import com.newbee.launcher_lib.bean.RecentAppShowBean;
import com.newbee.launcher_lib.bean.RecentServiceShowBean;
import com.newbee.launcher_lib.util.StopOtherApkUtil;

import java.util.ArrayList;
import java.util.List;

public class AllAppManager {

    private static AllAppManager allAppManager;

    private AllAppManager() {
    }

    public static AllAppManager getInstance() {
        if (null == allAppManager) {
            synchronized (AllAppManager.class) {
                if (null == allAppManager) {
                    allAppManager = new AllAppManager();
                }
            }
        }
        return allAppManager;
    }


    private ActivityManager activityManager;

    private ActivityManager getActivityManager() {
        if (null == activityManager) {
            activityManager = (ActivityManager) NrMywApp.getContext().getSystemService(Context.ACTIVITY_SERVICE);
        }
        return activityManager;
    }

    public List<RecentAppShowBean> getRecentAppShowList(List<String> canNotCleanPckList) {
        try {
            List<RecentAppShowBean> appInfoBeanList = new ArrayList<>();
            PackageManager pm = NrMywApp.getContext().getPackageManager();
            List<ActivityManager.RunningTaskInfo> runActivityList = getActivityManager().getRunningTasks(1000);
            for (int i = 0; i < runActivityList.size(); i++) {
                ActivityManager.RunningTaskInfo runActivity = runActivityList.get(i);
                ComponentName componentName = runActivity.baseActivity;
                if (null==canNotCleanPckList||!canNotCleanPckList.contains(componentName.getPackageName())) {
                    RecentAppShowBean recentAppShowBean = new RecentAppShowBean();
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                        recentAppShowBean.setTaskId(runActivity.taskId);
                        recentAppShowBean.setRun(runActivity.isRunning);
                    }
                    recentAppShowBean.setPckName(componentName.getPackageName());
                    recentAppShowBean.setNeedStartCls(componentName.getClassName());
                    String appName = (String) pm.getApplicationLabel(pm.getApplicationInfo(componentName.getPackageName(), 0));
                    recentAppShowBean.setAppName(appName);
                    appInfoBeanList.add(recentAppShowBean);
                }
            }
            return appInfoBeanList;

        } catch (Exception e) {
            return null;
        }
    }


    public void stopOtherApk(String pcg) {
        StopOtherApkUtil.needStop(getActivityManager(), pcg);
    }


    public void stopOtherService(String pcg) {
        getActivityManager().killBackgroundProcesses(pcg);
    }


    private long getAvailMemory() {
// 获取android当前可用内存大小

        ActivityManager.MemoryInfo mi = new ActivityManager.MemoryInfo();
        getActivityManager().getMemoryInfo(mi);
//mi.availMem; 当前系统的可用内存
//return Formatter.formatFileSize(context, mi.availMem);// 将获取的内存大小规格化
//        Log.d(TAG, "可用内存---->>>" + mi.availMem / (1024 * 1024));
        return mi.availMem / (1024 * 1024);
    }


    public List<RecentServiceShowBean> getRecentServiceList(List<String> canNotCleanPckList) {
        ActivityManager am = getActivityManager();
        List<ActivityManager.RunningAppProcessInfo> infoList = am.getRunningAppProcesses();
        if (null == infoList || infoList.size() == 0) {
            return null;
        }
        PackageManager pm = NrMywApp.getContext().getPackageManager();
        List<RecentServiceShowBean> recentServiceShowBeanList = new ArrayList<>();
        for (int i = 0; i < infoList.size(); ++i) {
            ActivityManager.RunningAppProcessInfo appProcessInfo = infoList.get(i);
//                Log.d("shenmegui", "999lixiao:process name : " + appProcessInfo.processName);
//importance 该进程的重要程度 分为几个级别，数值越低就越重要。
//                Log.d("shenmegui", "999lixiao:importance : " + appProcessInfo.importance);
// 一般数值大于RunningAppProcessInfo.IMPORTANCE_SERVICE的进程都长时间没用或者空进程了
// 一般数值大于RunningAppProcessInfo.IMPORTANCE_VISIBLE的进程都是非可见进程，也就是在后台运行着
            if (appProcessInfo.importance > ActivityManager.RunningAppProcessInfo.IMPORTANCE_VISIBLE) {
                String[] pkgList = appProcessInfo.pkgList;
                for (int j = 0; j < pkgList.length; ++j) {//pkgList 得到该进程下运行的包名
                    try {
                        String pckName = pkgList[j];
                        if(null==canNotCleanPckList||!canNotCleanPckList.contains(pckName)){
                            RecentServiceShowBean recentServiceShowBean = new RecentServiceShowBean();
                            recentServiceShowBean.setProcessName(appProcessInfo.processName);
                            recentServiceShowBean.setImportance(appProcessInfo.importance);
                            recentServiceShowBean.setPid(appProcessInfo.pid);
                            String appName = (String) pm.getApplicationLabel(pm.getApplicationInfo(pkgList[j], 0));
                            recentServiceShowBean.setAppName(appName);
                            recentServiceShowBean.setPckName(pckName);
                            recentServiceShowBeanList.add(recentServiceShowBean);
                        }
                    } catch (Exception e) {
                        Log.d("shenmegui", "999lixiao:ex : " + e.toString());
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
            }
        }
        return recentServiceShowBeanList;
    }
}
