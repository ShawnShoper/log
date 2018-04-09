package com.daqsoft.log;

import com.daqsoft.log.util.LogFactory;
import com.daqsoft.log.util.Logger;
import org.junit.Test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class TestLog {
    Logger logger = LogFactory.getLogger(TestLog.class);
    ExecutorService executorService = Executors.newFixedThreadPool(1);

    @Test
    public void testThrowable() throws InterruptedException {
        for (int i = 0; i < 2; i++) {
            try {
                int finalI = i;
                executorService.submit(() -> {
                    logger.info("User info message" + finalI);
                    Thread t = new Thread(() -> logger.info("sub thread ..."));
                    t.start();
                });
            } catch (Throwable t) {
//                logger.error("User handle a exceptions", t);
            }
//            TimeUnit.SECONDS.sleep(2);
        }
        TimeUnit.MINUTES.sleep(2);
    }

    public void throwable() {
//        throw new RuntimeException("i throw a runtime exception");
    }
}
