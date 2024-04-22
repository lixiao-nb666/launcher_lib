package com.newbee.launcher.activity;

import android.os.Build;

import androidx.annotation.RequiresApi;

import com.newbee.bulid_lib.util.myapp.MyAppUtils;
import com.newbee.launcher_lib.activity.applist.BaseRecentAppListActivity;


import java.util.ArrayList;
import java.util.List;

@RequiresApi(api = Build.VERSION_CODES.M)
public class NewBeeRecentAppListActivity extends BaseRecentAppListActivity {
    @Override
    public List<String> getCanNotCleanPckList() {

        List<String>canNotCleanPckList=new ArrayList<>();
        canNotCleanPckList.add("com.ecloud.emedia");
        canNotCleanPckList.add("com.ecloud.eairplay");
        canNotCleanPckList.add("com.ecloud.eshare.server");
        canNotCleanPckList.add("com.eshare.miracast");
        canNotCleanPckList.add("com.newbee.andserver");
        canNotCleanPckList.add(MyAppUtils.getPackageName());
        return canNotCleanPckList;
    }
}
