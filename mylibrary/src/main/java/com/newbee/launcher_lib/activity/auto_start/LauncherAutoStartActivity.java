package com.newbee.launcher_lib.activity.auto_start;

import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.newbee.bulid_lib.mybase.LG;
import com.newbee.bulid_lib.mybase.activity.BaseCompatActivity;
import com.newbee.launcher_lib.R;
import com.newbee.launcher_lib.adapter.GridAppListAdapter;
import com.newbee.launcher_lib.bean.show_icon.ShowIconBean;
import com.newbee.launcher_lib.bean.show_icon.ShowIconType;
import com.newbee.launcher_lib.bean.show_icon.ShowIconUtil;
import com.newbee.launcher_lib.config.NowAllAppListConfig;
import com.newbee.launcher_lib.util.MyWStartUtil;
import com.newbee.launcher_lib.util.system.AutoStratUtil;
import com.newbee.launcher_lib.view.icon.ShowIconByAppView;
import com.newbee.launcher_lib.view.icon.ShowIconView;
import com.newbee.launcher_lib.view.icon.ShowIconViewItemClick;
import com.newbee.system_applist_lib.systemapp.bean.ResultSystemAppInfoBean;
import com.newbee.system_applist_lib.systemapp.bean.SystemAppInfoBean;

import java.util.List;


public class LauncherAutoStartActivity extends BaseCompatActivity {
    private List<ShowIconBean> appList;
    private ShowIconByAppView nowSIV;
    private ShowIconViewItemClick nowItemClick=new ShowIconViewItemClick() {
        @Override
        public void nowSelect(SystemAppInfoBean systemAppInfoBean) {
            try {
                AutoStratUtil.getInstance().shareApk(context,"","");
                setNowIV();
            }catch (Exception e){}

        }
    };
    private RecyclerView rv;
    private GridAppListAdapter adapter;
    private ShowIconViewItemClick itemClick=new ShowIconViewItemClick() {
        @Override
        public void nowSelect(SystemAppInfoBean nowApp) {
            try {
                AutoStratUtil.getInstance().shareApk(context,nowApp.getPakeageName(),nowApp.getIndexActivityClass());
                setNowIV();
            }catch (Exception e){}
        }
    };

    @Override
    public int getViewLayoutRsId() {
        return R.layout.activity_auto_start;
    }

    @Override
    public void initView() {
        nowSIV=findViewById(R.id.siv_now);
        rv=findViewById(R.id.rv);
    }

    @Override
    public void initData() {

        ResultSystemAppInfoBean resultSystemAppInfoBean=new ResultSystemAppInfoBean();
        resultSystemAppInfoBean.setAppList(NowAllAppListConfig.getInstance().getApps());
        if(resultSystemAppInfoBean.getAppList()==null||resultSystemAppInfoBean.getAppList().size()==0){
           finish();
            return;
        }

        appList= ShowIconUtil. getNeedData(resultSystemAppInfoBean);
    }

    @Override
    public void initControl() {



        adapter=new GridAppListAdapter(context,itemClick);
        GridLayoutManager gridLayoutManager=new GridLayoutManager(context,5);
        rv.setLayoutManager(gridLayoutManager);
        rv.setAdapter(adapter);

    }

    @Override
    public boolean needWAndH() {
        return true;
    }

    @Override
    public void getWAndH(int w, int h) {
        nowSIV.init(h/4);

        setNowIV();
        adapter.setData(NowAllAppListConfig.getInstance().getApps(),h/5);
    }

    private void setNowIV(){
        String sharePck= AutoStratUtil.getInstance().getShareAutoApkPckStr(context);
        String shareCls=AutoStratUtil.getInstance().getShareAutoApkClsStr(context);
        if(TextUtils.isEmpty(sharePck)||TextUtils.isEmpty(shareCls)){

            nowSIV.setVisibility(View.GONE);
        }else {
            nowSIV.setVisibility(View.VISIBLE);
            SystemAppInfoBean systemAppInfoBean=NowAllAppListConfig.getInstance().getNeedAppInfo(sharePck);
            systemAppInfoBean.setIndexActivityClass(shareCls);
            nowSIV.setData(nowItemClick,systemAppInfoBean);
        }
    }

    @Override
    public void closeActivity() {

    }

    @Override
    public void viewIsShow() {

    }

    @Override
    public void viewIsPause() {

    }

    @Override
    public void changeConfig() {

    }
}
