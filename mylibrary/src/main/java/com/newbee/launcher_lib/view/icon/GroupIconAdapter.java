package com.newbee.launcher_lib.view.icon;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.newbee.launcher_lib.R;
import com.newbee.launcher_lib.config.LauncherConfig;
import com.newbee.launcher_lib.util.image.GetSystemIconUtil;
import com.newbee.system_applist_lib.systemapp.bean.SystemAppInfoBean;

import java.util.ArrayList;
import java.util.List;

public class GroupIconAdapter extends RecyclerView.Adapter {
    private final String tag = getClass().getName() + ">>>>";
    private List<SystemAppInfoBean> apps;
    private LayoutInflater layoutInflater;
    private ItemClick itemClick;

    public GroupIconAdapter(Context context, ItemClick itemClick) {
        layoutInflater = LayoutInflater.from(context);
        this.itemClick = itemClick;
        this.apps = new ArrayList<>();
    }


    public void setData(List<SystemAppInfoBean> apps) {
        if (apps == null) {
            this.apps = new ArrayList<>();
        }else {
            this.apps = apps;
        }
        notifyDataSetChanged();
    }

    public void setItemSelectData(int index) {
        if (null == apps || apps.size() == 0||index<0||index>=apps.size()) {
            return;
        }
        SystemAppInfoBean systemAppInfoBean = apps.get(index);
        itemClick.nowSelect(systemAppInfoBean);
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
        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemClick.nowSelect(app);
            }
        };
        viewHodler.appIconIV.setOnClickListener(onClickListener);
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


        public ViewHodler(View itemView) {
            super(itemView);
            appIconIV=itemView.findViewById(R.id.iv_icon);
        }
    }

    public interface ItemClick {
        void nowSelect(SystemAppInfoBean systemAppInfoBean);
    }


}