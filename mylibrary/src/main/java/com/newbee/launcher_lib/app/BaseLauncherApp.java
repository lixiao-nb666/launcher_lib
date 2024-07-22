package com.newbee.launcher_lib.app;



import android.text.TextUtils;
import com.newbee.bulid_lib.mybase.appliction.BaseApplication;
import com.newbee.launcher_lib.BuildConfig;
import com.newbee.launcher_lib.config.NowAllAppListConfig;
import com.newbee.launcher_lib.util.CheckBuildGuideUtil;
import com.newbee.launcher_lib.util.image.GetSystemIconUtil;
import com.newbee.launcher_lib.util.system.AutoStratUtil;
import com.newbee.system_applist_lib.systemapp.StartOtherApkUtil;


public abstract class BaseLauncherApp extends BaseApplication {

    public abstract void doOtherAppNeed();
    public abstract void closeNeedDo();

    public abstract String buildGuidePck();
    public abstract String buildGuideActivity();
    public abstract void selectBuildTypeToDo(String buildType);



    @Override
    protected void init() {
        selectBuildTypeToDo(BuildConfig.BUILD_TYPE);
        doOtherAppNeed();
        if(!CheckBuildGuideUtil.isRun()){
            if(!TextUtils.isEmpty(buildGuidePck())&&!TextUtils.isEmpty(buildGuideActivity())&&StartOtherApkUtil.getInstance().checkAppIsInstalled(getContext(),buildGuidePck())){
                StartOtherApkUtil.getInstance().doStartApk(getContext(),buildGuidePck(),buildGuideActivity());
            }else {
                String pck="com.newbee.myw_build_guide";
                if(StartOtherApkUtil.getInstance().checkAppIsInstalled(getContext(),pck)){
                    String cls="com.newbee.myw_build_guide.activity.MainActivity";
                    StartOtherApkUtil.getInstance().doStartApk(getContext(),pck,cls);
                }
            }
        }
        AutoStratUtil.getInstance().checkToStart(getApplicationContext());
    }

    @Override
    protected void needClear(String s) {
    }

    @Override
    protected void close() {
        GetSystemIconUtil.getInstance().close();
        NowAllAppListConfig.getInstance().close();
        closeNeedDo();
    }
}
