package com.daqsoft.log.asd;

import com.daqsoft.log.util.LogFactory;
import com.daqsoft.log.util.Logger;
import com.daqsoft.log.util.annotation.LogModel;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;

/**
 * Created by ShawnShoper on 2017/5/3.
 */
@RestController
public class Applicatio {
    Logger logger = LogFactory.getLogger(Applicatio.class);

    @PostConstruct
    public void init() {
        System.out.println("s");
    }

    @LogModel("test123")
    @RequestMapping("/sss")
    public String hi() {
        logger.info("asdadadaasd");
        return "hello world";
    }
}
