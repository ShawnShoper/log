package com.daqsoft.log.collcetor;

import com.daqsoft.log.core.serialize.Log;

import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;

/**
 * Created by ShawnShoper on 2017/5/25.
 * //Collcetor queue
 */
public class CollcetorQueue {
    public static final BlockingDeque<Log> collcetorQueue = new LinkedBlockingDeque<>();
}
