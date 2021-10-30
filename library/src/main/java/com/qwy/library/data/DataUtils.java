package com.qwy.library.data;

import android.content.SharedPreferences;

import com.qwy.library.toast.AppContext;

/**
 * 本地SharedPreferences,用于保存Key信息
 */
public class DataUtils {
    /**
     * 默认值(未set时,即没有数据时,get获取的值)
     */
    public static Boolean default_boolean = true;
    public static String default_string = "";

    public static void setDefault_boolean(Boolean default_boolean) {
        DataUtils.default_boolean = default_boolean;
    }

    public static void setDefault_string(String ds) {
        DataUtils.default_string = ds;
    }

    //Boolean封装获取
    public static Boolean RB(String s) {
        return Boolean.valueOf(AppContext.getContext().getSharedPreferences("configure",
                0).getBoolean(s, default_boolean));
    }

    //Boolean封装得到
    public static void SB(String paramString, Boolean paramBoolean) {
        SharedPreferences.Editor localEditor = AppContext.getContext()
                .getSharedPreferences("configure", 0).edit();
        localEditor.putBoolean(paramString, paramBoolean.booleanValue());
        localEditor.commit();
    }

    //String封装获取
    public static String RS(String s) {
        return AppContext.getContext().getSharedPreferences("configure", 0).getString(
                s, default_string);
    }

    //String封装得到
    public static void SS(String s1, String s2) {
        SharedPreferences.Editor localEditor = AppContext.getContext()
                .getSharedPreferences("configure", 0).edit();
        localEditor.putString(s1, s2);
        localEditor.commit();
    }
}
