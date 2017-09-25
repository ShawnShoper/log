package com.daqsoft.log.collcetor;

import com.daqsoft.log.core.serialize.Log;
import com.daqsoft.log.transfer.elasticsearch.ESTransfer;
import com.daqsoft.log.transfer.hdfs.HDFSTransfer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.Objects;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.TimeUnit;

/**
 * Created by ShawnShoper on 2017/5/25.
 * //Collcetor queue
 */

@Component
public class CollcetorQueue {
    public static final BlockingDeque<Log> collcetorQueue = new LinkedBlockingDeque<>();
    @Autowired
    ESTransfer esTransfer;
    @Autowired
    HDFSTransfer hdfsTransfer;

    @PostConstruct
    public void init() {

        esTransfer.initilize();
        Thread thread = new Thread(() -> {
            Log poll;
            for (; !Thread.currentThread().isInterrupted(); ) {
                try {
                    while (Objects.isNull(poll = collcetorQueue.poll(3, TimeUnit.SECONDS))) ;
                    try {
                        esTransfer.write(poll);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    Thread.currentThread().interrupt();
                }
            }
        });
        thread.start();
    }
}
