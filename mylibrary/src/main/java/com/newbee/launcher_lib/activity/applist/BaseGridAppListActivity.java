package com.newbee.launcher_lib.activity.applist;

import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.newbee.bulid_lib.mybase.activity.BaseCompatActivity;
import com.newbee.gson_lib.gson.MyGson;
import com.newbee.launcher_lib.R;
import com.newbee.launcher_lib.adapter.GridAppListAdapter;
import com.newbee.launcher_lib.bean.show_icon.ShowIconBean;
import com.newbee.launcher_lib.util.MyWStartUtil;
import com.newbee.system_applist_lib.systemapp.bean.ResultSystemAppInfoBean;
import com.newbee.system_applist_lib.systemapp.bean.SystemAppInfoBean;

public  class BaseGridAppListActivity extends BaseCompatActivity {

    private LinearLayout ll;
    private ShowIconBean showIconBean;
    private TextView groupNameTV;
    private RecyclerView rv;
    private GridAppListAdapter adapter;
    private GridAppListAdapter.ItemClick itemClick=new GridAppListAdapter.ItemClick() {
        @Override
        public void nowSelect(SystemAppInfoBean nowApp) {
            try {

                MyWStartUtil.toOtherApk(context,nowApp.getPakeageName(),nowApp.getIndexActivityClass());
            }catch (Exception e){}

        }
    };

    @Override
    public int getViewLayoutRsId() {
        return R.layout.activity_grid_applist;
    }

    @Override
    public void initView() {
        ll=findViewById(R.id.ll);
        groupNameTV=findViewById(R.id.tv_group_name);
        rv=findViewById(R.id.rv);
    }

    @Override
    public void initData() {
        try {
            String gsStr=getIntentString();
            showIconBean= MyGson.getInstance().fromJson(gsStr,ShowIconBean.class);
        }catch (Exception e){}
        if(null==showIconBean||null==showIconBean.getResultSystemAppInfoBean()||null==showIconBean.getResultSystemAppInfoBean().getAppList()){
            finish();
            return;
        }
        groupNameTV.setText(showIconBean.getIconName());
        adapter=new GridAppListAdapter(context,itemClick);
        GridLayoutManager gridLayoutManager=new GridLayoutManager(context,5);
        rv.setLayoutManager(gridLayoutManager);
        rv.setAdapter(adapter);



    }

    @Override
    public void initControl() {

    }

    @Override
    public boolean needWAndH() {
        return true;
    }

    @Override
    public void getWAndH(int w, int h) {
        super.getWAndH(w, h);
        try {
            groupNameTV.setTextSize(h/20);
            adapter.setData(showIconBean.getResultSystemAppInfoBean().getAppList(),h/5);
        }catch (Exception e){}

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
