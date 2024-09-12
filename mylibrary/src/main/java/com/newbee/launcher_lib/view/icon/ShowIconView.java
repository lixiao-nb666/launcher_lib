package com.newbee.launcher_lib.view.icon;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;

import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;

import androidx.recyclerview.widget.RecyclerView;

import com.newbee.launcher_lib.R;
import com.newbee.launcher_lib.bean.show_icon.ShowIconBean;
import com.newbee.launcher_lib.util.image.GetSystemIconUtil;
import com.newbee.system_applist_lib.systemapp.bean.ResultSystemAppInfoBean;
import com.newbee.system_applist_lib.systemapp.bean.SystemAppInfoBean;


public class ShowIconView extends LinearLayout {
    private FrameLayout iconFL;
    private ImageView iconIV;
    private TextView appNameTV;
    private RecyclerView recyclerView;
    private ShowIconAdapter adapter;
    private ShowIconViewItemClick itemClick=new ShowIconViewItemClick() {
        @Override
        public void nowSelect(SystemAppInfoBean systemAppInfoBean) {
            myListen.nowIsClick();
        }
    };
    private Handler viewHandler=new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            try {
                ViewType viewType= ViewType.values()[msg.what];
                switch (viewType){
                    case set_data:
                        ShowIconBean showIconBean= (ShowIconBean) msg.obj;
                        if(null==showIconBean||null==showIconBean.getIconType()){
                            return;
                        }
                        switch (showIconBean.getIconType()){
                            case Icon:
                                recyclerView.setVisibility(GONE);
                                iconIV.setVisibility(VISIBLE);
                                appNameTV.setText(showIconBean.getIconName());
                                GetSystemIconUtil.getInstance().setAppIcon(iconIV,  showIconBean.getSystemAppInfoBean());
                                break;
                            case Group:
                                iconIV.setVisibility(GONE);
                                recyclerView.setVisibility(VISIBLE);
                                appNameTV.setText(showIconBean.getIconName());
                                ResultSystemAppInfoBean resultSystemAppInfoBean=showIconBean.getResultSystemAppInfoBean();

                                adapter.setData(resultSystemAppInfoBean.getAppList());
                                break;
                        }
                        break;
                }
            }catch (Exception e){

            }

        }
    };

    enum ViewType{
        none,
        set_data,
    }


    public ShowIconView(Context context) {this(context,null);
    }

    public ShowIconView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public ShowIconView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr,0);

    }

    public ShowIconView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        View.inflate(context, R.layout.view_group_icon,this);
        iconFL=findViewById(R.id.fl_icon);
        recyclerView=findViewById(R.id.rv_group_icon);
        adapter=new ShowIconAdapter(context,itemClick);
        GridLayoutManager gridLayoutManager=new GridLayoutManager(context,3);
        recyclerView.setLayoutManager(gridLayoutManager);
        iconIV=findViewById(R.id.iv_icon);
        appNameTV=findViewById(R.id.tv_app_name);
    }

    private MyListen myListen;
    private boolean isInit;
    public void init(MyListen myListen,int iconSize){
        this.myListen=myListen;
        LayoutParams lp=new LayoutParams(iconSize,iconSize);
        lp.bottomMargin=(int) (iconSize/6.5f);
        int paddingSize=iconSize/14;
        iconFL.setLayoutParams(lp);
        iconFL.setPadding(paddingSize,paddingSize,paddingSize,paddingSize);
        float textSize=iconSize/7.4f;
        appNameTV.setTextSize(textSize);
        adapter.setViewSize((int) (iconSize/4.2));
        recyclerView.setAdapter(adapter);
        isInit=true;
        if(null!=lastBean){
            setData(lastBean);
            lastBean=null;
        }
        OnClickListener onClickListener=new OnClickListener() {
            @Override
            public void onClick(View v) {
                myListen.nowIsClick();
            }
        };
        setOnClickListener(onClickListener);
        iconFL.setOnClickListener(onClickListener);
        iconIV.setOnClickListener(onClickListener);
        appNameTV.setOnClickListener(onClickListener);
        recyclerView.setOnClickListener(onClickListener);
    }

    public void clear(){
        viewHandler.removeCallbacksAndMessages(null);
    }



    private ShowIconBean lastBean;
    public void setData(ShowIconBean showIconBean){
        if(!isInit){
            lastBean=showIconBean;
            return;
        }
        lastBean=null;
        if(null==showIconBean){
            return;
        }
        Message msg=new Message();
        msg.what= ViewType.set_data.ordinal();
        msg.obj=showIconBean;
        viewHandler.sendMessage(msg);

    }



    public interface MyListen{


        public void nowIsClick();
    }
}
