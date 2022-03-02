package com.qwy.library.utils;

import java.lang.reflect.Field;

/**
 * 反射：动态获取资源文件ID
 */
public class ResourceMan {
    public static int getResId(String variableName, Class<?> c) {
        try {
            Field idField = c.getDeclaredField(variableName);
            return idField.getInt(idField);
        } catch (Exception e) {
            e.getMessage();
            return -1;
        }
    }
}
