package com.newbee.launcher_lib.activity.launcher.util;

import android.content.Context;

import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.newbee.bulid_lib.mybase.activity.util.ActivityUtil;
import com.newbee.gson_lib.gson.MyGson;
import com.newbee.launcher_lib.R;
import com.newbee.launcher_lib.activity.applist.BaseGridAppListActivity;
import com.newbee.launcher_lib.app.BaseLauncherApp;
import com.newbee.launcher_lib.bean.show_icon.ShowIconBean;
import com.newbee.launcher_lib.bean.show_icon.ShowIconGroupUtil;
import com.newbee.launcher_lib.bean.show_icon.ShowIconType;
import com.newbee.launcher_lib.bean.show_icon.ShowIconUtil;
import com.newbee.launcher_lib.share.NrmywShare;
import com.newbee.launcher_lib.util.KeyCodesEventType;
import com.newbee.launcher_lib.util.MyWStartUtil;
import com.newbee.launcher_lib.view.icon.ShowIconView;
import com.newbee.system_applist_lib.systemapp.bean.ResultSystemAppInfoBean;
import com.newbee.system_applist_lib.systemapp.bean.SystemAppInfoBean;

import java.util.ArrayList;
import java.util.List;


public class ShowBrevityUtil extends BaseShowUtil{

    private Context context;
    private RelativeLayout rl;
    private int index;
    private List<ShowIconBean> appList;
    private ShowIconView nowGIV,leftGIV,rightGIV;

    private ImageView toLeftIV,toRightIV;
    private TextView pagerTV;

    public ShowBrevityUtil(Context context, RelativeLayout rl){
        this.context=context;
        this.rl=rl;
        nowGIV=rl.findViewById(R.id.giv_now);



        leftGIV=rl.findViewById(R.id.giv_left);


        rightGIV=rl.findViewById(R.id.giv_right);



        toLeftIV=rl.findViewById(R.id.iv_to_left);
        toLeftIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nowCanDoEvent(KeyCodesEventType.LEFT.ordinal());
            }
        });
        toRightIV= rl.findViewById(R.id.iv_to_right);
        toRightIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nowCanDoEvent(KeyCodesEventType.RIGHT.ordinal());
            }
        });
        pagerTV=rl.findViewById(R.id.tv_pager);
    }

    @Override
    public void initView(int w, int h) {
        rl.setVisibility(View.VISIBLE);

        ShowIconView.MyListen nowListen=new ShowIconView.MyListen() {
            @Override
            public void nowIsClick() {
                nowSelect();
            }
        };
        nowGIV.init(nowListen,(int) (h/3.13f));

        ShowIconView.MyListen leftListen=new ShowIconView.MyListen() {
            @Override
            public void nowIsClick() {
                nowSelectLeft();
            }
        };
        leftGIV.init(leftListen,(int) (h/4.28f));
        ShowIconView.MyListen rightListen=new ShowIconView.MyListen() {
            @Override
            public void nowIsClick() {
                nowSelectRight();
            }
        };
        rightGIV.init(rightListen,(int) (h/4.28f));
        pagerTV.setTextSize(h/30);
    }

    @Override
    public void initData(ResultSystemAppInfoBean initList) {
        appList= ShowIconUtil. getNeedData(initList);
        if(appList.size()<=0){
            return;
        }
        index= NrmywShare.getInstance().getShowIndex();
        if(index<0){
            index=0;
        }
        if(index>=appList.size()){
            index=appList.size()-1;
        }
        setShowContent();
    }




    @Override
    public void nowCanDoEvent(int eventTypeInt) {
        if(null==appList||appList.size()<=0){
            return;
        }
        KeyCodesEventType eventType = KeyCodesEventType.values()[eventTypeInt];
        switch (eventType) {
            case NONE:
                break;
            case LEFT:
                index--;
                if (index < 0) {
                    index = appList.size() - 1;
                }
                NrmywShare.getInstance().putShowIndex(index);
                setShowContent();
                break;
            case RIGHT:
                index++;
                if (index >= appList.size()) {
                    index = 0;
                }
                NrmywShare.getInstance().putShowIndex(index);
                setShowContent();
                break;
            case QUE:
                nowSelect();
                break;
        }
    }


    private void nowSelect(){
        int nowIndex=index;
        if(nowIndex<appList.size()&&nowIndex>=0){
            //处理中间的事件
            selectIndex(nowIndex);
        }
    }

    private void nowSelectLeft(){
        int leftIndex=index-1;
        if(leftIndex<appList.size()&&leftIndex>=0){
            //处理中间的事件
            selectIndex(leftIndex);
        }
    }

    private void nowSelectRight(){
        int rightIndex=index+1;
        if(rightIndex<appList.size()&&rightIndex>=0){
            //处理中间的事件
            selectIndex(rightIndex);
        }
    }
    private void selectIndex(int needIndex){
        ShowIconBean showIconBean=appList.get(needIndex);
        if(null==showIconBean||null==showIconBean.getIconType()){
            return;
        }
        switch (showIconBean.getIconType()){
            case Icon:
                SystemAppInfoBean nowApp= showIconBean.getSystemAppInfoBean();
                MyWStartUtil.toOtherApk(context,nowApp.getPakeageName(),nowApp.getIndexActivityClass());
                break;
            case Group:
                Log.i("kankandaozhelimei","kankandaozhelimei1----");
                ActivityUtil.toActivity(context, BaseGridAppListActivity.class, MyGson.getInstance().toGsonStr(showIconBean));
                break;
        }
    }

    private void setShowContent(){
        showNow();
        showLeft();
        showRight();
        showPager();
    }

    private void showNow(){
        int nowIndex=index;
        if(nowIndex<appList.size()&&nowIndex>=0){
            //处理中间的事件
            ShowIconBean nowApp=appList.get(nowIndex);
            setShow(nowGIV,nowApp);

        }
    }
    private void showLeft(){
        int leftIndex=index-1;
        if(leftIndex<appList.size()&&leftIndex>=0){
            //处理左边的事件
            leftGIV.setVisibility(View.VISIBLE);
            toLeftIV.setVisibility(View.VISIBLE);
            ShowIconBean leftApp=appList.get(leftIndex);
            setShow(leftGIV,leftApp);

        }else {
            leftGIV.setVisibility(View.GONE);
            toLeftIV.setVisibility(View.GONE);
        }
    }

    private void showRight(){
        int rightIndex=index+1;
        if(rightIndex<appList.size()&&rightIndex>=0){
            //处理右边的事件
            rightGIV.setVisibility(View.VISIBLE);
            toRightIV.setVisibility(View.VISIBLE);
            ShowIconBean rightApp=appList.get(rightIndex);
            setShow(rightGIV,rightApp);
        }else {
            rightGIV.setVisibility(View.GONE);
            toRightIV.setVisibility(View.GONE);
        }
    }

    private void setShow(ShowIconView showIconView,ShowIconBean showIconBean){
        if(null==showIconView||null==showIconBean){
            return;
        }
        showIconView.setData(showIconBean);
    }

    private void showPager(){
        int nowIndex=index;
        if(nowIndex<appList.size()&&nowIndex>=0){
            //处理页面显示
            int showIndex=nowIndex+1;
            pagerTV.setText(showIndex+"/"+appList.size());
        }else {
            pagerTV.setText("");
        }
    }


}
