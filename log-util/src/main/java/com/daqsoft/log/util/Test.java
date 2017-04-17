package com.daqsoft.log.util;

/**
 * Created by ShawnShoper on 2017/4/17.
 */
public class Test {
    private static Logger logger = LogFactory.getLogger(Test.class);

    public static void main(String[] args) {
        logger.info("测试te", new Log());
        new Test().te();
    }

    public void te() {
        logger.info("测试te", new Log());
    }
}
