//package com.newbee.launcher_lib.activity.applist;
//
//import java.util.List;
//
//import android.app.Activity;
//import android.app.ActivityManager;
//import android.app.ActivityManager.MemoryInfo;
//import android.app.ActivityManager.RunningAppProcessInfo;
//import android.content.Context;
//import android.content.pm.PackageManager;
//import android.content.pm.PackageManager.NameNotFoundException;
//import android.os.Bundle;
//import android.util.Log;
//
//import android.view.View;
//import android.widget.Toast;
//
//import com.newbee.launcher_lib.R;
//
//
//public class CleanProcessActivity extends Activity {
//    private static final String TAG = "Clean";
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_app_list1);
//        findViewById(R.id.bt).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                clean(v);
//            }
//        });
//    }
//
//    public void clean(View v) {
////To change body of implemented methods use File | Settings | File Templates.
//        ActivityManager am = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
//        List<RunningAppProcessInfo> infoList = am.getRunningAppProcesses();
//        List<ActivityManager.RunningServiceInfo> serviceInfos = am.getRunningServices(100);
//        long beforeMem = getAvailMemory(this);
//        Log.d(TAG, "-----------before memory info : " + beforeMem);
//        int count = 0;
//        PackageManager pm = getPackageManager();
//        if (infoList != null) {
//            for (int i = 0; i < infoList.size(); ++i) {
//                RunningAppProcessInfo appProcessInfo = infoList.get(i);
//                Log.d(TAG, "999lixiao:process name : " + appProcessInfo.processName);
////importance 该进程的重要程度 分为几个级别，数值越低就越重要。
//                Log.d(TAG, "999lixiao:importance : " + appProcessInfo.importance);
//// 一般数值大于RunningAppProcessInfo.IMPORTANCE_SERVICE的进程都长时间没用或者空进程了
//// 一般数值大于RunningAppProcessInfo.IMPORTANCE_VISIBLE的进程都是非可见进程，也就是在后台运行着
//                if (appProcessInfo.importance > RunningAppProcessInfo.IMPORTANCE_VISIBLE) {
//                    String[] pkgList = appProcessInfo.pkgList;
//                    Log.d(TAG, "999lixiao:1111 pkgList Size:"+pkgList.length );
//                    for (int j = 0; j < pkgList.length; ++j) {//pkgList 得到该进程下运行的包名
//                        String appName = null;
//                        try {
//                            appName = (String) pm.getApplicationLabel(pm.getApplicationInfo(pkgList[j], 0));
//                        } catch (NameNotFoundException e) {
//// TODO Auto-generated catch block
//                            e.printStackTrace();
//                        }
//                        if(!RecentAppListConfig.getInstance().canNotCleanPckList.contains(pkgList[j])){
//                            Log.d(TAG, "999lixiao:1111  It will be killed, package name : " + pkgList[j] + " -- " + appName);
//                            am.killBackgroundProcesses(pkgList[j]);
//                            count++;
//                        }
//
//                    }
//                }
//            }
//        }
//        long afterMem = getAvailMemory(this);
//        Log.d(TAG, "----------- after memory info : " + afterMem);
//        Toast.makeText(this, "clear " + count + " process, " + (afterMem - beforeMem) + "M", Toast.LENGTH_LONG).show();
//    }
//
//    private long getAvailMemory(CleanProcessActivity cleanProcessActivity) {
//// 获取android当前可用内存大小
//        ActivityManager am = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
//        MemoryInfo mi = new MemoryInfo();
//        am.getMemoryInfo(mi);
////mi.availMem; 当前系统的可用内存
////return Formatter.formatFileSize(context, mi.availMem);// 将获取的内存大小规格化
//        Log.d(TAG, "可用内存---->>>" + mi.availMem / (1024 * 1024));
//        return mi.availMem / (1024 * 1024);
//    }
//
//}