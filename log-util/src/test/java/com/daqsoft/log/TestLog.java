package com.daqsoft.log;

import com.daqsoft.log.util.LogFactory;
import com.daqsoft.log.util.Logger;
import org.junit.Test;

import java.util.concurrent.TimeUnit;

public class TestLog {
    Logger logger = LogFactory.getLogger(TestLog.class);

    @Test
    public void testThrowable() throws InterruptedException {
        for (int i = 0; i < 100; i++) {
            try {
                logger.info("User info message");
                throwable();
            } catch (Throwable t) {
//                logger.error("User handle a exceptions", t);
            }
            TimeUnit.SECONDS.sleep(3);
        }
    }

    public void throwable() {
        throw new RuntimeException("i throw a runtime exception");
    }
}
