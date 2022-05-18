package com.qwy.library.eventbus;

import android.os.Handler;
import android.os.Looper;

import com.qwy.library.utils.L;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

public class EventBusQwy {

    private static volatile EventBusQwy instance;

    public static synchronized EventBusQwy getInstance() {
        if (instance == null) {
            instance = new EventBusQwy();
        }
        return instance;
    }

    Handler mainHandler = new Handler(Looper.getMainLooper());

    public synchronized void init(Object objectClass) {
        if (isExist(objectClass) != null) return;
        try {
            Class cls = objectClass.getClass();//获取类

            Method[] methods;

            //获取方法
            try {
                methods = cls.getDeclaredMethods();//此方法获取更快
            } catch (SecurityException e) {
                methods = cls.getMethods();
            }

            if (methods != null) {
                AnnotationBean annotationBean = new AnnotationBean();
                annotationBean.setAddress(objectClass.hashCode());
                annotationBean.setObject(objectClass);
                List<Method> methodList = new ArrayList<>();

                for (Method method : methods) {
                    int modifiers = method.getModifiers();

                    if (modifiers == Modifier.PUBLIC) {
                        Qwy subscribeAnnotation = method.getAnnotation(Qwy.class);
                        if (subscribeAnnotation != null) {
                            methodList.add(method);

                            if (subscribeAnnotation.sticky() && saveObjectValue != null) {
                                invoke(method, annotationBean.getObject(), saveObjectValue);
                            }
                        }
                    }
                }

                annotationBean.setMethodList(methodList);
                annotationBeanList.add(annotationBean);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private List<AnnotationBean> annotationBeanList = new ArrayList<>();

    public void post(Object objectValue) {
        saveObjectValue = objectValue;
        if (objectValue == null) return;

        try {
            for (AnnotationBean annotationBean : annotationBeanList) {
                for (Method method : annotationBean.getMethodList()) {

                    if (method.getGenericParameterTypes().length == 1) {
                        String methodNameType = method.getGenericParameterTypes()[0].toString().replace("class ", "");
                        String objectValueType = objectValue.getClass().getName();
                        if (methodNameType.equals(objectValueType)) {

                            invoke(method, annotationBean.getObject(), objectValue);
                        }
                    }
                }
            }

        } catch (Exception e) {
            L.d("Post异常：" + e.getMessage());
        }
    }

    private void invoke(final Method method, final Object object, final Object objectValue) {
        mainHandler.post(new Runnable() {
            @Override
            public void run() {
                try {
                    method.invoke(object, objectValue);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private Object saveObjectValue;

    public void uninit(Object objectClass) {
        AnnotationBean annotationBean = isExist(objectClass);
        if (annotationBean == null) return;
        annotationBeanList.remove(annotationBean);
    }

    private AnnotationBean isExist(Object objectClass) {
        AnnotationBean isExist = null;
        int address = objectClass.hashCode();
        for (AnnotationBean annotationBean : annotationBeanList) {
            if (annotationBean.getAddress() == address) {
                isExist = annotationBean;
                return isExist;
            }
        }
        return isExist;
    }
}
