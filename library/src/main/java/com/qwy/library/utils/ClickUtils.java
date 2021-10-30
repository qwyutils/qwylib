package com.qwy.library.utils;

import java.util.HashMap;

public class ClickUtils {

    private static final int MIN_CLICK_DELAY_TIME = 2000;
    private static int curTime = -1;

    public static void setCurTime(int curTime) {
        ClickUtils.curTime = curTime;
    }

    private static HashMap<String, Long> sLastClickTimeMap = new HashMap<>();

    public static boolean isFastClick(int deviceId) {
        return isFastClick(String.valueOf(deviceId));
    }

    public static boolean isFastClick(Object deviceId) {
        return isFastClick(TypeChange.toString(deviceId));
    }

    public static boolean isFastClick(String deviceId) {
        if (curTime < 0) {
            setCurTime(MIN_CLICK_DELAY_TIME);
        }

        boolean flag = false;
        long curClickTime = System.currentTimeMillis();
        long lastClickTime = getLastClickTime(deviceId);
        if ((curClickTime - lastClickTime) < curTime) {
            flag = true;//快速点击
        } else {
            sLastClickTimeMap.put(deviceId, curClickTime);//间隔超过两秒
        }
        setCurTime(-1);
        return flag;
    }

    //activity onResume时调用(消除记录)
    public static void clear() {
        sLastClickTimeMap.clear();
    }

    private static Long getLastClickTime(String deviceId) {
        Long lastClickTime = sLastClickTimeMap.get(deviceId);
        if (lastClickTime == null) {
            lastClickTime = 0L;
        }
        return lastClickTime;
    }

}
