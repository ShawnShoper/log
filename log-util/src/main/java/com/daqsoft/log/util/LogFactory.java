package com.daqsoft.log.util;


import com.daqsoft.log.util.config.LogProperties;

/**
 * Created by ShawnShoper on 2017/4/17.
 * 日志工厂
 */
public class LogFactory {
    //日志配置
    private static LogProperties logProperties;
    private static LogProcessor logProcessor;
    public static void setLogConfig(LogProperties logProperties) {
        LogFactory.logProperties = logProperties;
    }

    public static LogProperties getLogConfig() {
        return logProperties;
    }
    static{

    }
    public static Logger getLogger(Class<?> clazz) {
        synchronized (clazz) {
            return new Logger(clazz,logProcessor);
        }
    }

    private LogFactory() {

    }
}
