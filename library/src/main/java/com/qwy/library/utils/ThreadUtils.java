package com.qwy.library.utils;

import android.os.Handler;
import android.os.Looper;

public class ThreadUtils {
    Handler mainHandler = new Handler(Looper.getMainLooper());

    private static volatile ThreadUtils instance;

    public static synchronized ThreadUtils getInstance() {
        if (instance == null) {
            instance = new ThreadUtils();
        }
        return instance;
    }

    public void start(final IThreadUtils iThreadUtils) {
        new Thread(new Runnable() {
            @Override
            public void run() {

                try {
                    final Object object = iThreadUtils.childThreadRun();
                    mainHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            iThreadUtils.resultMainThread(object);
                        }
                    });
                } catch (final Exception e) {

                    mainHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            iThreadUtils.failRun(e);
                        }
                    });

                }

            }
        }).start();
    }

    public interface IThreadUtils<T> {
        T childThreadRun();

        void resultMainThread(T object);

        void failRun(Exception exception);
    }
}
