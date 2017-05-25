package com.daqsoft.log.asd;

import com.daqsoft.log.util.LogFactory;
import com.daqsoft.log.util.Logger;
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
        logger.info("123");
        System.out.println("s");
    }

    @RequestMapping("/sss")
    public String hi() {
        logger.info("asdadadaasd");
        return "hello world";
    }
}
