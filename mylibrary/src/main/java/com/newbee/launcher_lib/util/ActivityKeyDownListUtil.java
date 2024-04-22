package com.newbee.launcher_lib.util;

import android.view.KeyEvent;

import java.util.ArrayList;
import java.util.List;

public class ActivityKeyDownListUtil {

    //中间键
    public static List<Integer> queOk1() {
        List<Integer> needKeyCodes = new ArrayList<>();
        needKeyCodes.add(KeyEvent.KEYCODE_DPAD_CENTER);
        return needKeyCodes;
    }

    //确定按钮
    public static List<Integer> queOk2() {
        List<Integer> needKeyCodes = new ArrayList<>();
        needKeyCodes.add(KeyEvent.KEYCODE_ENTER);
        return needKeyCodes;
    }

    //往左
    public static List<Integer> toLeftList() {
        List<Integer> needKeyCodes = new ArrayList<>();
        needKeyCodes.add(KeyEvent.KEYCODE_DPAD_LEFT);
        return needKeyCodes;
    }

    public static List<Integer> toUpList() {
        List<Integer> needKeyCodes = new ArrayList<>();
        needKeyCodes.add(KeyEvent.KEYCODE_DPAD_UP);

        return needKeyCodes;
    }

    //往右
    public static List<Integer> toRightList() {
        List<Integer> needKeyCodes = new ArrayList<>();
        needKeyCodes.add(KeyEvent.KEYCODE_DPAD_RIGHT);
        return needKeyCodes;
    }

    public static List<Integer> toDownList() {
        List<Integer> needKeyCodes = new ArrayList<>();
        needKeyCodes.add(KeyEvent.KEYCODE_DPAD_DOWN);
        return needKeyCodes;
    }

    //返回
    public static List<Integer> toBackList() {
        List<Integer> needKeyCodes = new ArrayList<>();
        needKeyCodes.add(KeyEvent.KEYCODE_BACK);
        return needKeyCodes;
    }
}
