package com.newbee.launcher_lib.activity.launcher.util;

import android.content.Context;
import android.widget.LinearLayout;

import androidx.recyclerview.widget.RecyclerView;

import com.newbee.system_applist_lib.systemapp.bean.ResultSystemAppInfoBean;

public abstract class BaseShowUtil {




    public abstract void initView(int w,int h);


    public abstract void initData(ResultSystemAppInfoBean initList);

    public abstract void nowCanDoEvent(int eventTypeInt);
}
