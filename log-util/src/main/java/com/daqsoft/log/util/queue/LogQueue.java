package com.daqsoft.log.util.queue;


import java.util.Map;
import java.util.concurrent.LinkedTransferQueue;

/**
 * Created by ShawnShoper on 2017/4/17.
 */
public class LogQueue {
    public static LinkedTransferQueue<Map<String,Object>> logQueue = new LinkedTransferQueue();
}
