package com.qwy.library.toast;

import android.graphics.Bitmap;
import android.text.Html;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.qwy.library.utils.TypeChange;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/**
 * 1.不能Context使用来跳转页面
 * 2.全局统一的Toast使用该类中的单例方法，一旦使用了一种以上的方法，需要在不常使用的方法调用后调用resetToast()方法，重置Toast位置等
 */

public class ToastUtils {
    private static Toast toast;
    private static List<Toast> toastList = new ArrayList<>();

    public ToastUtils() {
        throw new UnsupportedOperationException("not init ToastUtils");
    }
    /**
     * 显示文本内容
     *
     * @param content 显示内容
     */
    public static void show(String content) {
        if (toast == null) {
            toast = Toast.makeText(AppContext.getContext(), "", Toast.LENGTH_SHORT);
        }
        toastList.add(toast);
        toast.setText(content);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.show();
    }
    /**
     * 显示int内容
     *
     * @param content 显示内容
     */
    public static void show(Object content) {
        if (toast == null) {
            toast = Toast.makeText(AppContext.getContext(), "", Toast.LENGTH_SHORT);
        }
        toastList.add(toast);
        toast.setText(TypeChange.toString(content));
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.show();
    }
    /**
     * 显示文本内容:长时
     *
     * @param content 显示内容
     */
    public static void showLong(String content) {
        if (toast == null) {
            toast = Toast.makeText(AppContext.getContext(), "", Toast.LENGTH_SHORT);
        }
        toastList.add(toast);
        toast.setText(content);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.show();
    }
    /**
     * 显示文本内容：长时
     *
     * @param content 显示内容
     */
    public static void showLong(Object content) {
        if (toast == null) {
            toast = Toast.makeText(AppContext.getContext(), "", Toast.LENGTH_SHORT);
        }
        toastList.add(toast);
        toast.setText(TypeChange.toString(content));
        toast.setDuration(Toast.LENGTH_LONG);
        toast.show();
    }


    /**
     * 显示文本内容 居中
     *
     * @param content 显示内容
     */
    public static void showCenter(String content) {
        if (toast == null) {
            toast = Toast.makeText(AppContext.getContext(), "", Toast.LENGTH_SHORT);
        }
        toastList.add(toast);
        toast.setText(content);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }

    /**
     * 显示文本内容 居中,长时
     *
     * @param content 显示内容
     */
    public static void showCenterLong(String content) {
        if (toast == null) {
            toast = Toast.makeText(AppContext.getContext(), "", Toast.LENGTH_SHORT);
        }
        toastList.add(toast);
        toast.setText(content);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.show();
    }

    /**
     * 显示文本内容 自定义位置、时间 单例模式
     *
     * @param content  显示内容
     * @param duration 显示的时间
     * @param position 显示的位置
     */
    public static void showSingleton(String content, int duration,
                                         int position) {
        if (toast == null) {
            toast = Toast.makeText(AppContext.getContext(), "", duration);
        }
        toast.setText(content);
        toast.setDuration(duration);
        toast.setGravity(position, 0, 0);
        toast.show();
    }

    /**
     * Toast 无背景透明的文本
     *
     * @param content  内容
     * @param duration 时长
     * @param position 位置
     */
    public static void showSNBacText(String content, int duration, int position) {
        if (toast == null) {
            toast = Toast.makeText(AppContext.getContext(), "", duration);
        }
        LinearLayout linearLayout = new LinearLayout(AppContext.getContext());// 创建线性布局
        linearLayout.setOrientation(LinearLayout.VERTICAL);// 设置布局垂直
        TextView textView = new TextView(AppContext.getContext());
        textView.setText(content);
        linearLayout.addView(textView);
        toast.setView(linearLayout);
        toast.setDuration(duration);
        toast.setGravity(position, 0, 0);
        toast.show();

    }

    /**
     * Toast图片 单例模式，自定义时间
     *
     * @param resId    图片资源ID
     * @param duration Toast.LENGTH_LONG/Toast.LENGTH_LONG
     */
    public static void showSingletonImageCenter(int resId, int duration) {
        if (toast == null) {
            toast = Toast.makeText(AppContext.getContext(), "", duration);
        }
        ImageView imageView = new ImageView(AppContext.getContext());
        imageView.setImageResource(resId);
        toast.setView(imageView);
        toast.setDuration(duration);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }

    /**
     * Toast图片 非单例模式，自定义时间
     *
     * @param resId    图片资源ID
     * @param duration Toast.LENGTH_LONG/Toast.LENGTH_LONG
     */
    public static void showImageCenter(int resId, int duration) {
        toast = Toast.makeText(AppContext.getContext(), "", duration);
        toastList.add(toast);
        ImageView imageView = new ImageView(AppContext.getContext());// 创建图片控件
        imageView.setImageResource(resId);// 给控件设置图片
        toast.setView(imageView);// 把图片绑定到Toast上
        toast.setDuration(duration);// Toast显示的时间;
        // 设置图片显示的位置：三个参数
        // 第一个：位置，可以用|添加并列位置，第二个：相对于X的偏移量，第三个：相对于Y轴的偏移量
        // 注意一点：第二和第三个参数是相对于第一个参数设定的位置偏移的
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();// 显示Toast
    }

    /**
     * Toast图片 单例模式，自定义时间,自定义位置
     *
     * @param resId    图片资源ID
     * @param duration Toast.LENGTH_LONG/Toast.LENGTH_LONG
     * @param position Gravity.LEFT,Gravity.BOTTOM | Gravity.RIGHT...多个位置用竖线分割
     */
    public static void showSingletonImage(int resId, int duration, int position) {
        if (toast == null) {
            toast = Toast.makeText(AppContext.getContext(), "", duration);
        }
        ImageView imageView = new ImageView(AppContext.getContext());// 创建图片控件
        imageView.setImageResource(resId);
        toast.setView(imageView);
        toast.setDuration(duration);
        toast.setGravity(position, 0, 0);
        toast.show();
    }

    /**
     * Toast图片 非单例模式，自定义时间,自定义位置
     *
     * @param resId    图片资源ID
     * @param duration Toast.LENGTH_LONG/Toast.LENGTH_LONG
     * @param position Gravity.LEFT,Gravity.BOTTOM | Gravity.RIGHT...多个位置用竖线分割
     */
    public static void showImage(int resId, int duration, int position) {
        toast = Toast.makeText(AppContext.getContext(), "", duration);
        toastList.add(toast);
        ImageView imageView = new ImageView(AppContext.getContext());// 创建图片控件
        imageView.setImageResource(resId);
        toast.setView(imageView);
        toast.setDuration(duration);
        toast.setGravity(position, 0, 0);
        toast.show();
    }

    /**
     * Toast图片 非单例模式，自定义时间,自定义位置
     *
     * @param bitmap   图片资源ID
     * @param duration Toast.LENGTH_LONG/Toast.LENGTH_LONG
     * @param position Gravity.LEFT,Gravity.BOTTOM | Gravity.RIGHT...多个位置用竖线分割
     */
    public static void showImage(Bitmap bitmap, int duration, int position) {
        toast = Toast.makeText(AppContext.getContext(), "", duration);
        toastList.add(toast);
        ImageView imageView = new ImageView(AppContext.getContext());// 创建图片控件
        imageView.setImageBitmap(bitmap);
        toast.setView(imageView);
        toast.setDuration(duration);
        toast.setGravity(position, 0, 0);
        toast.show();
    }

    /**
     * 自定义显示图文结合的Toast 非单例
     *
     * @param resId    图片id
     * @param content  文本内容
     * @param duration toast时长
     * @param position toast位置
     */
    public static void showIT(int resId, String content, int duration,
                              int position) {
        toast = Toast.makeText(AppContext.getContext(), "", duration);
        toastList.add(toast);
        LinearLayout linearLayout = new LinearLayout(AppContext.getContext());// 创建线性布局
        linearLayout.setOrientation(LinearLayout.VERTICAL);// 设置布局垂直
        ImageView imageView = new ImageView(AppContext.getContext());// 创建图片控件
        imageView.setImageResource(resId);// 给控件设置图片
        TextView textView = new TextView(AppContext.getContext());// 创建文本控件
        textView.setGravity(Gravity.CENTER);
        textView.setText(content);// 设置文本内容
        linearLayout.addView(imageView);// 添加图片控件到布局中
        linearLayout.addView(textView);// 添加文本控件到布局中。注意添加顺序会影响图片在前还是为本在前
        toast.setView(linearLayout);// 把布局绑定到Toast上
        toast.setDuration(duration);// Toast显示的时间;
        /**
         * position：显示位置 第二个参数：相对X的偏移量 第三个参数：相对Y的偏移量 第二和第三个参数是相对于第一个参数设定的位置偏移的
         */
        toast.setGravity(position, 0, 0);
        toast.show();// 显示Toast
    }

    /**
     * 自定义显示图文结合的Toast 单例
     *
     * @param resId    图片id
     * @param content  文本内容
     * @param duration toast时长
     * @param position toast位置
     */
    public static void showITSingleton(int resId, String content, int duration,
                                       int position) {
        if (toast == null) {
            toast = Toast.makeText(AppContext.getContext(), "", duration);
        }
        toastList.add(toast);
        LinearLayout linearLayout = new LinearLayout(AppContext.getContext());// 创建线性布局
        linearLayout.setOrientation(LinearLayout.VERTICAL);// 设置布局垂直
        ImageView imageView = new ImageView(AppContext.getContext());// 创建图片控件
        imageView.setImageResource(resId);// 给控件设置图片
        TextView textView = new TextView(AppContext.getContext());// 创建文本控件
        textView.setText(content);// 设置文本内容
        linearLayout.addView(imageView);// 添加图片控件到布局中
        linearLayout.addView(textView);// 添加文本控件到布局中。注意添加顺序会影响图片在前还是为本在前
        toast.setView(linearLayout);// 把布局绑定到Toast上
        toast.setDuration(duration);// Toast显示的时间;
        /**
         * position：显示位置 第二个参数：相对X的偏移量 第三个参数：相对Y的偏移量 第二和第三个参数是相对于第一个参数设定的位置偏移的
         */
        toast.setGravity(position, 0, 0);
        toast.show();// 显示Toast
    }

    /**
     * Toast 多行文本 非单例
     *
     * @param size     字体大小
     * @param contents list 形式的文本内容
     */
    public static void showLines(List<String> contents, int size) {
        toast = Toast.makeText(AppContext.getContext(), "", Toast.LENGTH_LONG);
        toastList.add(toast);
        LinearLayout linearLayoutTop = new LinearLayout(AppContext.getContext());// 创建线性布局
        linearLayoutTop.setOrientation(LinearLayout.VERTICAL);// 设置布局垂直
        for (int i = 0; i < contents.size(); i++) {
            TextView textView = new TextView(AppContext.getContext());
            textView.setText(contents.get(i));
            textView.setTextSize(size);
            linearLayoutTop.addView(textView);
        }
        toast.setView(linearLayoutTop);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.show();
    }

    /**
     * Toast 多行文本 单例
     *
     * @param size     字体大小
     * @param contents list 形式的文本内容
     */
    public static void showSingletonLines(List<String> contents, int size) {
        if (toast == null) {
            toast = Toast
                    .makeText(AppContext.getContext(), "", Toast.LENGTH_LONG);
        }
        toastList.add(toast);
        LinearLayout linearLayoutTop = new LinearLayout(AppContext.getContext());// 创建线性布局
        linearLayoutTop.setOrientation(LinearLayout.VERTICAL);// 设置布局垂直
        for (int i = 0; i < contents.size(); i++) {
            TextView textView = new TextView(AppContext.getContext());
            textView.setText(contents.get(i));
            textView.setTextSize(size);
            linearLayoutTop.addView(textView);
        }
        toast.setView(linearLayoutTop);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.show();
    }

    /**
     * Toast 自定义布局 非单例
     *
     * @param view     布局
     * @param duration 时长
     * @param position 位置
     */
    public static void showLayout(View view, int duration, int position) {
        toast = Toast.makeText(AppContext.getContext(), "", Toast.LENGTH_LONG);
        toast.setDuration(duration);
        toast.setGravity(position, 0, 0);
        toast.setView(view);
        toast.show();

    }

    /**
     * Toast 自定义布局 单例
     *
     * @param view     布局
     * @param duration 时长
     * @param position 位置
     */
    public static void showSingletonLayout(View view, int duration, int position) {
        if (toast == null) {
            toast = Toast
                    .makeText(AppContext.getContext(), "", Toast.LENGTH_LONG);
        }
        toast.setDuration(duration);
        toast.setGravity(position, 0, 0);
        toast.setView(view);
        toast.show();

    }

    /**
     * 取消最近创建的一个Toast
     */
    public static void cancel() {
        if (toast != null) {
            toast.cancel();
        }
    }

    /**
     * 取消所有的Toast任务
     */
    public static void cancelAll() {
        for (int i = 0; i < toastList.size(); i++) {
            if (toastList.get(i) != null) {
                toastList.get(i).cancel();
            }
        }
    }

    /**
     * 反射字段
     *
     * @param object    要反射的对象
     * @param fieldName 要反射的字段名称
     * @return
     * @throws NoSuchFieldException
     * @throws IllegalAccessException
     */
    private static Object getField(Object object, String fieldName)
            throws NoSuchFieldException, IllegalAccessException {
        Field field = object.getClass().getDeclaredField(fieldName);
        if (field != null) {
            field.setAccessible(true);
            return field.get(object);
        }
        return null;
    }

/*    private static void taostfs() {
        // 通过反射给Toast设置动画
        try {
            Object mTN = null;
            mTN = getField(toast, "mTN");
            if (mTN != null) {
                Object mParams = getField(mTN, "mParams");
                if (mParams != null
                        && mParams instanceof WindowManager.LayoutParams) {
                    WindowManager.LayoutParams params = (WindowManager.LayoutParams) mParams;
                    // 动画效果
                    params.windowAnimations = R.style.MyToast;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        // 通过反射给Toast设置动画
    }*/

    /**
     * 居中显示：自定义动画(反射)
     *
     * @param content 显示内容
     */
    public static void Myshow(String content) {
        toast = Toast.makeText(AppContext.getContext(), "", Toast.LENGTH_SHORT);
        String contenty = "<font color='#000000'>" + content + "</font>";
        toast.setText(Html.fromHtml(contenty));
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        //得到视图
        View view = toast.getView();
        /*设置视图*/
//        view.setBackgroundResource(R.drawable.toastview);//R.drawable.toastview
        toast.setView(view);
        toast.show();
//        taostfs();
    }

}
