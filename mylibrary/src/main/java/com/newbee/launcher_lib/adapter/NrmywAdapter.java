package com.newbee.launcher_lib.adapter;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.DisplayMetrics;
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

public class NrmywAdapter extends RecyclerView.Adapter {
    private final String tag = getClass().getName() + ">>>>";
    private List<SystemAppInfoBean> apps;
    private LayoutInflater layoutInflater;
    private ItemClick itemClick;

    private int w, h;
    private int llNeedW;
    private int iconW;
    private int textMTop;
    private int textSize;

    private boolean needReflect;

    public NrmywAdapter(Context context, ItemClick itemClick, int w, int h, boolean needReflect) {
        layoutInflater = LayoutInflater.from(context);
        this.itemClick = itemClick;
        this.apps = new ArrayList<>();
        this.w = w;
        this.h = h;
        llNeedW = (int) (w / LauncherConfig.Icon_Distance_Base);
        int iconW1 = w / 5;
        int iconW2 = w / 3;
        if (iconW1 < iconW2) {
            iconW = iconW1;
        } else {
            iconW = iconW2;
        }
        textMTop = iconW / 25;
        textSize = iconW / 10;

        this.needReflect = needReflect;

    }


    public void setData(List<SystemAppInfoBean> apps) {
        if (apps == null) {
            this.apps = new ArrayList<>();
            return;
        }
        this.apps = apps;
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
        View itemView = layoutInflater.inflate(R.layout.adapter_lv_apps_layout, parent, false);
        ViewHodler viewHodler = new ViewHodler(itemView);

        return viewHodler;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        final ViewHodler viewHodler = (ViewHodler) holder;
        final SystemAppInfoBean app = apps.get(position);
        GetSystemIconUtil.getInstance().setAppIconAndName(viewHodler.appIconIV,viewHodler.appNameTV,app.getName(),app.getPakeageName());
        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemClick.nowSelect(app);
            }
        };
        viewHodler.iconLL.setOnClickListener(onClickListener);
        viewHodler.appNameTV.setOnClickListener(onClickListener);
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

        private LinearLayout iconLL;
        private ImageView appIconIV;
        private TextView appNameTV;

        public ViewHodler(View itemView) {
            super(itemView);
            iconLL = itemView.findViewById(R.id.ll_app);
            appIconIV = new ImageView(iconLL.getContext());
            appIconIV.setBackground(null);
            LinearLayout.LayoutParams imageLP = new LinearLayout.LayoutParams(iconW, iconW);
            appIconIV.setLayoutParams(imageLP);
            appIconIV.setScaleType(ImageView.ScaleType.FIT_CENTER);
            iconLL.addView(appIconIV);
            appNameTV = new TextView(iconLL.getContext());
            LinearLayout.LayoutParams textLP = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            textLP.gravity = Gravity.CENTER;
            textLP.topMargin = textMTop;
            appNameTV.setLines(1);
            appNameTV.setTextColor(Color.WHITE);
            appNameTV.setTextSize(textSize);
            appNameTV.setLayoutParams(textLP);
            iconLL.addView(appNameTV);
        }
    }

    public interface ItemClick {
        void nowSelect(SystemAppInfoBean systemAppInfoBean);
    }


}