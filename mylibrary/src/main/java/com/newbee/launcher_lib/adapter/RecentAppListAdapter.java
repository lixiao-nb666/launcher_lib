package com.newbee.launcher_lib.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;

import com.newbee.launcher_lib.R;
import com.newbee.launcher_lib.bean.RecentAppShowBean;
import com.newbee.launcher_lib.util.image.GetSystemIconUtil;

import java.util.ArrayList;
import java.util.List;

public class RecentAppListAdapter extends RecyclerView.Adapter {
    private final String tag = getClass().getName() + ">>>>";
    private List<RecentAppShowBean> apps;
    private LayoutInflater layoutInflater;
    private ItemClick itemClick;
    private Button clearAllBT;



    public RecentAppListAdapter(Context context, ItemClick itemClick,int  showItem,Button clearAllBT) {
        layoutInflater = LayoutInflater.from(context);
        this.itemClick = itemClick;
        this.apps = new ArrayList<>();
        this.nowShowItem=showItem;
        this.clearAllBT=clearAllBT;
    }


    public void setData(List<RecentAppShowBean> apps) {
        if (apps == null) {
            this.apps = new ArrayList<>();
            return;
        }
        this.apps = apps;
        notifyDataSetChanged();
    }

    private int nowShowItem;
    private LinearLayout lastShowView;
    public void setShowItem(int showItem){
        if(null!=lastShowView){
            lastShowView.setBackgroundResource(R.color.black);
        }
        if(clearAllBT.isPressed()){
            clearAllBT.setPressed(false);
        }
        if(getItemCount()<=0){
            return;
        }
        if(showItem>=0&&showItem<getItemCount()){
            this.nowShowItem=showItem;
            notifyItemChanged(nowShowItem);
        }else if(showItem>=getItemCount()){
            clearAllBT.setPressed(true);
            Log.i("kankanzenmehuishi","zenmele:1111111");
        }
    }

    public void clearShowIndex(){
        nowShowItem=-1;
    }

    public LinearLayout getSelectView(){
        return lastShowView;
    }

    public void setItemSelectData(int index) {
        if (null == apps || apps.size() == 0||index<0||index>=apps.size()) {
            return;
        }
        RecentAppShowBean recentAppShowBean = apps.get(index);
        itemClick.nowSelect(recentAppShowBean);
    }

    public RecentAppShowBean getItemData(int index) {
        if (null == apps || apps.size() == 0||index<0||index>=apps.size()) {
            return null;
        }
        RecentAppShowBean recentAppShowBean = apps.get(index);
        return recentAppShowBean;
    }

    public List<RecentAppShowBean> getList(){
        return apps;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = layoutInflater.inflate(R.layout.adapter_recent_apps_layout, parent, false);
        ViewHodler viewHodler = new ViewHodler(itemView);

        return viewHodler;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        final ViewHodler viewHodler = (ViewHodler) holder;
        final RecentAppShowBean app = apps.get(position);
        GetSystemIconUtil.getInstance().setAppIconAndName(viewHodler.appIconIV,viewHodler.showTV,app.getAppName(),app.getPckName());
        viewHodler.showTV.append("\n"+app.getNeedStartCls());
        viewHodler.idTV.setText("id : "+app.getTaskId());
        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(v.getId()==R.id.iv_clear){
                    itemClick.needClear(app,viewHodler.bgLL);
                }else {
                    itemClick.nowSelect(app);
                }
            }
        };
        viewHodler.itemView.setOnClickListener(onClickListener);
        viewHodler.appIconIV.setOnClickListener(onClickListener);
        viewHodler.clearIV.setOnClickListener(onClickListener);
        if(nowShowItem==position){
            viewHodler.bgLL.setBackgroundResource(R.drawable.recent_app_list_item_select_bj);
            lastShowView=viewHodler.bgLL;
        }else {
            viewHodler.bgLL.setBackgroundResource(R.color.black);
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

        private LinearLayout bgLL;
        private ImageView appIconIV;
        private TextView showTV;
        private TextView idTV;
        private ImageView clearIV;

        public ViewHodler(View itemView) {
            super(itemView);
            bgLL=itemView.findViewById(R.id.ll_item);
            appIconIV=  itemView.findViewById(R.id.iv_icon);
            showTV= itemView.findViewById(R.id.tv_show);
            idTV=itemView.findViewById(R.id.tv_id);
            clearIV=itemView.findViewById(R.id.iv_clear);
        }
    }

    public interface ItemClick {
        void nowSelect(RecentAppShowBean recentAppShowBean);

        void needClear(RecentAppShowBean recentAppShowBean, LinearLayout selectView);
    }


}