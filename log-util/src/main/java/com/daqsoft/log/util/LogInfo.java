package com.daqsoft.log.util;

/**
 * 日志信息
 */
public class LogInfo {
    private String msg;
    //错误堆栈
    private Throwable throwable;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Throwable getThrowable() {
        return throwable;
    }

    public void setThrowable(Throwable throwable) {
        this.throwable = throwable;
    }
}