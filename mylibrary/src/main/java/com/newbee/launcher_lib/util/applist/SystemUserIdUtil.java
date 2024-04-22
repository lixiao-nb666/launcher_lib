package com.newbee.launcher_lib.util.applist;

import android.annotation.SuppressLint;



import java.lang.reflect.Method;

public class SystemUserIdUtil {


    public static int getCurrentUserId() {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN_MR1) {
            try {
                @SuppressLint("DiscouragedPrivateApi") final Method m = android.os.UserHandle.class.getDeclaredMethod("myUserId");
                m.setAccessible(true);
                final Object o = m.invoke(null);
                if (o instanceof Integer) {
                    return (Integer) o;
                }
            } catch (Exception ignored) {
            }
        }
        return -1;
    }


}
