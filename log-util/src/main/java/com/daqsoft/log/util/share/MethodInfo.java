package com.daqsoft.log.util.share;

import java.lang.reflect.Method;

public class MethodInfo {
    private Method method;
    private int lineNumber;
    private String spanLink;
    private Long spanId;
    private StackTraceElement[] stackTraceElements;

    public MethodInfo(Method method, final StackTraceElement[] stackTraceElements, int lineNumber) {
        this.method = method;
        this.stackTraceElements = stackTraceElements;
        this.lineNumber = lineNumber;
    }

    public Method getMethod() {
        return method;
    }

    public StackTraceElement[] getStackTraceElements() {
        return stackTraceElements;
    }

    public int getLineNumber() {
        return lineNumber;
    }
}
