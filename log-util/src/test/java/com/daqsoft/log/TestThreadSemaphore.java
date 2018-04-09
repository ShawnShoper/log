package com.daqsoft.log;

import org.junit.Test;

import java.lang.reflect.Field;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TestThreadSemaphore {
    ExecutorService executorService = Executors.newFixedThreadPool(2);

    @Test
    public void testEetopSign() throws InterruptedException {
        CountDownLatch countDownLatch = new CountDownLatch(5);
        for (int i = 0; i < 5; i++) {
            executorService.submit(() -> {
//                Arrays.stream(thread.getClass().getDeclaredFields()).forEach(System.out::println);
                t();
                countDownLatch.countDown();
            });
        }
        countDownLatch.await();
        executorService.shutdown();
    }

    public void t() {
        Thread thread = Thread.currentThread();
        Class<? extends Thread> aClass = thread.getClass();
        Field eetop1 = null;
        Object o = null;
        try {
            eetop1 = aClass.getDeclaredField("eetop");
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
        eetop1.setAccessible(true);
        try {
            o = eetop1.get(thread);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        System.out.println("Thread id " + Thread.currentThread().getId() + "\t eetop " + o);
    }

}
