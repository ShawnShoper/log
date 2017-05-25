package com.daqsoft.log.util;

import com.daqsoft.log.core.config.Constans;
import com.daqsoft.log.util.config.LogProperties;

import javax.annotation.PreDestroy;

public class Logger {
    private Class<?> clazz;
    private LogProcessor logProcessor;
    public Class<?> getClazz() {
        return clazz;
    }

    public void setClazz(Class<?> clazz) {
        this.clazz = clazz;
    }

    public Logger(Class<?> clazz,final LogProcessor logProcessor) {
        this.clazz = clazz;
        this.logProcessor = logProcessor;
    }

    private LogProperties logProperties;

    @PreDestroy
    public void destroy() {
    }

    public void info(String logMsg) {
        log(logMsg, Constans.INFO);
    }

    public void info(String formatter, Object... obj) {
        info(String.format(formatter, obj));
    }

    public void warn(String logMsg) {
        log(logMsg, Constans.WARN);
    }

    public void warn(String formatter, Object... obj) {
        warn(String.format(formatter, obj));
    }

    private void debug(String logMsg) {
        log(logMsg, Constans.DEBUG);
    }

    public void debug(String formatter, Object... obj) {
        debug(String.format(formatter, obj));
    }

    public void error(String logMsg) {
        log(logMsg, Constans.ERROR);
    }

    public void error(String formatter, Object... obj) {
        error(String.format(formatter, obj));
    }

    /**
     * @author ShawnShoper
     * @date 2016/12/20 0020 11:27
     * LogUtil构造，构造中开启线程进行维护
     */
    public Logger(LogProperties logProperties) {
        this.logProperties = logProperties;
    }

    /**
     * @author ShawnShoper
     * @date 2016/12/20 0020 16:35
     * 记录日志
     */
    public void log(String logMsg, String logLevel) {
        logProcessor.processor(logMsg, logLevel, clazz);
    }

}