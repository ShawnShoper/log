package com.daqsoft.log.util;


import com.daqsoft.log.util.appender.ConsoleAppender;
import com.daqsoft.log.util.config.LogProperties;
import org.ho.yaml.Yaml;

import java.io.FileNotFoundException;
import java.util.Objects;

/**
 * Created by ShawnShoper on 2017/4/17.
 * 日志工厂
 */
public class LogFactory {
    //日志配置
    private static LogProperties logProperties;
    private static LogProcessor logProcessor;

    public static LogProperties getLogProperties() {
        return logProperties;
    }

    private static void init() {
        try {
            logProperties = Yaml.loadType(LogProperties.class.getResourceAsStream("/log.yml"), LogProperties.class);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        if(Objects.isNull(logProperties)) {
            logProperties.setApplication(logProperties.getApplication());
            logProperties.setHost(logProperties.getHost());
            logProperties.setPort(logProperties.getPort());
            logProperties.setPartten("%-23{yyyy-MM-dd HH:mm:ss.sss}t%-5l%6p%30mn%-5ln%cn%c");
        }
        logProcessor = new LogProcessor(new ConsoleAppender(logProperties));
    }

    public static void setLogConfig(LogProperties logProperties) {
        LogFactory.logProperties = logProperties;
    }


    public static Logger getLogger(Class<?> clazz) {
        if (Objects.isNull(logProperties))
            synchronized (LogProperties.class) {
                if (Objects.isNull(logProcessor))
                    init();
            }
        return new Logger(clazz, logProcessor);
    }

    private LogFactory() {

    }
}
