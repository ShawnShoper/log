package com.daqsoft.log.util.share;

/**
 * validate next chain.
 */
public class ChainResult {
    private boolean isNewChain;
    private int spanId;
    private long threadId;
    private long parentId;

    public boolean isNewChain() {
        return isNewChain;
    }

    public void setNewChain(boolean newChain) {
        isNewChain = newChain;
    }

    public int getSpanId() {
        return spanId;
    }

    public void setSpanId(int spanId) {
        this.spanId = spanId;
    }

    public long getThreadId() {
        return threadId;
    }

    public void setThreadId(long threadId) {
        this.threadId = threadId;
    }

    public long getParentId() {
        return parentId;
    }

    public void setParentId(long parentId) {
        this.parentId = parentId;
    }
}
