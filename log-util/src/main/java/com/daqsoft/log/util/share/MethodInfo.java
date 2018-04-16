package com.daqsoft.log.util.share;

import java.lang.reflect.Method;

public class MethodInfo {
    private Method method;
    private int lineNumber;
    //log calling span index;
    private long spanIndex;
    //stack info
    private StackTraceElement[] stackTraceElements;

    public MethodInfo(Method method, final StackTraceElement[] stackTraceElements, int lineNumber) {
        this.method = method;
        this.stackTraceElements = stackTraceElements;
        this.lineNumber = lineNumber;
    }

    public long getSpanIndex() {
        return spanIndex;
    }

    public void setSpanIndex(long spanIndex) {
        this.spanIndex = spanIndex;
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
