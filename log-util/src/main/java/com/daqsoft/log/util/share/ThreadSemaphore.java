package com.daqsoft.log.util.share;

import java.util.ArrayList;
import java.util.List;

/**
 * Thread semaphore
 * recording stack information
 */
public class ThreadSemaphore {
    private Long id;
    //curr thread id
    private int tid;
    private String firstStackToken;
    //the log root class name
    private String rootClassName;
    //the log root method name;
    private String rootMethodName;
    //the log root line number;
    private int rootLineNumber;
    private List<MethodInfo> threadSemaphores = new ArrayList<>();

    public ThreadSemaphore() {
        this.id = ThreadLocalUtil.generateId();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getTid() {
        return tid;
    }

    public void setTid(int tid) {
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

    public String getRootClassName() {
        return rootClassName;
    }

    public void setRootClassName(String rootClassName) {
        this.rootClassName = rootClassName;
    }

    public String getRootMethodName() {
        return rootMethodName;
    }

    public void setRootMethodName(String rootMethodName) {
        this.rootMethodName = rootMethodName;
    }

    public int getRootLineNumber() {
        return rootLineNumber;
    }

    public void setRootLineNumber(int rootLineNumber) {
        this.rootLineNumber = rootLineNumber;
    }
}
