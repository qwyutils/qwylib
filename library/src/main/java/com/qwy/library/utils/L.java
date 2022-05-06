package com.qwy.library.utils;

import android.text.TextUtils;
import android.util.Log;

public class L {

    private static boolean isPrinting = true;

    public static void setIsisPrinting(boolean isisPrinting) {
        L.isPrinting = isisPrinting;
    }

    public static void d(String tag, Object object) {
        if (isPrinting) {
            d(tag, getAddress(4), toString(object));
        }
    }

    public static void d(Object object) {
        if (isPrinting) {
            d(null, getAddress(4), toString(object));
        }
    }

    public static void l(String tag, Object object) {
        if (isPrinting) {
            d(tag, getAddress(5), toString(object));
        }
    }

    public static void l(Object object) {
        if (isPrinting) {
            d(null, getAddress(5), toString(object));
        }
    }

    public static void l(Object object, int i) {
        if (isPrinting) {
            d(null, getAddress(5 + i), toString(object));
        }
    }

    private static String getAddress(int position) {
        StackTraceElement stackTraceElement = Thread.currentThread().getStackTrace()[position];
        String address = "(" + stackTraceElement.getFileName() + ":" + stackTraceElement.getLineNumber() + ")";
        return address;
    }

    private static void d(String tag, String address, String content) {
        if (TextUtils.isEmpty(tag)) {
            Log.d("ASDF", address + "\t" + content);
        } else {
            Log.d(tag + "ASDF", address + "\t" + content);
        }
    }

    public static void a(String activityName, String content) {
        if (isPrinting) {
            d("onCreate", "(" + activityName + ":1)", content);
        }
    }

    public static String toString(Object object) {
        return TypeChange.toString(object);
    }
}
