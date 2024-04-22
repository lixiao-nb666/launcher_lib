package com.newbee.launcher.activity;


import com.newbee.bulid_lib.util.myapp.MyAppUtils;
import com.newbee.launcher_lib.activity.launcher.BaseNewBeeLauncherActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NewBeeLauncherActivity extends BaseNewBeeLauncherActivity {
    @Override
    public List<String> getNeedHidePckList() {
        List<String>needHidePack=new ArrayList<>();
        needHidePack.add(MyAppUtils.getPackageName());
        needHidePack.add("com.android.stk");
        needHidePack.add("com.android.documentsui");
        needHidePack.add("com.android.deskclock");
        needHidePack.add("com.android.dialer");
        needHidePack.add("com.hulu.plus");
//                needHidePack.add("com.android.settings");
        needHidePack.add("com.android.gallery3d");
        needHidePack.add("com.mediatek.gnss.nonframeworklbs");
        needHidePack.add("com.android.quicksearchbox");
        //投屏必带的包
        needHidePack.add("com.ecloud.eairplay");
        needHidePack.add("com.eshare.miracast");
        needHidePack.add("com.ecloud.eshare.server");
        return needHidePack;
    }

    @Override
    public Map<String, Integer> getSortMap() {
        Map<String,Integer> sortMap=new HashMap<>();
        sortMap.put("com.mediatek.camera",0);
        sortMap.put("com.newbee.photo_album",1);
        sortMap.put("com.nrmyw.photo_album",1);
        sortMap.put("com.newbee.yd_translation",2);
        sortMap.put("com.nrmyw.yd_translation",2);
        sortMap.put("cm.aptoidetv.pt",3);
        sortMap.put("com.google.android.youtube.tv",4);
        sortMap.put("com.instagram.android",5);
        sortMap.put("com.amazon.avod.thirdpartyclient",6);
        sortMap.put("com.tiktok.tv",7);
        sortMap.put("com.xiaobaifile.tv",8);
        sortMap.put("com.nrmyw.share_screen",9);
        sortMap.put("com.android.settings",10);
        return sortMap;
    }

    @Override
    public Map<String, Integer> getSortFuzzyNameMap() {
        Map<String,Integer> sortFuzzyNameMap=new HashMap<>();
        sortFuzzyNameMap.put("nrmyw",20);
        sortFuzzyNameMap.put("MyW",20);
        return sortFuzzyNameMap;
    }
}
