package com.daqsoft.log.util.share;

import java.lang.reflect.Method;

public class MethodCall {
    private Method method ;
    StackTraceElement[] stackTraceElements;
    private String pakcageName;
    public MethodCall(Method method, StackTraceElement[] stackTraceElements) {
        this.method = method;
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
