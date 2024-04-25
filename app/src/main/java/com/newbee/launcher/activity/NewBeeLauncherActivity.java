package com.newbee.launcher.activity;


import android.util.ArrayMap;

import com.newbee.bulid_lib.util.myapp.MyAppUtils;
import com.newbee.launcher.R;
import com.newbee.launcher_lib.activity.launcher.BaseNewBeeLauncherActivity;
import com.newbee.launcher_lib.app.BaseLauncherApp;

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

    @Override
    public Map<String, String> getUsePckChangeNameMap() {
        Map<String,String> usePckChangeNameMap=new ArrayMap<>();
        usePckChangeNameMap.put("cm.aptoidetv.pt",BaseLauncherApp.getRsString(com.newbee.launcher_lib.R.string.myw_app_store));
        usePckChangeNameMap.put("com.opera.browser","Opera Browser");
        usePckChangeNameMap.put("com.xiaobaifile.tv",BaseLauncherApp.getRsString(com.newbee.launcher_lib.R.string.myw_file));
        return usePckChangeNameMap;
    }

    @Override
    public Map<String, String> getUseNameChangeNameMap() {
        return null;
    }

    @Override
    public Map<String, String> getNameReplaceMap() {
        Map<String,String> nameReplaceMap=new ArrayMap<>();//键值是名称中包含的字符串，V值是需要替换成的字符串
        nameReplaceMap.put("TV","");
        nameReplaceMap.put(" TV","");
        nameReplaceMap.put("TV ","");
        nameReplaceMap.put("MyW","aaaa");
        return nameReplaceMap;
    }

    @Override
    public Map<String, Integer> getUsePckChangeIconMap() {
        Map<String,Integer> usePckChangeIconMap=new ArrayMap<>();
        usePckChangeIconMap.put("cm.aptoidetv.pt", com.newbee.launcher_lib.R.drawable.icon_myw_store);
        usePckChangeIconMap.put("com.xiaobaifile.tv", com.newbee.launcher_lib.R.drawable.icon_myw_file);
        return usePckChangeIconMap;
    }
}
