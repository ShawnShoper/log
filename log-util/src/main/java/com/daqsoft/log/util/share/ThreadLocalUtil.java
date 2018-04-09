package com.daqsoft.log.util.share;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * MCC util
 */
public class ThreadLocalUtil {


    private ThreadLocalUtil() {

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

    /**
     * mcc According method calling stack chain.
     * condition 1:
     * During the chain.
     * Just compare with the thread id is equal to the last log's thread....
     * condition 2:
     * Other the chain
     * First Step
     * calculate the first stack token value and compare to that before the chain's value of the thread local,
     * if result is true,the chain is not the seem chain.
     */
    public Optional<String> generate(int callNumber, Method method, int threadId, ThreadSemaphore threadSemaphore, List<String> stackTraceElements) {
        String beforeToken = threadSemaphore.getFirstStackToken();


        List<StackTraceElement[]> lastChain = threadSemaphore.getThreadSemaphores().stream().map(MethodInfo::getStackTraceElements).collect(Collectors.toList());

        return Optional.ofNullable(null);
    }
}