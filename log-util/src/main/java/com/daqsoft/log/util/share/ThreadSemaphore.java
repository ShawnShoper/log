package com.daqsoft.log.util.share;

import java.util.ArrayList;
import java.util.List;

/**
 * Thread semaphore
 * recording stack information
 */
public class ThreadSemaphore {
    private String id;
    List<MethodCall> threadSemaphores = new ArrayList<>();
    public ThreadSemaphore() {
        this.id = IdGenerator.generate();
    }

    public String getId() {
        return id;
    }

    public List<MethodCall> getThreadSemaphores() {
        return threadSemaphores;
    }

    public void addThreadSemaphores(MethodCall methodCall) {
        this.threadSemaphores = threadSemaphores;
    }
}
