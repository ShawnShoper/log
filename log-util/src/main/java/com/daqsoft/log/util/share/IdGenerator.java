package com.daqsoft.log.util.share;

/**
 * MCC id generator
 */
public class IdGenerator {


    private IdGenerator() {

    }

    /**
     * id generate for thread semaphore
     * use thread id  and current time to build a unique id
     */
    public static String generate() {
        long tid = Thread.currentThread().getId();
        long l = System.currentTimeMillis();
        return String.valueOf(l) + String.valueOf(tid);
    }
}
