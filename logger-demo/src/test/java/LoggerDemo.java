import com.daqsoft.log.core.config.Constans;
import com.daqsoft.log.util.LogFactory;
import com.daqsoft.log.util.Logger;
import com.daqsoft.log.util.annotation.LogModel;
import org.junit.Test;

import java.util.concurrent.TimeUnit;

/**
 * Created by ShawnShoper on 2017/5/asd.
 */
public class LoggerDemo {
    Logger logger = LogFactory.getLogger(LoggerDemo.class);
    @Test
    //这里添加LogModel注解标识 当前方法是属于什么模块.Eg:用户模块
    @LogModel("测试demo")
    public void log() throws InterruptedException {
        logger.info("测试info");
        logger.info("测试%s","info");
        logger.debug("测试debug");
        logger.debug("测试%s","debug");
        logger.error("测试error");
        logger.error("测试%s","error");
        logger.warn("测试warn");
        logger.warn("测试%s","warn");
        logger.log("测试自定级别", Constans.ERROR);
        //这里休眠2秒,便于日志处理器有时间去处理记录的日志
        TimeUnit.SECONDS.sleep(2);
    }
}
