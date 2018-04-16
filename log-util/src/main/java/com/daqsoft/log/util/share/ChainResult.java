package com.daqsoft.log.util.share;

/**
 * validate next chain.
 */
public enum ChainResult {
    EMPTY,
    //not the same chain
    NOT_SAME,
    //the same chain
    SAME,
    //the same chain but not the same thread
    SAME_SUBTHREAD
}
