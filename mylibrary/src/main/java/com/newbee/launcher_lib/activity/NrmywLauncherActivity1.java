//package com.nymyw.launcher.activity;
//
//import android.content.pm.ApplicationInfo;
//import android.os.Handler;
//import android.os.Message;
//import android.view.KeyEvent;
//import android.view.View;
//import android.widget.TextView;
//
//import androidx.annotation.NonNull;
//import androidx.recyclerview.widget.LinearLayoutManager;
//import androidx.recyclerview.widget.PagerSnapHelper;
//import androidx.recyclerview.widget.RecyclerView;
//
//import com.newbee.bulid_lib.mybase.activity.BaseCompatActivity;
//import com.newbee.bulid_lib.util.myapp.MyAppUtils;
//import com.newbee.system_applist_lib.systemapp.PackageManagerUtil;
//import com.newbee.system_applist_lib.systemapp.StartOtherApkUtil;
//import com.newbee.system_applist_lib.systemapp.bean.ResultSystemAppInfoBean;
//import com.newbee.system_applist_lib.systemapp.bean.SystemAppInfoBean;
//import com.newbee.system_applist_lib.systemapp.broadcastreceiver.SystemAppReceiverUtil;
//import com.newbee.system_applist_lib.systemapp.observer.PackageManagerObserver;
//import com.newbee.system_applist_lib.systemapp.observer.PackageManagerSubscriptionSubject;
//import com.newbee.system_applist_lib.systemapp.observer.PackageManagerType;
//import com.newbee.system_key_lib.systemkey.SystemKeyEvent;
//import com.newbee.system_key_lib.systemkey.SystemKeyEventListen;
//import com.nymyw.launcher.R;
//import com.nymyw.launcher.adapter.NrmywAdapter;
//import com.nymyw.launcher.share.NrmywShare;
//import com.nymyw.launcher.util.ActivityKeyDownListUtil;
//import com.nymyw.launcher.util.KeyCodesEventType;
//import com.nymyw.launcher.util.SystemUtil;
//import com.nymyw.launcher.view.SpeedConfig;
//
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//
//public class NrmywLauncherActivity1 extends BaseCompatActivity {
//
//    private PackageManagerObserver packageManagerObserver = new PackageManagerObserver() {
//        @Override
//        public void update(PackageManagerType eventBs, Object object) {
//            switch (eventBs) {
//                case GET_SYSTEM_APPS:
//                    try {
//                        Message msg=new Message();
//                        msg.obj=object;
//                        msg.what= NrmywLauncherMsgType.INIT_LIST.ordinal();
//                        viewHandler.sendMessage(msg);
//                    }catch (Exception e){}
//                    break;
//                case ERR:
//                    break;
//            }
//        }
//    };
//
//    private Handler viewHandler = new Handler() {
//        @Override
//        public void handleMessage(@NonNull Message msg) {
//            super.handleMessage(msg);
//            NrmywLauncherMsgType msgType=NrmywLauncherMsgType.values()[msg.what];
//            switch (msgType) {
//                case INIT_LIST:
//                    if(adapter.getItemCount()==0){
//                        ResultSystemAppInfoBean initList= (ResultSystemAppInfoBean) msg.obj;
//                        adapter.setData(initList.getAppList());
//                        keyEventListen.nowCanDoEvent(KeyCodesEventType.BACK.ordinal());
//                    }else {
//                        ResultSystemAppInfoBean initList= (ResultSystemAppInfoBean) msg.obj;
//                        adapter.setData(initList.getAppList());
//                        refreshIndex();
//                    }
//                    break;
//                case SET_TV:
//                   /* SystemAppInfoBean appInfoBean= (SystemAppInfoBean) msg.obj;
//                    titleTV.setText("nrmyw("+appInfoBean.getName()+")");*/
//                    break;
//            }
//        }
//    };
//    private RecyclerView appLV;
//    private RecyclerView.OnScrollListener onScrollListener=new RecyclerView.OnScrollListener() {
//        @Override
//        public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
//            super.onScrollStateChanged(recyclerView, newState);
//        }
//        @Override
//        public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
//            super.onScrolled(recyclerView, dx, dy);
//            refreshIndex();
//        }
//    };
//
//    private NrmywAdapter adapter;
//    private NrmywAdapter.ItemClick itemChick = new NrmywAdapter.ItemClick() {
//        @Override
//        public void nowSelect(SystemAppInfoBean appInfoBean) {
//            if (null != appInfoBean) {
//                StartOtherApkUtil.getInstance().doStartApk(context,appInfoBean.getPakeageName(),appInfoBean.getIndexActivityClass());
//            }
//        }
//    };
//
//    private TextView titleTV,otherTV;
//
//    @Override
//    public int getViewLayoutRsId() {
//        return R.layout.activity_launcher_nrmyw;
//    }
//
//    @Override
//    public boolean needWAndH() {
//        return true;
//    }
//
//    @Override
//    public void initView() {
//        appLV = findViewById(R.id.lv);
//        titleTV = findViewById(R.id.tv_title);
//        otherTV=findViewById(R.id.tv_other);
//    }
//
//
//    @Override
//    public void initData() {
//        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
//        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
//        appLV.setLayoutManager(linearLayoutManager);
//        PagerSnapHelper pagerSnapHelper = new PagerSnapHelper();
//        pagerSnapHelper.attachToRecyclerView(appLV);
//        appLV.addOnScrollListener(onScrollListener);
//    }
//
//    private SystemKeyEvent keyEventUtil = new SystemKeyEvent();
//    private SystemAppReceiverUtil appReceiverUtil = new SystemAppReceiverUtil();
//
//    @Override
//    public void initControl() {
//        keyEventUtil.setListen(keyEventListen);
//        keyEventUtil.setKeyCodesToDoEvent(KeyCodesEventType.LEFT.ordinal(), ActivityKeyDownListUtil.toLeftList1());
//        keyEventUtil.setKeyCodesToDoEvent(KeyCodesEventType.LEFT.ordinal(), ActivityKeyDownListUtil.toLeftList2());
//        keyEventUtil.setKeyCodesToDoEvent(KeyCodesEventType.RIGHT.ordinal(), ActivityKeyDownListUtil.toRightList1());
//        keyEventUtil.setKeyCodesToDoEvent(KeyCodesEventType.RIGHT.ordinal(), ActivityKeyDownListUtil.toRightList2());
//        keyEventUtil.setKeyCodesToDoEvent(KeyCodesEventType.QUE.ordinal(), ActivityKeyDownListUtil.queOk1());
//        keyEventUtil.setKeyCodesToDoEvent(KeyCodesEventType.QUE.ordinal(), ActivityKeyDownListUtil.queOk2());
//        keyEventUtil.setKeyCodesToDoEvent(KeyCodesEventType.BACK.ordinal(), ActivityKeyDownListUtil.toBackList());
//
//        PackageManagerSubscriptionSubject.getInstance().addObserver(packageManagerObserver);
//        List<String> needHidePack=new ArrayList<>();
//        needHidePack.add(MyAppUtils.getPackageName());
//        needHidePack.add("com.android.stk");
//        needHidePack.add("com.android.documentsui");
//        needHidePack.add("com.android.deskclock");
//        needHidePack.add("com.android.dialer");
////                needHidePack.add("com.android.settings");
//        needHidePack.add("com.android.gallery3d");
//        needHidePack.add("com.mediatek.gnss.nonframeworklbs");
//        needHidePack.add("com.android.quicksearchbox");
//        //投屏必带的包
//        needHidePack.add("com.ecloud.eairplay");
//        needHidePack.add("com.eshare.miracast");
//        needHidePack.add("com.ecloud.eshare.server");
//        Map<String,Integer> sortMap=new HashMap<>();
//        sortMap.put("com.mediatek.camera",1);
//        Map<String,Integer> sortFuzzyNameMap=new HashMap<>();
//        sortFuzzyNameMap.put("nrmyw",9);
////        needHidePack.clear();
//        PackageManagerUtil.getInstance().init(NrmywLauncherActivity1.this,needHidePack,sortMap,sortFuzzyNameMap);
//        PackageManagerUtil.getInstance().setReceiverGetAppList(true);
//        appReceiverUtil.start(this);
//        /**
//         * 設置系統相機支持遙控器
//         */
//        SystemUtil.setCameraKeyCodeCanUse();
//
//    }
//
//    @Override
//    public boolean isLiveWall() {
//        return true;
//    }
//
//    private void initRecyclerView() {
//        // mRecyclerView绑定scale效果
//        if(w==0||h==0){
//            return;
//        }
//        NrmywLauncherShowType showType=  NrmywLauncherShowManeger.getInstance().getShowType();
//        switch (showType){
//            case BREVITY:
//                initRVByBREVITY();
//                break;
//            case DAZZLE:
//                initRVByDAZZLE();
//                break;
//        }
//
//
//
//    }
//
//
//    private void initRVByBREVITY(){
//        adapter = new NrmywAdapter(context, itemChick,w,h,false);
//        appLV.setAdapter(adapter);
//        /**
//         * 此处需要设置 paddingStart和paddingEnd,即旁边显示的大小
//         * android:paddingStart="160dp"
//         * android:paddingEnd="160dp"
//         */
//        int needShowPx=w/9*2;
//        appLV.setPaddingRelative(needShowPx,0,needShowPx,0);
//    }
//
//    private void initRVByDAZZLE(){
//        adapter = new NrmywAdapter(context, itemChick,w,h,false);
//        appLV.setAdapter(adapter);
//        /**
//         * 此处需要设置 paddingStart和paddingEnd,即旁边显示的大小
//         * android:paddingStart="160dp"
//         * android:paddingEnd="160dp"
//         */
//        int needShowPx=w/9*2;
//        appLV.setPaddingRelative(needShowPx,0,needShowPx,0);
//    }
//
//    int w,h;
//    @Override
//    public void getWAndH(int w, int h) {
//        float tvSize=w/27;
//        titleTV.setTextSize(tvSize);
//        titleTV.setVisibility(View.VISIBLE);
//        otherTV.setTextSize((float) (tvSize/2.6));
//        otherTV.setVisibility(View.VISIBLE);
//        this.w=w;
//        this.h=h;
//        initRecyclerView();
//        PackageManagerUtil.getInstance().toGetSystemApps();
//    }
//
//    @Override
//    public void closeActivity() {
//        appReceiverUtil.close(this);
//        keyEventUtil.close();
//        PackageManagerUtil.getInstance().close();
//        viewHandler.removeCallbacksAndMessages(null);
//        PackageManagerSubscriptionSubject.getInstance().removeObserver(packageManagerObserver);
//    }
//
//
//
//    @Override
//    public void viewIsShow() {
//        keyEventUtil.start();
//
//
//    }
//
//    @Override
//    public void viewIsPause() {
//        keyEventUtil.pause();
//    }
//
//    @Override
//    public void changeConfig() {
//
//    }
//
//    //判断当前应用是否系统应用
//    public boolean isSystemApp() {
//        if((getApplicationInfo().flags & ApplicationInfo.FLAG_SYSTEM) > 0)
//            return true;
//        return false;
//    }
//
//
//
//
//    private SystemKeyEventListen keyEventListen=new SystemKeyEventListen() {
//        @Override
//        public void nowCanDoEvent(int eventTypeInt) {
//            if(null==adapter||adapter.getItemCount()==0){
//                return;
//            }
//
//            KeyCodesEventType eventType = KeyCodesEventType.values()[eventTypeInt];
//            switch (eventType) {
//                case NONE:
//                    break;
//                case LEFT:
//
//                    index--;
//                    if (index < 0) {
//                        index = 0;
//                    }
//                    appLV.smoothScrollToPosition(index);
//                    break;
//                case RIGHT:
//
//                    index++;
//                    if (index >= adapter.getItemCount()) {
//                        index = adapter.getItemCount() - 1;
//                    }
//                    appLV.smoothScrollToPosition(index);
//                    break;
//                case QUE:
//
//                    adapter.setItemSelectData(index);
//                    break;
//                case BACK:
//
//                    index= NrmywShare.getInstance().getShowIndex();
//                    if (index < 0) {
//                        index = 0;
//                    }else if(index >= adapter.getItemCount()){
//                        index = adapter.getItemCount() - 1;
//                    }
//                    appLV.scrollToPosition(index);
//                    break;
//
//            }
//        }
//    };
//
//
//
//    @Override
//    public boolean onKeyDown(int keyCode, KeyEvent event) {
//        if (keyEventUtil.nowClickKeyCode(event)) {
//            return  true ;
//        }
//        return super.onKeyDown(keyCode, event);
//    }
//
//    /**
//     * 截获按键事件.发给ScanGunKeyEventHelper
//     * @param event
//     * @return
//     */
//    @Override
//    public boolean dispatchKeyEvent(KeyEvent event) {
//
////        showToast("nowcode:"+event.getKeyCode());
//        if (keyEventUtil.nowClickKeyCode(event)) {
//            return true;
//        }
//        return super.dispatchKeyEvent(event);
//    }
//
//    private int index=-1;
//    public void  refreshIndex(){
//        try {
//            int childCount = appLV.getChildCount();
//            for (int i = 0; i < childCount; i++) {
//                View child = appLV.getChildAt(i);
//                int left = child.getLeft();
//                int paddingStart = appLV.getPaddingStart();
//                // 遍历recyclerView子项，以中间项左侧偏移量为基准进行缩放
//                float bl = Math.min(1, Math.abs(left - paddingStart) * 1f / child.getWidth());
//                float scale = SpeedConfig.MaxScale - bl * (SpeedConfig.MaxScale- SpeedConfig.MinScale);
//                child.setScaleX(scale);
//                child.setScaleY(scale);
//                if(bl==0){
//                   index= appLV.getChildAdapterPosition(child);
//                   shareIndex();
//                }
//            }
//        }catch (Exception e){}
//    }
//
//    private void shareIndex(){
//        NrmywShare.getInstance().putShowIndex(index);
//    }
//}
