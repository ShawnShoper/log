package com.daqsoft.log.util.share;

import java.lang.reflect.Method;

public class MethodInfo {
    private Method method ;
    StackTraceElement[] stackTraceElements;
    public MethodInfo(Method method, StackTraceElement[] stackTraceElements) {
        this.method = method;
        this.stackTraceElements = stackTraceElements;
    }

    public void setStackTraceElements(StackTraceElement[] stackTraceElements) {
        this.stackTraceElements = stackTraceElements;
    }

    public Method getMethod() {
        return method;
    }

    public void setMethod(Method method) {
        this.method = method;
    }

    public StackTraceElement[] getStackTraceElements() {
        return stackTraceElements;
    }

}
