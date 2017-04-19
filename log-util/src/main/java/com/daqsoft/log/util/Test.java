package com.daqsoft.log.util;

import com.daqsoft.log.util.annotation.LogModel;
import com.daqsoft.log.util.config.LogProperties;

/**
 * Created by ShawnShoper on 2017/4/17.
 */
@LogModel("测试class")
public class Test {
    private static Logger logger = LogFactory.getLogger(Test.class);

    //    static org.slf4j.Logger logger = LoggerFactory.getLogger(Test.class);
    public static void main(String[] args) {
//        logger.info("asd");
        LogProperties logProperties = new LogProperties();
        logProperties.setApplication("测试");
        logProperties.setHost("127.0.0.1");
        logProperties.setPort(8900);
        LogFactory.setLogConfig(logProperties);
//      logger.info("测试t111e");
        new Test().te();
    }

    @LogModel("测试method")
    public void te() {
        logger.info("测试te");
    }
}
