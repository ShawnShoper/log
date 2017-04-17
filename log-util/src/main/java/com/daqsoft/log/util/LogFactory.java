package com.daqsoft.log.util;


/**
 * Created by ShawnShoper on 2017/4/17.
 */
public class LogFactory {

    public static Logger getLogger(Class<?> clazz) {
        synchronized (clazz) {
            return new Logger(clazz);
        }
    }
}
