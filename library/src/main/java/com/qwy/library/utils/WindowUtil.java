package com.qwy.library.utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Build;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;

import androidx.annotation.ColorInt;
import androidx.core.graphics.ColorUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * 状态栏工具
 */
public class WindowUtil {
    /**
     * 状态栏背景透明(携带时间显示,手动修改字体颜色)
     *
     * @param activity
     * @param isBlack  状态字体是否黑色
     */
    public static void hideWindowStatusBar(Activity activity, boolean isBlack) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            activity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            setStatusTextColor(activity, isBlack);
            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            activity.getWindow().setStatusBarColor(Color.TRANSPARENT);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
    }

    /**
     * 状态栏背景透明(字体颜色自动转化)
     *
     * @param bitmap 根据图片主颜色转化字体颜色
     * @return
     */
    public static void hideStatusBarAuto(Activity activity, Bitmap bitmap) {
        int bitmapInt = getPicturePixel(activity, bitmap);//得到图片主int颜色
        Log.i("log", "hideStatusBarAuto: 图片白色：" + isLightColor(bitmapInt));
        hideWindowStatusBar(activity, isLightColor(bitmapInt));
    }

    /**
     * 状态栏背景透明(字体颜色自动转化)
     *
     * @param activity
     * @param image_int 根据图片主颜色转化字体颜色
     */
    public static void hideStatusBarAuto(Activity activity, int image_int) {
        Bitmap bitmap = BitmapFactory.decodeResource(activity.getResources(), image_int);
        hideStatusBarAuto(activity, bitmap);
    }

    /**
     * 得到状态栏的高度(px)
     *
     * @param context
     * @return
     */
    public static int getSystemStatusBarHeight(Context context) {
        int id = context.getResources().getIdentifier(
                "status_bar_height", "dimen", "android");
        return id > 0 ? context.getResources().getDimensionPixelSize(id) : id;
    }

    /**
     * Android 6.0 以上设置状态栏颜色（设置了颜色，状态透明无效）
     */
    public static void setStatusBar(Activity activity, @ColorInt int color) {
//        Log.i("log", "setStatusBar: 像素颜色：" + color);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            // 设置状态栏底色颜色
            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            activity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            activity.getWindow().setStatusBarColor(color);

            // 如果亮色，设置状态栏文字为黑色
            setStatusTextColor(activity, isLightColor(color));
        }

    }

    /**
     * 设置状态栏中的字体颜色
     *
     * @param activity
     * @param isBlack
     */
    public static void setStatusTextColor(Activity activity, boolean isBlack) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            //设置状态栏文字为黑色
            if (isBlack) {
                activity.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            } else {
                activity.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_VISIBLE);
            }
        }
    }

    /**
     * 判断颜色是不是亮色
     * 白色为亮色,黑色暗色
     *
     * @param color
     */
    private static boolean isLightColor(@ColorInt int color) {
        return ColorUtils.calculateLuminance(color) >= 0.5;
    }

    /**
     * 获取StatusBar颜色，默认白色
     *
     * @return
     */
    protected @ColorInt
    int getStatusBarColor() {
        return Color.WHITE;
    }

    /**
     * 获得图片的最多像素(没有返回-1)
     * 1.根据状态栏高度获取
     * 2.返回类型-9260322（像素 int）
     *
     * @param bitmap
     */
    private static int getPicturePixel(Context context, Bitmap bitmap) {

        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        //如果图片比状态栏高,去状态栏的图片高度,否则去图片的高度
        height = height > getSystemStatusBarHeight(context) ? getSystemStatusBarHeight(context) : height;
        // 保存所有的像素的数组，图片宽×高
        int[] pixels = new int[width * height];
        /**
         * pixels   保存像素的数组
         * offset   x和y方向上的偏移量
         * stride   横向多少像素换行
         * x        横向开始像素点
         * y        纵向开始像素点
         * width    截图的宽度像素点
         * height   截取的高度像素点
         */
        bitmap.getPixels(pixels, 0, width, 0, 0, width, height);

        ArrayList<Integer> rgb = new ArrayList<>();
        int filterInt = pixels.length / 10000;//采样1万个
//        Log.i("log", "getPicturePixel: 图片像素点大小：" + pixels.length);
        for (int i = 0; i < pixels.length; i++) {
            //过滤10倍的数据
            if (i % filterInt == 0) {
                int clr = pixels[i];
                int red = (clr & 0x00ff0000) >> 16; // 取高两位
                int green = (clr & 0x0000ff00) >> 8; // 取中两位
                int blue = clr & 0x000000ff; // 取低两位
//            Log.d("tag", "r=" + red + ",g=" + green + ",b=" + blue);
                int color = Color.rgb(red, green, blue);
                //除去白色和黑色
/*            if (color != Color.WHITE && color != Color.BLACK) {
                rgb.add(color);
            }*/
                rgb.add(color);
            }
        }

//        Log.i("log", "getPicturePixel: 图片像素点大小后：" + rgb.size());


        //计数相同颜色数量并保存
        HashMap<Integer, Integer> color2 = new HashMap<>();
        for (Integer color : rgb) {
            if (color2.containsKey(color)) {
                Integer integer = color2.get(color);
                integer++;
                color2.remove(color);
                color2.put(color, integer);

            } else {
                color2.put(color, 1);
            }
        }
        //挑选数量最多的颜色
        Iterator iter = color2.entrySet().iterator();
        int count = 0;
        int color = 0;
        while (iter.hasNext()) {
            Map.Entry entry = (Map.Entry) iter.next();
            int value = (int) entry.getValue();
            if (count < value) {
                count = value;
                color = (int) entry.getKey();
            }

        }
        if (color == 0) {
            return -1;
        }

        return color;
    }

    /**
     * dp转px
     * @param context
     * @param dpValue
     * @return
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 得到屏幕宽
     * @param context
     * @return
     */
    public static int getWidth(Activity context) {
        DisplayMetrics displaymetrics = new DisplayMetrics();
        context.getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        int width = displaymetrics.widthPixels;
        return width;
    }

    /**
     * 得到屏幕高
     * @param context
     * @return
     */
    public static int getHeight(Activity context) {
        DisplayMetrics displaymetrics = new DisplayMetrics();
        context.getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        int height = displaymetrics.heightPixels;
        return height;
    }
}
