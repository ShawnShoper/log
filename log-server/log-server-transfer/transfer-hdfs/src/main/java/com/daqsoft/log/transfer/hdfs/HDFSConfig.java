package com.daqsoft.log.transfer.hdfs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by ShawnShoper on 2017/6/2.
 */
@Configuration
public class HDFSConfig {
    @Autowired
    HDFSProperties hdfsProperties;

    @Bean
    public HDFSTransfer hdfsTransfer(HDFSProperties hdfsProperties) {
        return new HDFSTransfer(hdfsProperties);
    }
}
