package com.daqsoft.log.util;


import com.daqsoft.log.util.appender.ConsoleAppender;
import com.daqsoft.log.util.config.LogProperties;

/**
 * Created by ShawnShoper on 2017/4/17.
 * 日志工厂
 */
public class LogFactory {
    //日志配置
    private static LogProperties logProperties;
    private static LogProcessor logProcessor;
    static{
        logProperties = new LogProperties();
        logProperties.setApplication("测试");
        logProperties.setHost("127.0.0.1");
        logProperties.setPort(8900);
        logProperties.setPartten("%-23{yyyy-MM-dd HH:mm:ss.sss}t%-5l%6p%30mn%-5ln%cn%c");
        logProcessor = new LogProcessor(new ConsoleAppender(logProperties));
    }

    public static void setLogConfig(LogProperties logProperties) {
        LogFactory.logProperties = logProperties;
    }

    public static LogProperties getLogConfig() {
        return logProperties;
    }

    public static Logger getLogger(Class<?> clazz) {
        synchronized (clazz) {
            return new Logger(clazz,logProcessor);
        }
    }

    private LogFactory() {

    }
}
