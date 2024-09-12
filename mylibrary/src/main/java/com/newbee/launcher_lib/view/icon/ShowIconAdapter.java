package com.newbee.launcher_lib.view.icon;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.recyclerview.widget.RecyclerView;

import com.newbee.launcher_lib.R;
import com.newbee.launcher_lib.util.image.GetSystemIconUtil;
import com.newbee.system_applist_lib.systemapp.bean.SystemAppInfoBean;

import java.util.ArrayList;
import java.util.List;

public class ShowIconAdapter extends RecyclerView.Adapter {
    private final String tag = getClass().getName() + ">>>>";
    private List<SystemAppInfoBean> apps;
    private LayoutInflater layoutInflater;
    private ShowIconViewItemClick itemClick;

    public ShowIconAdapter(Context context, ShowIconViewItemClick itemClick) {
        layoutInflater = LayoutInflater.from(context);
        this.itemClick = itemClick;
        this.apps = new ArrayList<>();
    }

    private int viewSize;
    public void setViewSize(int viewSize){
        this.viewSize=viewSize;
    }


    public void setData(List<SystemAppInfoBean> apps) {
        if (apps == null) {
            this.apps = new ArrayList<>();
        }else {
            this.apps = apps;
        }
        notifyDataSetChanged();
    }



    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {




        View itemView = layoutInflater.inflate(R.layout.adapter_group_icon, parent, false);
        ViewHodler viewHodler = new ViewHodler(itemView);
        return viewHodler;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final ViewHodler viewHodler = (ViewHodler) holder;
        final SystemAppInfoBean app = apps.get(position);
        GetSystemIconUtil.getInstance().setAppIcon(viewHodler.appIconIV,app);
        if(null!=itemClick){
            View.OnClickListener onClickListener = new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    itemClick.nowSelect(app);
                }
            };
            viewHodler.appIconIV.setOnClickListener(onClickListener);
        }
    }

    @Override
    public int getItemCount() {
        if (null == apps) {
            return 0;
        }
        if(apps.size()>=9){
            return 9;
        }
        return apps.size();
    }

    class ViewHodler extends RecyclerView.ViewHolder {


        private ImageView appIconIV;


        public ViewHodler(View itemView) {
            super(itemView);
            RecyclerView.LayoutParams lp= (RecyclerView.LayoutParams) itemView.getLayoutParams();
            lp.width=viewSize;
            lp.height=viewSize;
            int paddingSize=viewSize/10;
            lp.topMargin=paddingSize;
            lp.bottomMargin=paddingSize;
            lp.leftMargin=paddingSize;
            lp.rightMargin=paddingSize;
            itemView.setLayoutParams(lp);

//
//            itemView.setPadding(paddingSize,paddingSize,paddingSize,paddingSize);
            appIconIV=itemView.findViewById(R.id.iv_icon);
        }
    }




}