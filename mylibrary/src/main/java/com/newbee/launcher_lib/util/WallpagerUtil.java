package com.newbee.launcher_lib.util;

import android.app.WallpaperManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.provider.MediaStore;

import com.newbee.system_applist_lib.systemapp.StartOtherApkUtil;

public class WallpagerUtil {

    public static void setPage(Context context){
//        Intent chooseIntent = new Intent(Intent.ACTION_SET_WALLPAPER);
//// 启动系统选择应用
//        Intent intent = new Intent(Intent.ACTION_CHOOSER);
//        intent.putExtra(Intent.EXTRA_INTENT, chooseIntent);
//        intent.putExtra(Intent.EXTRA_TITLE, "选择壁纸");
//        context.startActivity(intent);
        String pckName="com.android.wallpaperpicker";
        if(StartOtherApkUtil.getInstance().checkAppIsInstalled(context,pckName)){
            String actName="com.android.wallpaperpicker.WallpaperPickerActivity";
                MyWStartUtil.toOtherApk(context,pckName,actName);
        }else {
//            Intent chooseIntent = new Intent(Intent.ACTION_SET_WALLPAPER);
//            // 启动系统选择应用
//            Intent intent = new Intent(Intent.ACTION_CHOOSER);
//            intent.putExtra(Intent.EXTRA_INTENT, chooseIntent);
//            intent.putExtra(Intent.EXTRA_TITLE, "选择动态壁纸");
//            context.startActivity(intent);
        }
    }


//    public void setWallPagerToBg(Context mContext){
//        // 获取壁纸管理器
//
//        WallpaperManager wallpaperManager = WallpaperManager
//
//             .getInstance(mContext);
//
//          // 获取当前壁纸
//
//               Drawable wallpaperDrawable = wallpaperManager.getDrawable();
//
//                     // 将Drawable,转成Bitmap
//
//                   Bitmap bm = ((BitmapDrawable) wallpaperDrawable).getBitmap();
//
//
//
//                   // 需要详细说明一下，mScreenCount、getCurrentWorkspaceScreen()、mScreenWidth、mScreenHeight分别
//
//                  //对应于Launcher中的桌面屏幕总数、当前屏幕下标、屏幕宽度、屏幕高度.等下拿Demo的哥们稍微要注意一下
//
//                  float step = 0;
//
//                  // 计算出屏幕的偏移量
//
//                    step = (bm.getWidth() - LauncherPreferenceModel.mScreenWidth)
//
//                            / (LauncherPreferenceModel.mScreenCount - 1);
//
//                  // 截取相应屏幕的Bitmap
//
//                   Bitmap pbm = Bitmap.createBitmap(bm, (int) (mLauncher
//
//                         .getCurrentWorkspaceScreen() * step), 0,
//
//                                  (int) (LauncherPreferenceModel.mScreenWidth),
//
//                                  (int) (LauncherPreferenceModel.mScreenHeight));
//
//                  // 设置 背景
//
//                   layout.setBackgroundDrawable(new BitmapDrawable(pbm));
//    }



}
