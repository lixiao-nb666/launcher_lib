package com.newbee.launcher_lib.view.icon;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.newbee.launcher_lib.R;
import com.newbee.system_applist_lib.systemapp.bean.ResultSystemAppInfoBean;
import com.newbee.system_applist_lib.systemapp.bean.SystemAppInfoBean;

public class GroupIconView extends LinearLayout {
    private RecyclerView recyclerView;
    private GroupIconAdapter adapter;
    private GroupIconAdapter.ItemClick itemClick=new GroupIconAdapter.ItemClick() {
        @Override
        public void nowSelect(SystemAppInfoBean systemAppInfoBean) {

        }
    };
    private Handler viewHandler=new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            try {
                ViewType viewType=ViewType.values()[msg.what];
                switch (viewType){
                    case set_data:
                        ResultSystemAppInfoBean resultSystemAppInfoBean= (ResultSystemAppInfoBean) msg.obj;
                        if(null==resultSystemAppInfoBean||null==resultSystemAppInfoBean.getAppList()||resultSystemAppInfoBean.getAppList().size()==0){
                            return;
                        }
                        adapter.setData(resultSystemAppInfoBean.getAppList());
                        adapter.notifyDataSetChanged();
                        break;
                }
            }catch (Exception e){}

        }
    };

    enum ViewType{
        none,
        set_data,
    }


    public GroupIconView(Context context) {this(context,null);
    }

    public GroupIconView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public GroupIconView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr,0);

    }

    public GroupIconView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        View.inflate(context, R.layout.view_group_icon,this);
        recyclerView=findViewById(R.id.rv_group_icon);
        adapter=new GroupIconAdapter(context,itemClick);
        GridLayoutManager gridLayoutManager=new GridLayoutManager(context,3);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setAdapter(adapter);
    }

    public void close(){
    }

    public void setData(ResultSystemAppInfoBean resultSystemAppInfoBean){
        Message msg=new Message();
        msg.what=ViewType.set_data.ordinal();
        msg.obj=resultSystemAppInfoBean;
        viewHandler.sendMessage(msg);
    }


}
