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

    private static Timer timerLoop;
    private static TimerTask timerLoopTask;

    private static HashMap<Integer, ITimer> iTimerHashMap = new HashMap<>();

    private static HashMap<Integer, Timer> timerHashMap = new HashMap<>();
    ;
    private static HashMap<Integer, TimerTask> timerTaskHashMap = new HashMap<>();

    private static HashMap<Integer, Integer> time_HashMap = new HashMap<>();

    private static Handler mainHandler = new Handler(Looper.getMainLooper());

    private static int timeJG = 1000;

    public static void startTimerLoop(final int id, final ITimer iTimer) {
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

    public static void stopTimerLoop(int id) {
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

    public static void stopAll() {
        List<Integer> integerList = new ArrayList<>();
        for (Iterator iter = iTimerHashMap.entrySet().iterator(); iter.hasNext(); ) {
            Map.Entry element = (Map.Entry) iter.next();
            Object strKey = element.getKey();
            Integer ii = (Integer) strKey;
            integerList.add(ii);
        }
        for (int ii : integerList) {
            stopTimerLoop(ii);
        }
    }

}