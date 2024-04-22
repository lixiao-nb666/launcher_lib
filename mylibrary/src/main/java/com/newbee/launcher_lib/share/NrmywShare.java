package com.newbee.launcher_lib.share;



import android.util.Log;

import com.newbee.data_sava_lib.share.BaseShare;
import com.newbee.launcher_lib.app.NrMywApp;


public class NrmywShare extends BaseShare {
    private static NrmywShare nrmywShare;

    private NrmywShare() {
        super(NrMywApp.getContext());
    }

    public static NrmywShare getInstance(){
        if(null==nrmywShare){
            synchronized (NrmywShare.class){
                if(null==nrmywShare){
                    nrmywShare=new NrmywShare();
                }
            }
        }
        return nrmywShare;
    }

    private final String showIndexShareStr= "showIndex";
    public void putShowIndex(int index){
        putString(showIndexShareStr,index+"");
        Log.i("kankandaodishiji:","kankandaodishiji111:"+index);
    }

    public int getShowIndex(){
        try {
            String showIndexStr=getString(showIndexShareStr,"-1");
            int showIndex=Integer.valueOf(showIndexStr);
            return showIndex;
        }catch (Exception e){
            Log.i("kankandaodishiji:","kankandaodishiji222:"+e.toString());
            return -1;
        }

    }


}
