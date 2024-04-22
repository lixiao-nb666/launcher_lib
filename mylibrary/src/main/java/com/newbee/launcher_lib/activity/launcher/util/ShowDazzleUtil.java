package com.newbee.launcher_lib.activity.launcher.util;

import android.content.Context;

import android.view.View;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.newbee.launcher_lib.adapter.NrmywAdapter;
import com.newbee.launcher_lib.share.NrmywShare;
import com.newbee.launcher_lib.util.KeyCodesEventType;
import com.newbee.launcher_lib.util.MyWStartUtil;
import com.newbee.launcher_lib.view.SpeedConfig;
import com.newbee.system_applist_lib.systemapp.bean.ResultSystemAppInfoBean;
import com.newbee.system_applist_lib.systemapp.bean.SystemAppInfoBean;


public class ShowDazzleUtil extends BaseShowUtil{
    private RecyclerView appLV;
    private RecyclerView.OnScrollListener onScrollListener=new RecyclerView.OnScrollListener() {
        @Override
        public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
        }
        @Override
        public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            refreshIndex();
        }
    };

    private NrmywAdapter adapter;
    private NrmywAdapter.ItemClick itemChick = new NrmywAdapter.ItemClick() {
        @Override
        public void nowSelect(SystemAppInfoBean appInfoBean) {
            if (null != appInfoBean) {
                MyWStartUtil.toOtherApk(context,appInfoBean.getPakeageName(),appInfoBean.getIndexActivityClass());
            }
        }
    };


    private Context context;
    public ShowDazzleUtil(Context context, RecyclerView rv){
        this.context=context;
        this.appLV=rv;
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        appLV.setLayoutManager(linearLayoutManager);
        PagerSnapHelper pagerSnapHelper = new PagerSnapHelper();
        pagerSnapHelper.attachToRecyclerView(appLV);
        appLV.addOnScrollListener(onScrollListener);
    }


    @Override
    public void initView(int w, int h) {
        appLV.setVisibility(View.VISIBLE);
        adapter = new NrmywAdapter(context, itemChick,w,h,false);
        appLV.setAdapter(adapter);
        /**
         * 此处需要设置 paddingStart和paddingEnd,即旁边显示的大小
         * android:paddingStart="160dp"
         * android:paddingEnd="160dp"
         */
        int needShowPx=w/9*2;
        appLV.setPaddingRelative(needShowPx,0,needShowPx,0);
    }

    @Override
    public void initData(ResultSystemAppInfoBean initList){
        if(adapter.getItemCount()==0){
            adapter.setData(initList.getAppList());
            nowCanDoEvent(KeyCodesEventType.BACK.ordinal());
        }else {
            adapter.setData(initList.getAppList());
            refreshIndex();
        }
    }

    @Override
    public void nowCanDoEvent(int eventTypeInt) {
        if(null==adapter||adapter.getItemCount()==0){
            return;
        }

        KeyCodesEventType eventType = KeyCodesEventType.values()[eventTypeInt];
        switch (eventType) {
            case NONE:
                break;
            case LEFT:
                index--;
                if (index < 0) {
                    index = 0;
                }
                appLV.smoothScrollToPosition(index);
                break;
            case RIGHT:
                index++;
                if (index >= adapter.getItemCount()) {
                    index = adapter.getItemCount() - 1;
                }
                appLV.smoothScrollToPosition(index);
                break;
            case QUE:
                adapter.setItemSelectData(index);
                break;
            case BACK:
//                index= NrmywShare.getInstance().getShowIndex();
//                Log.i("kankandaodishiji","kankandaodishiji:"+index);
//                if (index < 0) {
//                    index = 0;
//                }else if(index >= adapter.getItemCount()){
//                    index = adapter.getItemCount() - 1;
//                }
//                appLV.scrollToPosition(index);
                break;

        }
    }



    private int index=-1;
    public void  refreshIndex(){
        try {
            int childCount = appLV.getChildCount();
            for (int i = 0; i < childCount; i++) {
                View child = appLV.getChildAt(i);
                int left = child.getLeft();
                int paddingStart = appLV.getPaddingStart();
                // 遍历recyclerView子项，以中间项左侧偏移量为基准进行缩放
                float bl = Math.min(1, Math.abs(left - paddingStart) * 1f / child.getWidth());
                float scale = SpeedConfig.MaxScale - bl * (SpeedConfig.MaxScale- SpeedConfig.MinScale);
                child.setScaleX(scale);
                child.setScaleY(scale);
                if(bl==0){
                    index= appLV.getChildAdapterPosition(child);
                    shareIndex();
                }
            }
        }catch (Exception e){
        }
    }

    private void shareIndex(){
        NrmywShare.getInstance().putShowIndex(index);
    }






}
