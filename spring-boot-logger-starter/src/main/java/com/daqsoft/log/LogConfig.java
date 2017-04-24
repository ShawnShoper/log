package com.daqsoft.log;

import com.daqsoft.log.util.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

import javax.annotation.PostConstruct;

/**
 * Created by ShawnShoper on 2017/4/24.
 */
@EnableConfigurationProperties(com.daqsoft.log.LogProperties.class)
@Order(value = 0)
@Configuration
public class LogConfig {
    @Autowired
    LogProperties logProperties;
    @PostConstruct
    public void init(){
        LogFactory.setLogConfig(logProperties);
    }
}
