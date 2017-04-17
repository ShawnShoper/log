package com.daqsoft.log.util.queue;


import com.daqsoft.log.util.Log;

import java.util.concurrent.LinkedTransferQueue;

/**
 * Created by ShawnShoper on 2017/4/17.
 */
public class LogQueue {
    public static LinkedTransferQueue<Log> logQueue = new LinkedTransferQueue();
}
