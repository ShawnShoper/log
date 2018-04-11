package com.daqsoft.log;

import com.daqsoft.log.util.LogFactory;
import com.daqsoft.log.util.Logger;
import org.junit.Test;

import java.util.concurrent.TimeUnit;

public class TestLog {
    Logger logger = LogFactory.getLogger(TestLog.class);

    @Test
    public void testThrowable() throws InterruptedException {
        for (int i = 0; i < 1; i++) {
            try {
                int finalI = i;
               testSubMethod(finalI);
            } catch (Throwable t) {
//                logger.error("User handle a exceptions", t);
            }
//            TimeUnit.SECONDS.sleep(2);
        }
        TimeUnit.MINUTES.sleep(2);
    }

    public void testSubMethod(int finalI) {
        logger.info("User info message testSubMethod " + finalI);
        testSub2Me(finalI);
    }

    public void testSub2Me(int finalI) {
        logger.info("User info message testSub2Me " + finalI);
    }
}
