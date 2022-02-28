package com.qwy.library.toast;

import android.content.Context;

/**
 * 保存全局Context对象
 */

public class AppContext {

    public static Context context;

    public AppContext() {
        throw new UnsupportedOperationException("not init ConTextUtils");
    }

    public static void initContext(Context context) {
        AppContext.context = context;
    }

    public static Context getContext() {
        if (context != null) {
            return context;
        } else {
            throw new NullPointerException("全局Context NullPointerException,you should init before");
        }
    }
}
