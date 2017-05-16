package com.daqsoft.log.util.appender;

import com.daqsoft.log.util.Log;
import com.daqsoft.log.util.config.LogProperties;

import java.io.IOException;

/**
 * Created by ShawnShoper on 2017/5/16.
 */
public class KafkaAppender extends Appender {
    public KafkaAppender(LogProperties logProperties) {
        super(logProperties);
    }

    @Override
    public void init() {

    }

    @Override
    public void write(Log log) throws IOException {

    }

    @Override
    public void destroy() {

    }
}
