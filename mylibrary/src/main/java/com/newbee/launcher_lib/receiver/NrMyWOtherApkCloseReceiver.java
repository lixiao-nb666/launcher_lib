package com.newbee.launcher_lib.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.newbee.launcher_lib.app.NrMywOtherAppUtil;


/**
 * Created by xiefuning on 2017/5/10.
 * about:
 */

public class NrMyWOtherApkCloseReceiver extends BroadcastReceiver {

    public static final String keyListenCloseAction = "com.newbee.systemkeylisten.service.close";
    public static final String fileShareCloseAction = "com.newbee.andserver.service.close";

    @Override
    public void onReceive(Context context, Intent intent) {
        switch (intent.getAction()){
            case keyListenCloseAction:
                NrMywOtherAppUtil.robMoneyOpenServiceSetting(context);
                break;
            case fileShareCloseAction:
                NrMywOtherAppUtil.startFileShareService(context);
                break;
        }
    }

}
