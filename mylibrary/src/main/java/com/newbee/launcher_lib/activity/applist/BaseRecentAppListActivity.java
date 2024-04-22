package com.newbee.launcher_lib.activity.applist;

import android.os.Build;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;

import androidx.annotation.RequiresApi;

import com.newbee.bulid_lib.mybase.activity.BaseCompatActivity;
import com.newbee.bulid_lib.mybase.fragment.BaseFragmen_v4;
import com.newbee.bulid_lib.util.SelectViewUtil;
import com.newbee.launcher_lib.R;
import com.newbee.launcher_lib.adapter.FragmentAdapter;
import com.newbee.launcher_lib.fragment.BaseRecentImp;
import com.newbee.launcher_lib.fragment.RecentActivityShowFragment;
import com.newbee.launcher_lib.fragment.RecentServiceShowFragment;
import com.newbee.launcher_lib.util.ActivityKeyDownListUtil;
import com.newbee.launcher_lib.util.KeyCodesEventType;
import com.newbee.launcher_lib.view.NoScrollViewPager;
import com.newbee.system_key_lib.systemkey.SystemKeyEvent;
import com.newbee.system_key_lib.systemkey.SystemKeyEventListen;

import java.util.ArrayList;
import java.util.List;


@RequiresApi(api = Build.VERSION_CODES.M)
public abstract class BaseRecentAppListActivity extends BaseCompatActivity {

    public abstract List<String> getCanNotCleanPckList();


    private NoScrollViewPager vp;
    private List<BaseFragmen_v4> viewList = new ArrayList<>();
    private FragmentAdapter fragmentAdapter;
    private int nowIndex;
    private SelectViewUtil selectViewUtil;
    private Button activityBT,serviceBT;

    @Override
    public int getViewLayoutRsId() {
        return R.layout.activity_recent_app_list;
    }

    @Override
    public void initView() {
        vp = findViewById(R.id.vp);
        activityBT=findViewById(R.id.bt_activity_show);
        serviceBT=findViewById(R.id.bt_service_show);
        activityBT.setFocusable(false);
        serviceBT.setFocusable(false);
    }

    @Override
    public void initData() {
        selectViewUtil = new SelectViewUtil(activityBT, serviceBT);
        viewList.add(new RecentActivityShowFragment(getCanNotCleanPckList()));
        viewList.add(new RecentServiceShowFragment(getCanNotCleanPckList()));
        fragmentAdapter = new FragmentAdapter(getSupportFragmentManager(), viewList);
    }

    @Override
    public void initControl() {
        vp.setAdapter(fragmentAdapter);
        keyEventUtil.setListen(keyEventListen);
        keyEventUtil.setKeyCodesToDoEvent(KeyCodesEventType.LEFT.ordinal(), ActivityKeyDownListUtil.toLeftList());
        keyEventUtil.setKeyCodesToDoEvent(KeyCodesEventType.RIGHT.ordinal(), ActivityKeyDownListUtil.toRightList());
        keyEventUtil.setKeyCodesToDoEvent(KeyCodesEventType.TOP.ordinal(), ActivityKeyDownListUtil.toUpList());
        keyEventUtil.setKeyCodesToDoEvent(KeyCodesEventType.DOWN.ordinal(), ActivityKeyDownListUtil.toDownList());
        keyEventUtil.setKeyCodesToDoEvent(KeyCodesEventType.QUE.ordinal(), ActivityKeyDownListUtil.queOk1());
        keyEventUtil.setKeyCodesToDoEvent(KeyCodesEventType.QUE.ordinal(), ActivityKeyDownListUtil.queOk2());
        keyEventUtil.setKeyCodesToDoEvent(KeyCodesEventType.BACK.ordinal(), ActivityKeyDownListUtil.toBackList());
        activityBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setPagerIndex(0);
            }
        });
        serviceBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setPagerIndex(1);
            }
        });
    }

    @Override
    public void closeActivity() {
        keyEventUtil.close();
    }

    @Override
    public void viewIsShow() {
//        app();
//        toActivity(CleanProcessActivity.class);
        keyEventUtil.start();
        selectViewUtil.setSelectViewByIndex(0);
        setTextColor();
    }

    @Override
    public void viewIsPause() {
        keyEventUtil.pause();
        finish();
    }

    @Override
    public void changeConfig() {
    }


    private SystemKeyEvent keyEventUtil = new SystemKeyEvent();
    private SystemKeyEventListen keyEventListen = new SystemKeyEventListen() {
        @Override
        public void nowCanDoEvent(int eventTypeInt) {
            if (isReSume) {
                KeyCodesEventType keyCodesEventType = KeyCodesEventType.values()[eventTypeInt];
                switch (keyCodesEventType) {
                    case LEFT:
                        if(!setPagerIndex(0)){
                            toFragmentDo(keyCodesEventType);
                        }
                        break;
                    case RIGHT:
                        if(!setPagerIndex(1)){
                            toFragmentDo(keyCodesEventType);
                        }
                        break;
                    case BACK:
                        finish();
                        break;
                    default:
                        toFragmentDo(keyCodesEventType);
                        break;
                }
            }
        }
    };



    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyEventUtil.nowClickKeyCode(event)) {
            return true;
        }
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }

    /**
     * 截获按键事件.发给ScanGunKeyEventHelper
     *
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


    private boolean setPagerIndex(int setIndex) {
        if (nowIndex != setIndex) {
            nowIndex = setIndex;
            vp.setCurrentItem(nowIndex);
            selectViewUtil.setSelectViewByIndex(setIndex);
            setTextColor();
            return true;
        }else {
            return false;
        }
    }



    private void toFragmentDo(KeyCodesEventType keyCodesEventType){
        BaseFragmen_v4 baseFragmen_v4=viewList.get(nowIndex);
        if(baseFragmen_v4.isResumed()&&baseFragmen_v4 instanceof BaseRecentImp){
            BaseRecentImp baseRecentImp= (BaseRecentImp) viewList.get(nowIndex);
            baseRecentImp.keyDo(keyCodesEventType);
        }
    }

    private void setTextColor(){
        activityBT.setTextColor(getTextColor(activityBT.isSelected()));
        serviceBT.setTextColor(getTextColor(serviceBT.isSelected()));
    }

    private int getTextColor(boolean isSelect){

        return getResources().getColor(isSelect? R.color.black:R.color.white);
    }





}
