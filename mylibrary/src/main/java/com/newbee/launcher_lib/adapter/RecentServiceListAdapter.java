package com.newbee.launcher_lib.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;


import com.newbee.launcher_lib.R;
import com.newbee.launcher_lib.bean.RecentServiceShowBean;
import com.newbee.launcher_lib.util.image.GetSystemIconUtil;

import java.util.ArrayList;
import java.util.List;



public class RecentServiceListAdapter extends RecyclerView.Adapter {
    private final String tag = getClass().getName() + ">>>>";
    private List<RecentServiceShowBean> serviceShowBeanList;
    private LayoutInflater layoutInflater;
    private ItemClick itemClick;
    private Button clearAllBT;



    public RecentServiceListAdapter(Context context, ItemClick itemClick, int  showItem, Button clearAllBT) {
        layoutInflater = LayoutInflater.from(context);
        this.itemClick = itemClick;
        this.serviceShowBeanList = new ArrayList<>();
        this.nowShowItem=showItem;
        this.clearAllBT=clearAllBT;
    }


    public void setData(List<RecentServiceShowBean> serviceShowBeanList) {
        if (serviceShowBeanList == null) {
            this.serviceShowBeanList = new ArrayList<>();
            return;
        }
        this.serviceShowBeanList =serviceShowBeanList;
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
        if (null == serviceShowBeanList || serviceShowBeanList.size() == 0||index<0||index>=serviceShowBeanList.size()) {
            return;
        }
        RecentServiceShowBean recentServiceShowBean = serviceShowBeanList.get(index);
        itemClick.nowSelect(recentServiceShowBean);
    }

    public RecentServiceShowBean getItemData(int index) {
        if (null == serviceShowBeanList || serviceShowBeanList.size() == 0||index<0||index>=serviceShowBeanList.size()) {
            return null;
        }
        RecentServiceShowBean recentAppShowBean = serviceShowBeanList.get(index);
        return recentAppShowBean;
    }

    public List<RecentServiceShowBean> getList(){
        return serviceShowBeanList;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = layoutInflater.inflate(R.layout.adapter_recent_services_layout, parent, false);
        ViewHodler viewHodler = new ViewHodler(itemView);

        return viewHodler;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final ViewHodler viewHodler = (ViewHodler) holder;
        final RecentServiceShowBean serviceShowBean=serviceShowBeanList.get(position);
        GetSystemIconUtil.getInstance().setAppIconAndName(viewHodler.appIconIV,viewHodler.showTV,serviceShowBean.getAppName(),serviceShowBean.getPckName());
        viewHodler.showTV.append("\n"+serviceShowBean.getPckName());
        viewHodler.impTV.setText("pid : "+serviceShowBean.getPid());
        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(v.getId()==R.id.iv_clear){
                    itemClick.needClear(serviceShowBean,viewHodler.bgLL);
                }else {
                    itemClick.nowSelect(serviceShowBean);
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
        if (null ==serviceShowBeanList) {
            return 0;
        }
        return serviceShowBeanList.size();
    }

    class ViewHodler extends RecyclerView.ViewHolder {

        private LinearLayout bgLL;
        private ImageView appIconIV;
        private TextView showTV;
        private TextView impTV;
        private ImageView clearIV;

        public ViewHodler(View itemView) {
            super(itemView);
            bgLL=itemView.findViewById(R.id.ll_item);
            appIconIV=  itemView.findViewById(R.id.iv_icon);
            showTV= itemView.findViewById(R.id.tv_show);
            impTV=itemView.findViewById(R.id.tv_imp);
            clearIV=itemView.findViewById(R.id.iv_clear);
        }
    }

    public interface ItemClick {
        void nowSelect( RecentServiceShowBean recentServiceShowBean);

        void needClear( RecentServiceShowBean recentServiceShowBean, LinearLayout selectView);
    }


}