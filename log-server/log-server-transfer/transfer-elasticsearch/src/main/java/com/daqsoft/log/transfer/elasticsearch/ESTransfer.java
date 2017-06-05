package com.daqsoft.log.transfer.elasticsearch;

import com.daqsoft.log.core.serialize.Log;
import com.daqsoft.log.transfer.Transfer;
import org.apache.kafka.common.security.auth.Login;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * Created by ShawnShoper on 2017/5/25.
 */
@Component
public class ESTransfer implements Transfer {

    @Override
    public void initilize() {

    }

    @Autowired
    LogRepository logRepository;

    @Override
    public void write(Log log) throws IOException {
        LogIndex logIndex = new LogIndex(log);
        logRepository.save(logIndex);
    }

    @Override
    public void destory() {

    }
}
