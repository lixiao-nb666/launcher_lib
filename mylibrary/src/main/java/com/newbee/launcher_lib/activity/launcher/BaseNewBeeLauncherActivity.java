package com.newbee.launcher_lib.activity.launcher;

import android.content.pm.ApplicationInfo;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.GestureDetector;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.newbee.bulid_lib.mybase.activity.BaseCompatActivity;
import com.newbee.bulid_lib.mybase.share.MyShare;
import com.newbee.bulid_lib.util.myapp.MyAppUtils;
import com.newbee.launcher_lib.R;

import com.newbee.launcher_lib.activity.launcher.manager.NewBeeLauncherShowManeger;
import com.newbee.launcher_lib.activity.launcher.type.NewBeeLauncherMsgType;
import com.newbee.launcher_lib.activity.launcher.type.NewBeeLauncherShowType;
import com.newbee.launcher_lib.activity.launcher.util.BaseShowUtil;
import com.newbee.launcher_lib.activity.launcher.util.ShowBrevityUtil;
import com.newbee.launcher_lib.activity.launcher.util.ShowDazzleUtil;
import com.newbee.launcher_lib.config.NowAllAppListConfig;
import com.newbee.launcher_lib.manager.FirstStartNeedOpenManager;
import com.newbee.launcher_lib.util.ActivityKeyDownListUtil;
import com.newbee.launcher_lib.util.KeyCodesEventType;
import com.newbee.system_applist_lib.systemapp.PackageManagerUtil;
import com.newbee.system_applist_lib.systemapp.bean.ResultSystemAppInfoBean;
import com.newbee.system_applist_lib.systemapp.broadcastreceiver.SystemAppReceiverUtil;
import com.newbee.system_applist_lib.systemapp.observer.PackageManagerObserver;
import com.newbee.system_applist_lib.systemapp.observer.PackageManagerSubscriptionSubject;
import com.newbee.system_applist_lib.systemapp.observer.PackageManagerType;
import com.newbee.system_key_lib.systemkey.SystemKeyEvent;
import com.newbee.system_key_lib.systemkey.SystemKeyEventListen;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public abstract class BaseNewBeeLauncherActivity extends BaseCompatActivity  {

    public abstract List<String> getNeedHidePckList();
    public abstract Map<String,Integer> getSortMap();
    public abstract Map<String,Integer> getSortFuzzyNameMap();
    public abstract Map<String,String> getUsePckChangeNameMap();
    public abstract Map<String,String> getUseNameChangeNameMap();
    public abstract Map<String,String> getNameReplaceMap();
    public abstract Map<String,Integer> getUsePckChangeIconMap();



    private boolean isFirstGetApps=true;
    private PackageManagerObserver packageManagerObserver = new PackageManagerObserver() {
        @Override
        public void update(PackageManagerType eventBs, Object object) {
            switch (eventBs) {
                case GET_SYSTEM_APPS:
                    try {
                        Message msg=new Message();
                        msg.obj=object;
                        msg.what= NewBeeLauncherMsgType.INIT_LIST.ordinal();
                        viewHandler.sendMessage(msg);
                        if(isFirstGetApps){
                            isFirstGetApps=false;
                            FirstStartNeedOpenManager.getInstance().useOpenInfo(context);
                        }
                    }catch (Exception e){}
                    break;
                case ERR:
                    break;
            }
        }
    };

    private Handler viewHandler = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            NewBeeLauncherMsgType msgType=NewBeeLauncherMsgType.values()[msg.what];
            switch (msgType) {
                case INIT_LIST:
                    ResultSystemAppInfoBean initList= (ResultSystemAppInfoBean) msg.obj;
                    if(null==initList||null==initList.getAppList()||initList.getAppList().size()==0){
                        initList=new ResultSystemAppInfoBean();
                    }
                    NowAllAppListConfig.getInstance().setApps(initList.getAppList());
                    baseShowUtil.initData(initList);
                    break;
                case SET_TV:
                   /* SystemAppInfoBean appInfoBean= (SystemAppInfoBean) msg.obj;
                    titleTV.setText("nrmyw("+appInfoBean.getName()+")");*/
                    break;
            }
        }
    };


    private TextView otherTV;


    private BaseShowUtil baseShowUtil;

    public void getShowUtil(RecyclerView rv, RelativeLayout rl){
        NewBeeLauncherShowType showType=  NewBeeLauncherShowManeger.getInstance().getShowType();
        switch (showType){
            case BREVITY:
                baseShowUtil= new ShowBrevityUtil(context,rl);
                break;
            case DAZZLE:
                baseShowUtil= new ShowDazzleUtil(context,rv);
                break;
        }

    }

    @Override
    public int getViewLayoutRsId() {
        return R.layout.activity_launcher_nrmyw;
    }

    @Override
    public boolean needWAndH() {
        return true;
    }

    @Override
    public void initView() {
        getShowUtil(findViewById(R.id.lv),findViewById(R.id.rl_brevity));
        otherTV=findViewById(R.id.tv_other);

    }


    @Override
    public void initData() {

    }

    private SystemKeyEvent keyEventUtil = new SystemKeyEvent();
    private SystemAppReceiverUtil appReceiverUtil = new SystemAppReceiverUtil();
    private GestureDetector mDetector;
    protected  final float FLIP_DISTANCE = 30;

    @Override
    public void initControl() {
        keyEventUtil.setListen(keyEventListen);
        keyEventUtil.setKeyCodesToDoEvent(KeyCodesEventType.LEFT.ordinal(), ActivityKeyDownListUtil.toLeftList());
        keyEventUtil.setKeyCodesToDoEvent(KeyCodesEventType.LEFT.ordinal(), ActivityKeyDownListUtil.toUpList());
        keyEventUtil.setKeyCodesToDoEvent(KeyCodesEventType.RIGHT.ordinal(), ActivityKeyDownListUtil.toRightList());
        keyEventUtil.setKeyCodesToDoEvent(KeyCodesEventType.RIGHT.ordinal(), ActivityKeyDownListUtil.toDownList());
        keyEventUtil.setKeyCodesToDoEvent(KeyCodesEventType.QUE.ordinal(), ActivityKeyDownListUtil.queOk1());
        keyEventUtil.setKeyCodesToDoEvent(KeyCodesEventType.QUE.ordinal(), ActivityKeyDownListUtil.queOk2());
        keyEventUtil.setKeyCodesToDoEvent(KeyCodesEventType.BACK.ordinal(), ActivityKeyDownListUtil.toBackList());
        PackageManagerSubscriptionSubject.getInstance().addObserver(packageManagerObserver);
        PackageManagerUtil.getInstance().init(BaseNewBeeLauncherActivity.this,getNeedHidePckList(),getSortMap(),getSortFuzzyNameMap(),getUsePckChangeNameMap(),getUseNameChangeNameMap(),getNameReplaceMap(), getUsePckChangeIconMap());
        PackageManagerUtil.getInstance().setReceiverGetAppList(true);
        appReceiverUtil.start(this);
        /**
         * 滑动监听器
         */
        mDetector = new GestureDetector(this, new GestureDetector.OnGestureListener() {
            @Override
            public boolean onSingleTapUp(MotionEvent e) {
// TODO Auto-generated method stub
                return false;
            }

            @Override
            public void onShowPress(MotionEvent e) {
// TODO Auto-generated method stub
            }

            @Override
            public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
// TODO Auto-generated method stub

                return false;
            }

            @Override
            public void onLongPress(MotionEvent e) {
// TODO Auto-generated method stub
            }

            /**
             *
             * e1 The first down motion event that started the fling. e2 The
             * move motion event that triggered the current onFling.
             */
            @Override
            public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
                if(!isReSume){
                    return true;
                }
                if (e1.getX() - e2.getX() > FLIP_DISTANCE) {
                    baseShowUtil.nowCanDoEvent(KeyCodesEventType.LEFT.ordinal());
                    return true;
                }
                if (e2.getX() - e1.getX() > FLIP_DISTANCE) {
                    baseShowUtil.nowCanDoEvent(KeyCodesEventType.RIGHT.ordinal());
                    return true;
                }
                if (e1.getY() - e2.getY() > FLIP_DISTANCE) {
                    baseShowUtil.nowCanDoEvent(KeyCodesEventType.LEFT.ordinal());
                    return true;
                }
                if (e2.getY() - e1.getY() > FLIP_DISTANCE) {
                    baseShowUtil.nowCanDoEvent(KeyCodesEventType.RIGHT.ordinal());
                    return true;
                }
                return false;
            }

            @Override
            public boolean onDown(MotionEvent e) {
// TODO Auto-generated method stub
                return false;
            }

        });

    }

    @Override
    public boolean isLiveWall() {
        return true;
    }



    @Override
    public void getWAndH(int w, int h) {
        Log.i(tag,"kankanSystem-size:"+w+"-"+h);
        float tvSize=w/27;
//        titleTV.setTextSize(tvSize);
//        titleTV.setVisibility(View.VISIBLE);
//        otherTV.setTextSize((float) (tvSize/2.6));
//        otherTV.setVisibility(View.VISIBLE);
        baseShowUtil.initView(w,h);
        PackageManagerUtil.getInstance().toGetSystemApps();
    }

    @Override
    public void closeActivity() {
        appReceiverUtil.close(this);
        keyEventUtil.close();
        PackageManagerUtil.getInstance().close();
        viewHandler.removeCallbacksAndMessages(null);
        PackageManagerSubscriptionSubject.getInstance().removeObserver(packageManagerObserver);
    }



    @Override
    public void viewIsShow() {
        keyEventUtil.start();
    }

    @Override
    public void viewIsPause() {
        keyEventUtil.pause();
    }

    @Override
    public void changeConfig() {

    }

    //判断当前应用是否系统应用
    public boolean isSystemApp() {
        if((getApplicationInfo().flags & ApplicationInfo.FLAG_SYSTEM) > 0)
            return true;
        return false;
    }




    private SystemKeyEventListen keyEventListen=new SystemKeyEventListen() {
        @Override
        public void nowCanDoEvent(int eventTypeInt) {
            if(isReSume){
                baseShowUtil.nowCanDoEvent(eventTypeInt);
            }

        }
    };



    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyEventUtil.nowClickKeyCode(event)) {
            return  true ;
        }
        if(keyCode==KeyEvent.KEYCODE_BACK){
            return true ;
        }

        return super.onKeyDown(keyCode, event);
    }

    /**
     * 截获按键事件.发给ScanGunKeyEventHelper
     * @param event
     * @return
     */
    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        if (keyEventUtil.nowClickKeyCode(event)) {
            return true;
        }
        return super.dispatchKeyEvent(event);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        return mDetector.onTouchEvent(event);
    }



}
