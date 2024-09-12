package com.newbee.launcher_lib.view.icon;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;

import android.view.View;

import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;


import com.newbee.launcher_lib.R;

import com.newbee.launcher_lib.util.image.GetSystemIconUtil;

import com.newbee.system_applist_lib.systemapp.bean.SystemAppInfoBean;

public class ShowIconByAppView extends LinearLayout {
    private ImageView appIconIV;
    private TextView appNameTV;

    private Handler viewHandler=new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            try {
                final SystemAppInfoBean app = (SystemAppInfoBean) msg.obj;
                GetSystemIconUtil.getInstance().setAppIconAndNameByAppList(appIconIV,appNameTV,app.getName(),app.getPakeageName());
                if(null!=itemClick){
                    View.OnClickListener onClickListener = new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if(null!=itemClick){
                                itemClick.nowSelect(app);
                            }
                        }
                    };
                   appIconIV.setOnClickListener(onClickListener);
                }

            }catch (Exception e){

            }

        }
    };

    enum ViewType{
        none,
        set_data,
    }


    public ShowIconByAppView(Context context) {this(context,null);
    }

    public ShowIconByAppView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public ShowIconByAppView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr,0);

    }

    public ShowIconByAppView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        View.inflate(context, R.layout.view_show_icon_by_app,this);
        appIconIV=findViewById(R.id.iv_icon);
        appNameTV=findViewById(R.id.tv_app_name);

    }


    private boolean isInit;
    public void init(int viewSize){

        LinearLayout.LayoutParams lp= (LinearLayout.LayoutParams) appIconIV.getLayoutParams();
        lp.leftMargin=viewSize/3;
        lp.rightMargin=viewSize/3;
        lp.topMargin=(int) (viewSize/6.5f);
        lp.bottomMargin= (int) (viewSize/6.5f);
        lp.width=viewSize;
        lp.height=viewSize;
        int paddingSize=viewSize/14;
        appIconIV.setPadding(paddingSize,paddingSize,paddingSize,paddingSize);
        appIconIV.setLayoutParams(lp);
        appNameTV.setTextSize(viewSize/7.4f);
        isInit=true;
    }

    public void clear(){
        viewHandler.removeCallbacksAndMessages(null);
    }



    private ShowIconViewItemClick itemClick;
    public void setData(ShowIconViewItemClick itemClick,SystemAppInfoBean appInfoBean){
        if(!isInit){
            return;
        }
        if(null==appInfoBean){
            return;
        }
        Message msg=new Message();
        msg.what= ViewType.set_data.ordinal();
        msg.obj=appInfoBean;
        viewHandler.sendMessage(msg);
        this.itemClick=itemClick;
    }




}
