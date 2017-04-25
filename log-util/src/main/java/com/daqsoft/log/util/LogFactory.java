package com.daqsoft.log.util;


import com.daqsoft.log.util.appender.Appender;
import com.daqsoft.log.util.appender.ConsoleAppender;
import com.daqsoft.log.util.appender.FileAppender;
import com.daqsoft.log.util.config.LogProperties;
import com.daqsoft.log.util.constans.Target;
import org.ho.yaml.Yaml;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
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
        if (Objects.isNull(logProperties)) {
            logProperties.setApplication(logProperties.getApplication());
            logProperties.setHost(logProperties.getHost());
            logProperties.setPort(logProperties.getPort());
            logProperties.setPartten("%-23{yyyy-MM-dd HH:mm:ss.sss}t%-5l%6p%30mn%-5ln%cn%c");
        }
        List<Appender> appenders = new ArrayList<>(logProperties.getTargets().length);
        for (Target target : logProperties.getTargets()) {
            if (target == Target.File)
                appenders.add(new FileAppender(logProperties));
            else if (target == Target.Sout)
                appenders.add(new ConsoleAppender(logProperties));
            else {
                throw new RuntimeException("Target " + target + " not support yet");
            }
        }
        logProcessor = new LogProcessor(appenders);

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
