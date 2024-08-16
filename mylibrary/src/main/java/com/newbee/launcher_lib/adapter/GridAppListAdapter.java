package com.newbee.launcher_lib.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.newbee.launcher_lib.R;
import com.newbee.launcher_lib.util.image.GetSystemIconUtil;
import com.newbee.system_applist_lib.systemapp.bean.SystemAppInfoBean;

import java.util.ArrayList;
import java.util.List;

public class GridAppListAdapter extends RecyclerView.Adapter {
    private final String tag = getClass().getName() + ">>>>";
    private List<SystemAppInfoBean> apps;
    private LayoutInflater layoutInflater;
    private ItemClick itemClick;

    public GridAppListAdapter(Context context, ItemClick itemClick) {
        layoutInflater = LayoutInflater.from(context);
        this.itemClick = itemClick;


    }

    private int viewSize;
    public void setData(List<SystemAppInfoBean> apps,int viewSize){
        this.viewSize=viewSize;
        this.apps = apps;
        notifyDataSetChanged();
    }



    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = layoutInflater.inflate(R.layout.adapter_grid_app_list, parent, false);
        ViewHodler viewHodler = new ViewHodler(itemView);
        return viewHodler;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final ViewHodler viewHodler = (ViewHodler) holder;
        final SystemAppInfoBean app = apps.get(position);
        GetSystemIconUtil.getInstance().setAppIconAndName(viewHodler.appIconIV,viewHodler.appNameTV,app);
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
        return apps.size();
    }

    class ViewHodler extends RecyclerView.ViewHolder {


        private ImageView appIconIV;
        private TextView appNameTV;

        public ViewHodler(View itemView) {
            super(itemView);
            appIconIV=itemView.findViewById(R.id.iv_icon);
            appNameTV=itemView.findViewById(R.id.tv_app_name);
            LinearLayout.LayoutParams lp= (LinearLayout.LayoutParams) appIconIV.getLayoutParams();
            lp.leftMargin=viewSize/3;
            lp.rightMargin=viewSize/3;
            lp.topMargin=viewSize/2;
            lp.bottomMargin= (int) (viewSize/6.5f);
            lp.width=viewSize;
            lp.height=viewSize;
            int paddingSize=viewSize/14;
            appIconIV.setPadding(paddingSize,paddingSize,paddingSize,paddingSize);
            appIconIV.setLayoutParams(lp);
            appNameTV.setTextSize(viewSize/7.4f);
        }
    }

    public interface ItemClick {
        void nowSelect(SystemAppInfoBean systemAppInfoBean);
    }


}