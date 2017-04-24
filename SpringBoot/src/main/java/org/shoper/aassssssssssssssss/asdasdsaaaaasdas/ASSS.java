package org.shoper.aassssssssssssssss.asdasdsaaaaasdas;

import com.daqsoft.log.util.LogFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * Created by ShawnShoper on 2017/4/24.
 */
@Component
public class ASSS {
    com.daqsoft.log.util.Logger logger = LogFactory.getLogger(AnnotationConfigApplicationContextAnnotationConfigApplicationContext.class);
    Logger logger1 = LoggerFactory.getLogger(ASSS.class);
    @PostConstruct
    public void ss() {
        logger.info("sss");
        logger1.warn("warn");
        logger1.debug("debug");
        logger1.error("error");
    }
}
