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
    public static int default_int = -1;

    public static void setDefault_boolean(Boolean default_boolean) {
        DataUtils.default_boolean = default_boolean;
    }

    public static void setDefault_string(String ds) {
        DataUtils.default_string = ds;
    }

    public static void setDefault_int(int default_int) {
        DataUtils.default_int = default_int;
    }

    /**
     * Int相关
     */
    //Int封装获取
    public static int getInt(String key) {
        return AppContext.getContext().getSharedPreferences("configure",
                0).getInt(key, default_int);
    }

    //Int封装获取
    public static int getInt(String key, int def_values) {
        return AppContext.getContext().getSharedPreferences("configure",
                0).getInt(key, def_values);
    }

    //Int封装设置,保存
    public static void setInt(String key, int value) {
        SharedPreferences.Editor localEditor = AppContext.getContext()
                .getSharedPreferences("configure", 0).edit();
        localEditor.putInt(key, value);
        localEditor.commit();
    }

    /**
     * Boolean相关
     */
    //Boolean封装获取
    public static Boolean getBoolean(String key) {
        return Boolean.valueOf(AppContext.getContext().getSharedPreferences("configure",
                0).getBoolean(key, default_boolean));
    }

    //Boolean封装获取
    public static Boolean getBoolean(String key, boolean def) {
        return Boolean.valueOf(AppContext.getContext().getSharedPreferences("configure",
                0).getBoolean(key, def));
    }

    //Boolean封装设置,保存
    public static void setBoolean(String key, Boolean value) {
        SharedPreferences.Editor localEditor = AppContext.getContext()
                .getSharedPreferences("configure", 0).edit();
        localEditor.putBoolean(key, value.booleanValue());
        localEditor.commit();
    }

    /**
     * String相关
     */
    //String封装获取
    public static String getString(String key) {
        return AppContext.getContext().getSharedPreferences("configure", 0).getString(
                key, default_string);
    }

    //String封装获取:有默认值
    public static String getString(String key, String def_value) {
        return AppContext.getContext().getSharedPreferences("configure", 0).getString(
                key, def_value);
    }

    //String封装设置,保存
    public static void setString(String key, String value) {
        SharedPreferences.Editor localEditor = AppContext.getContext()
                .getSharedPreferences("configure", 0).edit();
        localEditor.putString(key, value);
        localEditor.commit();
    }
}
