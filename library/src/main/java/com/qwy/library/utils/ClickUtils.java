package com.qwy.library.utils;

import java.util.HashMap;

/**
 * ***时间内仅执行一次***
 * ①if(ClickUtils.isFastClick(v.getId()))return; //执行任务
 * ***时间内仅执行一次双击***
 * ②if(ClickUtils.isTwoClickOutside(v.getId()))return; //执行任务
 */
public class ClickUtils {

    private static final int MIN_CLICK_DELAY_TIME = 2000;
    private static int curTime = -1;

    private static void setCurTime(int curTime) {
        ClickUtils.curTime = curTime;
    }

    public static boolean isFastClick(int curTime, Object deviceId) {
        ClickUtils.curTime = curTime;
        return isFastClick(TypeChange.toString(deviceId));
    }

    private static HashMap<String, Long> sLastClickTimeMap = new HashMap<>();

    public static boolean isFastClick(int deviceId) {
        return isFastClick(TypeChange.toString(deviceId));
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

    private static HashMap<String, Boolean> twoClickMap = new HashMap<>();

    public static boolean isTwoClickOutside(Object deviceId) {
        return isTwoClickOutside(MIN_CLICK_DELAY_TIME, deviceId);
    }

    public static boolean isTwoClickOutside(int curTime, Object deviceId) {
        boolean isTwoClick = true;
        String id = TypeChange.toString(deviceId);
        boolean isFastClick = isFastClick(curTime, id);
        if (isFastClick) {
            if (twoClickMap.get(id) != null && twoClickMap.get(id) == true) {
                twoClickMap.put(id, false);
                isTwoClick = false;
            }

        } else {
            twoClickMap.put(id, true);
        }

        return isTwoClick;
    }
}
