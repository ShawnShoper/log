package com.daqsoft.log.util.share;

import java.util.ArrayList;
import java.util.List;

/**
 * Thread semaphore
 * recording stack information
 */
public class ThreadSemaphore {
    private String id;
    private String tid;
    private String firstStackToken;
    private List<MethodInfo> threadSemaphores = new ArrayList<>();

    public ThreadSemaphore() {
        this.id = ThreadLocalUtil.generate();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTid() {
        return tid;
    }

    public void setTid(String tid) {
        this.tid = tid;
    }

    public String getFirstStackToken() {
        return firstStackToken;
    }

    public void setFirstStackToken(String firstStackToken) {
        this.firstStackToken = firstStackToken;
    }

    public List<MethodInfo> getThreadSemaphores() {
        return threadSemaphores;
    }
}
