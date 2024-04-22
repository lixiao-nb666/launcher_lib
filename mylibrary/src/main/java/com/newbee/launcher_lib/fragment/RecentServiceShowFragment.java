package com.newbee.launcher_lib.fragment;

import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.newbee.bulid_lib.mybase.fragment.BaseFragmen_v4;
import com.newbee.launcher_lib.R;
import com.newbee.launcher_lib.adapter.RecentServiceListAdapter;
import com.newbee.launcher_lib.bean.RecentServiceShowBean;
import com.newbee.launcher_lib.util.KeyCodesEventType;
import com.newbee.launcher_lib.util.allappmanger.AllAppManager;


import java.util.ArrayList;
import java.util.List;

public class RecentServiceShowFragment extends BaseFragmen_v4 implements BaseRecentImp{
    private final String tag=getClass().getName()+">>>>";
    private RecyclerView rv;
    private Button clearAllBT;
    private LinearLayout clearAllLL;
    private int nowIndex=-1;
    private LinearLayoutManager linearLayoutManager;
    private RecentServiceListAdapter recentServiceListAdapter;
    private RecentServiceListAdapter.ItemClick itemClick=new RecentServiceListAdapter.ItemClick() {
        @Override
        public void nowSelect(RecentServiceShowBean recentServiceShowBean) {
//            if(null==recentServiceShowBean||
//                    TextUtils.isEmpty(recentServiceShowBean.getPckName())||
//
//                    !StartOtherApkUtil.getInstance().checkAppIsInstalled(getContext(),recentServiceShowBean.getPckName())){
//                return;
//            }
//            StartOtherApkUtil.getInstance().doStartApk(getContext(),recentAppShowBean.getPckName(),recentAppShowBean.getNeedStartCls());
//            getActivity().finish();
        }


        @Override
        public void needClear(RecentServiceShowBean recentServiceShowBean, LinearLayout selectView) {
            if(null!=recentServiceShowBean&&!TextUtils.isEmpty(recentServiceShowBean.getPckName())&&null!=selectView&&null!=stopAnimation){
                AllAppManager.getInstance().stopOtherService(recentServiceShowBean.getPckName());
                selectView.startAnimation(stopAnimation);
            }
        }
    };

    private void doClearAll(){
        List<RecentServiceShowBean> list=recentServiceListAdapter.getList();
        if(null==list||list.size()==0){
            return;
        }
        recentServiceListAdapter.clearShowIndex();
        for(RecentServiceShowBean bean:list){
            if(null!=bean&&!TextUtils.isEmpty(bean.getPckName())){
                AllAppManager.getInstance().stopOtherService(bean.getPckName());
            }
        }
        rv.startAnimation(stopAnimation);
    }


    private Handler viewHandler=new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            RecentActivityShowFragment.ViewType viewType= RecentActivityShowFragment.ViewType.values()[msg.what];
            switch (viewType){
                case SET_DATA:
                    recentServiceListAdapter.setData((List<RecentServiceShowBean>) msg.obj);
                    if(recentServiceListAdapter.getItemCount()==0){
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
            msg.what= RecentActivityShowFragment.ViewType.SET_DATA.ordinal();
            msg.obj=AllAppManager.getInstance().getRecentServiceList(canNotCleanPckList);
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
    public RecentServiceShowFragment(List<String> canNotCleanPckList){
        this.canNotCleanPckList=canNotCleanPckList;
    }

    @Override
    protected int initViewLayout() {
        return R.layout.fragment_recent_service;
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
        recentServiceListAdapter=new RecentServiceListAdapter(getContext(),itemClick,nowIndex,clearAllBT);
        rv.setAdapter(recentServiceListAdapter);
        stopAnimation= AnimationUtils.loadAnimation(getContext(), R.anim.push_right_out);
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
                if(recentServiceListAdapter.getItemCount()==0){
                    return;
                }
                if(recentServiceListAdapter.getItemCount()==nowIndex){
                    doClearAll();
                }else {
                    recentServiceListAdapter.setItemSelectData(nowIndex);
                }
                break;
            case RIGHT:
                RecentServiceShowBean recentServiceShowBean=recentServiceListAdapter.getItemData(nowIndex);
                LinearLayout closeView=recentServiceListAdapter.getSelectView();
                itemClick.needClear(recentServiceShowBean,closeView);
                break;
        }
    }


    void toNext(){
        if(recentServiceListAdapter.getItemCount()>0){
            nowIndex++;
            if(nowIndex>=recentServiceListAdapter.getItemCount()){
                nowIndex=recentServiceListAdapter.getItemCount();
            }
           recentServiceListAdapter.setShowItem(nowIndex);
            linearLayoutManager.scrollToPosition(nowIndex);
        }else {
            nowIndex=-1;
        }

    }

    void toLast(){
        if(recentServiceListAdapter.getItemCount()>0){
            nowIndex--;
            if(nowIndex<0){
                nowIndex=0;
            }
            recentServiceListAdapter.setShowItem(nowIndex);
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
