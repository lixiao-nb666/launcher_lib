package com.newbee.launcher_lib.util.image;

import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Shader.TileMode;
import android.view.View;
 
/**
 * 创建图片倒影的方法
 * 
 * @author 
 * */
public class ImageReflect {
    private static int reflectImageHeight = 90;//倒影的高度
 
    //转化为bitmap
    public static Bitmap convertViewToBitmap(View paramView) {
        paramView.measure(View.MeasureSpec.makeMeasureSpec(0, 0), View.MeasureSpec.makeMeasureSpec(0, 0));
        paramView.layout(0, 0, paramView.getMeasuredWidth(), paramView.getMeasuredHeight());
        paramView.buildDrawingCache();
        return paramView.getDrawingCache();
    }
 
    public static Bitmap createCutReflectedImage(Bitmap paramBitmap, int paramInt) {
        int i = paramBitmap.getWidth();
        int j = paramBitmap.getHeight();
        Bitmap localBitmap2 = null;
        if (j <= paramInt + reflectImageHeight) {
            localBitmap2 = null;
        } else {
            Matrix localMatrix = new Matrix();
            localMatrix.preScale(1.0F, -1.0F);
            // System.out.println(j - reflectImageHeight -
            // paramInt);
            Bitmap localBitmap1 = Bitmap.createBitmap(paramBitmap, 0, j - reflectImageHeight - paramInt, i, reflectImageHeight, localMatrix, true);
            localBitmap2 = Bitmap.createBitmap(i, reflectImageHeight, Bitmap.Config.ARGB_8888);
            Canvas localCanvas = new Canvas(localBitmap2);
            localCanvas.drawBitmap(localBitmap1, 0.0F, 0.0F, null);
            LinearGradient localLinearGradient = new LinearGradient(0.0F, 0.0F, 0.0F, localBitmap2.getHeight(), -2130706433, 16777215, TileMode.CLAMP);
            Paint localPaint = new Paint();
            localPaint.setShader(localLinearGradient);
            localPaint.setXfermode(new PorterDuffXfermode(Mode.DST_IN));
            localCanvas.drawRect(0.0F, 0.0F, i, localBitmap2.getHeight(), localPaint);
            if (!localBitmap1.isRecycled())
                localBitmap1.recycle();
            System.gc();
        }
        return localBitmap2;
    }
 
    public static Bitmap createReflectedImage(Bitmap paramBitmap, int paramInt) {
        int i = paramBitmap.getWidth();
        int j = paramBitmap.getHeight();
        Bitmap localBitmap2;
        if (j <= paramInt) {
            localBitmap2 = null;
        } else {
            Matrix localMatrix = new Matrix();
            localMatrix.preScale(1.0F, -1.0F);
            Bitmap localBitmap1 = Bitmap.createBitmap(paramBitmap, 0, j - paramInt, i, paramInt, localMatrix, true);
            localBitmap2 = Bitmap.createBitmap(i, paramInt, Bitmap.Config.ARGB_8888);
            Canvas localCanvas = new Canvas(localBitmap2);
            localCanvas.drawBitmap(localBitmap1, 0.0F, 0.0F, null);
            LinearGradient localLinearGradient = new LinearGradient(0.0F, 0.0F, 0.0F, localBitmap2.getHeight(), -2130706433, 16777215, TileMode.CLAMP);
            Paint localPaint = new Paint();
            localPaint.setShader(localLinearGradient);
            localPaint.setXfermode(new PorterDuffXfermode(Mode.DST_IN));
            localCanvas.drawRect(0.0F, 0.0F, i, localBitmap2.getHeight(), localPaint);
        }
        return localBitmap2;
    }
    
    public static Bitmap createReflectedImage(Bitmap originalImage) {
        final int reflectionGap = 4;
        int width = originalImage.getWidth();
        int height = originalImage.getHeight();
        Matrix matrix = new Matrix();
        matrix.preScale(1, -1);
        Bitmap reflectionImage = Bitmap.createBitmap(originalImage, 0,
                height / 2, width, height / 2, matrix, false);
        Bitmap bitmapWithReflection = Bitmap.createBitmap(width,
                (height + height / 2), Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmapWithReflection);
        canvas.drawBitmap(originalImage, 0, 0, null);
        Paint defaultPaint = new Paint();
        canvas.drawRect(0, height, width, height + reflectionGap, defaultPaint);
        canvas.drawBitmap(reflectionImage, 0, height + reflectionGap, null);
        Paint paint = new Paint();
        LinearGradient shader = new LinearGradient(0,
                originalImage.getHeight(), 0, bitmapWithReflection.getHeight()
                        + reflectionGap, 0x70ffffff, 0x00ffffff,
                TileMode.MIRROR);
        paint.setShader(shader);
        paint.setXfermode(new PorterDuffXfermode(Mode.DST_IN));
        canvas.drawRect(0, height, width, bitmapWithReflection.getHeight()
                + reflectionGap, paint);
        return bitmapWithReflection;
    }
}