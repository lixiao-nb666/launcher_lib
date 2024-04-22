package com.newbee.launcher_lib.activity.launcher.util;

import android.content.Context;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.newbee.launcher_lib.R;
import com.newbee.launcher_lib.share.NrmywShare;
import com.newbee.launcher_lib.util.KeyCodesEventType;
import com.newbee.launcher_lib.util.MyWStartUtil;
import com.newbee.launcher_lib.util.image.GetSystemIconUtil;
import com.newbee.system_applist_lib.systemapp.bean.ResultSystemAppInfoBean;
import com.newbee.system_applist_lib.systemapp.bean.SystemAppInfoBean;


public class ShowBrevityUtil extends BaseShowUtil{

    private Context context;
    private RelativeLayout rl;
    private int index;
    private ResultSystemAppInfoBean appList;
    private ImageView nowIV,leftIV,rightIV;
    private TextView nowTV,leftTV,rightTV;
    private LinearLayout leftLL,rightLL;
    private ImageView toLeftIV,toRightIV;
    private TextView pagerTV;

    public ShowBrevityUtil(Context context, RelativeLayout rl){
        this.context=context;
        this.rl=rl;
        nowIV=rl.findViewById(R.id.iv_now);
        nowTV=rl.findViewById(R.id.tv_now);
        nowIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nowSelect();
            }
        });

        leftLL=rl.findViewById(R.id.ll_left);
        leftIV=rl.findViewById(R.id.iv_left);
        leftTV=rl.findViewById(R.id.tv_left);
        leftIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nowSelectLeft();
            }
        });
        rightLL=rl.findViewById(R.id.ll_right);
        rightIV=rl.findViewById(R.id.iv_right);
        rightTV=rl.findViewById(R.id.tv_right);
        rightIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nowSelectRight();
            }
        });
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
    }

    @Override
    public void initData(ResultSystemAppInfoBean initList) {
        if(null==initList||null==initList.getAppList()){
            this.appList=new ResultSystemAppInfoBean();
        }else {
            this.appList=initList;
        }
        if(appList.getAppList().size()<=0){
            return;
        }
        index= NrmywShare.getInstance().getShowIndex();
        if(index<0){
            index=0;
        }
        if(index>=appList.getAppList().size()){
            index=appList.getAppList().size()-1;
        }
        setShowContent();
    }


    @Override
    public void nowCanDoEvent(int eventTypeInt) {
        if(null==appList||null==appList.getAppList()||appList.getAppList().size()<=0){
            return;
        }
        KeyCodesEventType eventType = KeyCodesEventType.values()[eventTypeInt];
        switch (eventType) {
            case NONE:
                break;
            case LEFT:
                index--;
                if (index < 0) {
                    index = appList.getAppList().size() - 1;
                }
                NrmywShare.getInstance().putShowIndex(index);
                setShowContent();
                break;
            case RIGHT:
                index++;
                if (index >= appList.getAppList().size()) {
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
        if(nowIndex<appList.getAppList().size()&&nowIndex>=0){
            //处理中间的事件
            SystemAppInfoBean nowApp=appList.getAppList().get(nowIndex);
            MyWStartUtil.toOtherApk(context,nowApp.getPakeageName(),nowApp.getIndexActivityClass());
        }
    }

    private void nowSelectLeft(){
        int leftIndex=index-1;
        if(leftIndex<appList.getAppList().size()&&leftIndex>=0){
            //处理中间的事件
            SystemAppInfoBean nowApp=appList.getAppList().get(leftIndex);
            MyWStartUtil.toOtherApk(context,nowApp.getPakeageName(),nowApp.getIndexActivityClass());
        }
    }

    private void nowSelectRight(){
        int rightIndex=index+1;
        if(rightIndex<appList.getAppList().size()&&rightIndex>=0){
            //处理中间的事件
            SystemAppInfoBean nowApp=appList.getAppList().get(rightIndex);
            MyWStartUtil.toOtherApk(context,nowApp.getPakeageName(),nowApp.getIndexActivityClass());
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
        if(nowIndex<appList.getAppList().size()&&nowIndex>=0){
            //处理中间的事件
            SystemAppInfoBean nowApp=appList.getAppList().get(nowIndex);
            setShowIVAndTv(nowIV,nowTV,nowApp);
        }
    }
    private void showLeft(){
        int leftIndex=index-1;
        if(leftIndex<appList.getAppList().size()&&leftIndex>=0){
            //处理左边的事件
            leftLL.setVisibility(View.VISIBLE);
            toLeftIV.setVisibility(View.VISIBLE);
            SystemAppInfoBean leftApp=appList.getAppList().get(leftIndex);
            setShowIVAndTv(leftIV,leftTV,leftApp);
        }else {
            leftLL.setVisibility(View.GONE);
            toLeftIV.setVisibility(View.GONE);
        }
    }

    private void showRight(){
        int rightIndex=index+1;
        if(rightIndex<appList.getAppList().size()&&rightIndex>=0){
            //处理右边的事件
            rightLL.setVisibility(View.VISIBLE);
            toRightIV.setVisibility(View.VISIBLE);
            SystemAppInfoBean rightApp=appList.getAppList().get(rightIndex);
            setShowIVAndTv(rightIV,rightTV,rightApp);
        }else {
            rightLL.setVisibility(View.GONE);
            toRightIV.setVisibility(View.GONE);
        }
    }

    private void setShowIVAndTv(ImageView showIV,TextView showTV,SystemAppInfoBean appInfo){
        GetSystemIconUtil.getInstance().setAppIconAndName(showIV,showTV,appInfo.getName(),appInfo.getPakeageName());
    }

    private void showPager(){
        int nowIndex=index;
        if(nowIndex<appList.getAppList().size()&&nowIndex>=0){
            //处理页面显示
            int showIndex=nowIndex+1;
            pagerTV.setText(showIndex+"/"+appList.getAppList().size());
        }else {
            pagerTV.setText("");
        }
    }


}
