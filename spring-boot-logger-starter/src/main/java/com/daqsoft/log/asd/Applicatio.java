package com.daqsoft.log.asd;

import com.daqsoft.log.util.LogFactory;
import com.daqsoft.log.util.Logger;
import org.springframework.stereotype.Controller;

import javax.annotation.PostConstruct;

/**
 * Created by ShawnShoper on 2017/5/3.
 */
@Controller
public class Applicatio {
    Logger logger = LogFactory.getLogger(Applicatio.class);
    @PostConstruct
    public void init(){
        logger.info("123");
        System.out.println("s");
    }
}
