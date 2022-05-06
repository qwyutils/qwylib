package com.qwy.library.utils;

import android.os.Handler;
import android.os.Looper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

public class TimerUtils {
    //会立马调用一次
    public static void startTimerLoop(Object id, ITimer iTimer) {
        startTimerLoop(TypeChange.toString(id), iTimer);
    }

    //第一次回调,不返回
    public static void startTimerLoopTwo(Object id, ITimer iTimer) {
        startTimerLoopTwo(TypeChange.toString(id), iTimer);
    }

    //停止定时
    public static void stopTimerLoop(Object id) {
        stopTimerLoop(TypeChange.toString(id));
    }

    //停止所有定时任务
    public static void stopAll() {
        List<String> integerList = new ArrayList<>();
        for (Iterator iter = iTimerHashMap.entrySet().iterator(); iter.hasNext(); ) {
            Map.Entry element = (Map.Entry) iter.next();
            Object strKey = element.getKey();
            String ii = (String) strKey;
            integerList.add(ii);
        }
        for (String ii : integerList) {
            stopTimerLoop(ii);
        }
    }

    private static Timer timerLoop;
    private static TimerTask timerLoopTask;

    private static HashMap<String, ITimer> iTimerHashMap = new HashMap<>();

    private static HashMap<String, Timer> timerHashMap = new HashMap<>();
    ;
    private static HashMap<String, TimerTask> timerTaskHashMap = new HashMap<>();

    private static HashMap<String, Integer> time_HashMap = new HashMap<>();

    private static Handler mainHandler = new Handler(Looper.getMainLooper());

    private static int timeJG = 1000;


    private static void startTimerLoop(final String id, ITimer iTimer) {
        stopTimerLoop(id);
        iTimerHashMap.put(id, iTimer);

        Timer timerLoop = new Timer();
        timerHashMap.put(id, timerLoop);

        TimerTask timerLoopTask = new TimerTask() {
            @Override
            public void run() {
                mainHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        if (iTimerHashMap.get(id) != null)
                            iTimerHashMap.get(id).event();
                    }
                });
            }
        };
        timerTaskHashMap.put(id, timerLoopTask);
        time_HashMap.put(id, timeJG);
        timeJG = 1000;
        if (timerHashMap.get(id) != null && timerTaskHashMap.get(id) != null)
            timerHashMap.get(id).schedule(timerTaskHashMap.get(id), 0, time_HashMap.get(id));
    }


    private static void stopTimerLoop(String id) {
        if (!timerHashMap.containsKey(id)) return;

        iTimerHashMap.remove(id);

        timerLoop = timerHashMap.get(id);
        if (timerLoop != null) {
            timerLoop.cancel();
            timerLoop = null;
        }
        timerHashMap.remove(id);

        timerLoopTask = timerTaskHashMap.get(id);
        if (timerLoopTask != null) {
            timerLoopTask.cancel();
            timerLoopTask = null;
        }
        timerTaskHashMap.remove(id);
    }

    public static void setTime(int time) {
        timeJG = time;
    }

    public interface ITimer {
        void event();
    }


    private static HashMap<String, Boolean> one_HashMap = new HashMap<>();

    private static void startTimerLoopTwo(final String id, ITimer iTimer) {

        stopTimerLoop(id);
        iTimerHashMap.put(id, iTimer);

        Timer timerLoop = new Timer();
        timerHashMap.put(id, timerLoop);

        one_HashMap.put(id, true);

        TimerTask timerLoopTask = new TimerTask() {
            @Override
            public void run() {
                mainHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        if (iTimerHashMap.get(id) != null) {
                            if (one_HashMap.get(id) == true) {
                                one_HashMap.put(id, false);
                            } else {
                                iTimerHashMap.get(id).event();
                            }
                        }
                    }
                });
            }
        };
        timerTaskHashMap.put(id, timerLoopTask);
        time_HashMap.put(id, timeJG);
        timeJG = 1000;
        if (timerHashMap.get(id) != null && timerTaskHashMap.get(id) != null)
            timerHashMap.get(id).schedule(timerTaskHashMap.get(id), 0, time_HashMap.get(id));
    }

}