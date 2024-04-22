package com.newbee.launcher_lib.fragment;

import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;

import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;

import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.newbee.bulid_lib.mybase.fragment.BaseFragmen_v4;

import com.newbee.launcher_lib.R;
import com.newbee.launcher_lib.adapter.RecentAppListAdapter;
import com.newbee.launcher_lib.bean.RecentAppShowBean;
import com.newbee.launcher_lib.util.KeyCodesEventType;
import com.newbee.launcher_lib.util.allappmanger.AllAppManager;
import com.newbee.system_applist_lib.systemapp.StartOtherApkUtil;

import java.util.List;


public class RecentActivityShowFragment extends BaseFragmen_v4 implements BaseRecentImp{
    private final String tag=getClass().getName()+">>>>";
    private RecyclerView rv;
    private Button clearAllBT;
    private LinearLayout clearAllLL;
    private int nowIndex=-1;
    private LinearLayoutManager linearLayoutManager;
    private RecentAppListAdapter recentAppListAdapter;
    private RecentAppListAdapter.ItemClick itemClick=new RecentAppListAdapter.ItemClick() {
        @Override
        public void nowSelect(RecentAppShowBean recentAppShowBean) {
            if(null==recentAppShowBean||
                    TextUtils.isEmpty(recentAppShowBean.getPckName())||
                    TextUtils.isEmpty(recentAppShowBean.getNeedStartCls())||
                    !StartOtherApkUtil.getInstance().checkAppIsInstalled(getContext(),recentAppShowBean.getPckName())){
                return;
            }
            StartOtherApkUtil.getInstance().doStartApk(getContext(),recentAppShowBean.getPckName(),recentAppShowBean.getNeedStartCls());
            getActivity().finish();
        }


        @Override
        public void needClear(RecentAppShowBean recentAppShowBean, LinearLayout selectView) {
            if(null!=recentAppShowBean&&!TextUtils.isEmpty(recentAppShowBean.getPckName())&&null!=selectView&&null!=stopAnimation){
                AllAppManager.getInstance().stopOtherApk(recentAppShowBean.getPckName());
                selectView.startAnimation(stopAnimation);
            }
        }
    };

    private void doClearAll(){
        List<RecentAppShowBean> list=recentAppListAdapter.getList();
        if(null==list||list.size()==0){
            return;
        }
        recentAppListAdapter.clearShowIndex();
        for(RecentAppShowBean bean:list){
            if(null!=bean&&!TextUtils.isEmpty(bean.getPckName())){
                AllAppManager.getInstance().stopOtherApk(bean.getPckName());
            }
        }
        rv.startAnimation(stopAnimation);
    }


    private Handler viewHandler=new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            ViewType viewType=ViewType.values()[msg.what];
            switch (viewType){
                case SET_DATA:
                    recentAppListAdapter.setData((List<RecentAppShowBean>) msg.obj);
                    if(recentAppListAdapter.getItemCount()==0){
                        clearAllLL.setVisibility(View.GONE);
                    }else {
                        clearAllLL.setVisibility(View.VISIBLE);
                    }
                    break;
            }
        }
    };





    private Runnable getDataRunnable=new Runnable() {
        @Override
        public void run() {
                Message msg=new Message();
                msg.what=ViewType.SET_DATA.ordinal();
                msg.obj=AllAppManager.getInstance().getRecentAppShowList(canNotCleanPckList);
                viewHandler.sendMessage(msg);

        }
    };
    Animation stopAnimation;
    Animation.AnimationListener animationListener=new Animation.AnimationListener() {
        @Override
        public void onAnimationStart(Animation animation) {

        }

        @Override
        public void onAnimationEnd(Animation animation) {
            getData();
        }

        @Override
        public void onAnimationRepeat(Animation animation) {

        }
    };


    private List<String> canNotCleanPckList;
    public RecentActivityShowFragment(List<String> canNotCleanPckList){
        this.canNotCleanPckList=canNotCleanPckList;
    }

    @Override
    protected int initViewLayout() {
        return R.layout.fragment_recent_activity;
    }

    @Override
    protected void initView() {
        rv=view.findViewById(R.id.rv_activity);
        clearAllBT=view.findViewById(R.id.bt_clear_all);
        clearAllLL=view.findViewById(R.id.ll_clear_all);
        clearAllBT.setFocusable(false);
        View.OnClickListener clearAllOnClickListener=new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doClearAll();
            }
        };
        clearAllLL.setOnClickListener(clearAllOnClickListener);
        clearAllBT.setOnClickListener(clearAllOnClickListener);
    }

    @Override
    protected void initData() {
        linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rv.setLayoutManager(linearLayoutManager);
        recentAppListAdapter=new RecentAppListAdapter(getContext(),itemClick,nowIndex,clearAllBT);
        rv.setAdapter(recentAppListAdapter);
        stopAnimation=AnimationUtils.loadAnimation(getContext(), R.anim.push_left_out);
        stopAnimation.setAnimationListener(animationListener);
    }

    @Override
    protected void initControl() {

    }

    @Override
    protected void viewIsShow() {
            getData();
    }

    @Override
    protected void viewIsPause() {

    }

    @Override
    protected void close() {

    }

    @Override
    protected void changeConfig() {

    }

   private void getData(){
       viewHandler.removeCallbacks(getDataRunnable);
       viewHandler.post(getDataRunnable);
   }

    @Override
    public void keyDo(KeyCodesEventType eventType) {
        switch (eventType){
            case TOP:
                toLast();
                break;
            case DOWN:
                toNext();
                break;
            case QUE:
                if(recentAppListAdapter.getItemCount()==0){
                    return;
                }
                if(recentAppListAdapter.getItemCount()==nowIndex){
                    doClearAll();
                }else {
                    recentAppListAdapter.setItemSelectData(nowIndex);
                }
                break;
            case LEFT:
                RecentAppShowBean recentAppShowBean=recentAppListAdapter.getItemData(nowIndex);
                LinearLayout closeView=recentAppListAdapter.getSelectView();
                itemClick.needClear(recentAppShowBean,closeView);
                break;
        }
    }


    void toNext(){
        if(recentAppListAdapter.getItemCount()>0){
            nowIndex++;
            if(nowIndex>=recentAppListAdapter.getItemCount()){
                nowIndex=recentAppListAdapter.getItemCount();
            }
            recentAppListAdapter.setShowItem(nowIndex);
            linearLayoutManager.scrollToPosition(nowIndex);
        }else {
            nowIndex=-1;
        }

    }

    void toLast(){
        if(recentAppListAdapter.getItemCount()>0){
            nowIndex--;
            if(nowIndex<0){
                nowIndex=0;
            }
            recentAppListAdapter.setShowItem(nowIndex);
            linearLayoutManager.scrollToPosition(nowIndex);
        }else {
            nowIndex=-1;
        }
    }



    enum ViewType{
        none,
        SET_DATA,

    }
}
