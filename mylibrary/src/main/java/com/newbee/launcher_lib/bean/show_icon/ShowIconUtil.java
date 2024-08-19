package com.newbee.launcher_lib.bean.show_icon;

import android.text.TextUtils;
import android.util.Log;

import com.newbee.system_applist_lib.systemapp.bean.ResultSystemAppInfoBean;
import com.newbee.system_applist_lib.systemapp.bean.SystemAppInfoBean;

import java.util.ArrayList;
import java.util.List;

public class ShowIconUtil {



    public static List<ShowIconBean> getNeedData(ResultSystemAppInfoBean initList){
        List<ShowIconBean> appList=new ArrayList<>();
        if(null==initList||null==initList.getAppList()){
        }else {
            for(SystemAppInfoBean systemAppInfoBean:initList.getAppList()){
                ShowIconBean showIconBean=null;
                String groupStr= ShowIconGroupUtil.getInstance().getGroupName(systemAppInfoBean.getPakeageName());
                Log.i("kankanyichang","kankanyichang44--"+groupStr);
                if(TextUtils.isEmpty(groupStr)){
                    showIconBean=new ShowIconBean();
                    showIconBean.setIconType(ShowIconType.Icon);
                    showIconBean.setSystemAppInfoBean(systemAppInfoBean);
                    showIconBean.setIconName(systemAppInfoBean.getName());
                    showIconBean.setIndex(systemAppInfoBean.getIndex());
                }else {
                    showIconBean=getNeedGroupShowIconBean(appList,groupStr,systemAppInfoBean);
                }
                if(null!=showIconBean){
                    appList.add(showIconBean);
                }
            }
        }
        return appList;
    }

    private static ShowIconBean getNeedGroupShowIconBean(List<ShowIconBean> list,String groupStr,SystemAppInfoBean systemAppInfoBean){
        for(ShowIconBean showIconBean:list){
            if(null!=showIconBean&&null!=showIconBean.getIconType()&&showIconBean.getIconType()==ShowIconType.Group&&showIconBean.getIconName().equals(groupStr)){
                ResultSystemAppInfoBean resultSystemAppInfoBean=showIconBean.getResultSystemAppInfoBean();
                if(null==resultSystemAppInfoBean){
                    resultSystemAppInfoBean=new ResultSystemAppInfoBean();
                }
                resultSystemAppInfoBean.add(systemAppInfoBean);
                showIconBean.setResultSystemAppInfoBean(resultSystemAppInfoBean);
                return null;
            }
        }
        ShowIconBean showIconBean=new ShowIconBean();
        showIconBean.setIconType(ShowIconType.Group);
        showIconBean.setIconName(groupStr);
        showIconBean.setIndex(systemAppInfoBean.getIndex());
        ResultSystemAppInfoBean resultSystemAppInfoBean=new ResultSystemAppInfoBean();
        resultSystemAppInfoBean.add(systemAppInfoBean);
        showIconBean.setResultSystemAppInfoBean(resultSystemAppInfoBean);
        return showIconBean;
    }
}
