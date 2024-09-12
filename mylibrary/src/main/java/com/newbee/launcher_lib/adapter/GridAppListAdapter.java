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
import com.newbee.launcher_lib.view.icon.ShowIconByAppView;
import com.newbee.launcher_lib.view.icon.ShowIconViewItemClick;
import com.newbee.system_applist_lib.systemapp.bean.SystemAppInfoBean;

import java.util.ArrayList;
import java.util.List;

public class GridAppListAdapter extends RecyclerView.Adapter {
    private final String tag = getClass().getName() + ">>>>";
    private List<SystemAppInfoBean> apps;
    private LayoutInflater layoutInflater;
    private ShowIconViewItemClick itemClick;

    public GridAppListAdapter(Context context,ShowIconViewItemClick itemClick) {
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
        viewHodler.showIconByAppView.setData(itemClick,app);

    }

    @Override
    public int getItemCount() {
        if (null == apps) {
            return 0;
        }
        return apps.size();
    }

    class ViewHodler extends RecyclerView.ViewHolder {


        private ShowIconByAppView showIconByAppView;

        public ViewHodler(View itemView) {
            super(itemView);
            showIconByAppView=itemView.findViewById(R.id.sibav);
            showIconByAppView.init(viewSize);
        }
    }




}