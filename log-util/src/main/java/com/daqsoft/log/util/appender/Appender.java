package com.daqsoft.log.util.appender;

import com.daqsoft.log.core.serialize.Log;
import com.daqsoft.log.util.config.LogProperties;

import java.io.IOException;

/**
 * Created by ShawnShoper on 2017/4/19.
 */
public abstract class Appender {
    LogProperties logProperties;
    public abstract void init();

    public Appender(LogProperties logProperties) {
        this.logProperties = logProperties;
    }

    public abstract void write(Log log) throws IOException;

    public abstract void destroy();
}
