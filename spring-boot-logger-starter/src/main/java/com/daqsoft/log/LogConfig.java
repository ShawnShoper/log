package com.daqsoft.log;

import com.daqsoft.log.util.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

/**
 * Created by ShawnShoper on 2017/4/24.
 */
//@EnableConfigurationProperties(com.daqsoft.log.LogProperties.class)
//@Order(value = 0)
//@Configuration
public class LogConfig  implements CommandLineRunner {
    @Autowired
    LogProperties logProperties;
    @Override
    public void run(String... args) throws Exception {
        System.out.println("123");
        LogFactory.setLogConfig(logProperties);
    }
}
