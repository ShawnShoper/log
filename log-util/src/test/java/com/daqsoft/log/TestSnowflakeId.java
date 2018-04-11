package com.daqsoft.log;

import com.daqsoft.log.util.share.SnowflakeIdWorker;
import org.junit.Test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TestSnowflakeId {
    @Test
    public void testSnowflakeId() throws InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        CountDownLatch countDownLatch = new CountDownLatch(10000);
        SnowflakeIdWorker idWorker = new SnowflakeIdWorker(0, 0);
        long s = System.currentTimeMillis();
        for (int i = 0; i < 10000; i++) {
            executorService.submit(() -> {
                long id = idWorker.nextId();
                System.out.println(id);
                countDownLatch.countDown();
            });
        }
        countDownLatch.await();
        System.out.println(System.currentTimeMillis() - s);
        executorService.shutdown();
    }
}
