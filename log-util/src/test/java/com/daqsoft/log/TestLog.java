package com.daqsoft.log;

import com.daqsoft.log.util.LogFactory;
import com.daqsoft.log.util.Logger;
import org.junit.Test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class TestLog {
    Logger logger = LogFactory.getLogger(TestLog.class);
    ExecutorService executorService = Executors.newFixedThreadPool(2);

    @Test
    public void testThrowable() throws InterruptedException {
        for (int i = 0; i < 2; i++) {
            try {
                int finalI = i;
                executorService.submit(() -> {
                    testSubMe(finalI);
                    new Thread(() -> test2SubMe(finalI)).start();
                });
            } catch (Throwable t) {
            }
        }
        System.out.println("..");
        TimeUnit.MINUTES.sleep(2);
    }

    public void testSubMe(int finalI) {
        logger.info("User info message testSubMe " + finalI);
        testSub2Me(finalI);
    }

    public void testSub2Me(int finalI) {
        logger.info("User info message testSub2Me " + finalI);
    }

    public void test2SubMe(int finalI) {
        logger.info("User info message test2SubMe " + finalI);
    }

    public static void main(String[] args) {
        new Thread(() ->{
            System.out.println(1);
            System.out.println("2");
        }).start();

    }
}
