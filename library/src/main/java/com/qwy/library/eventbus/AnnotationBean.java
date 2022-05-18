package com.qwy.library.eventbus;

import java.lang.reflect.Method;
import java.util.List;

public class AnnotationBean {
        private int address;
        private List<Method> methodList;
        private Object object;

        public int getAddress() {
            return address;
        }

        public void setAddress(int address) {
            this.address = address;
        }

        public List<Method> getMethodList() {
            return methodList;
        }

        public void setMethodList(List<Method> methodList) {
            this.methodList = methodList;
        }

        public Object getObject() {
            return object;
        }

        public void setObject(Object object) {
            this.object = object;
        }
    }