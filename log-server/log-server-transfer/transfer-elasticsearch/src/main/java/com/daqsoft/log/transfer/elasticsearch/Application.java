package com.daqsoft.log.transfer.elasticsearch;

import com.daqsoft.log.core.serialize.Business;
import com.daqsoft.log.core.serialize.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.io.IOException;

@SpringBootApplication
@RestController
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class,args);
    }
    @Autowired
    ESTransfer esTransfer;
    @RequestMapping("/call")
    public String ss() throws IOException {
        Log log = new Log();
        log.setApplication("test");
        Business business = new Business();
        business.setContent("this is a judge");
        business.setLevel("Info");
        log.setBusiness(business);
        log.setTime(System.currentTimeMillis());
        log.setClassName("org.shoper.test.b.Pb");
        log.setLineNumber(88);
        log.setPid(7025);
        esTransfer.write(log);
        return "seccuss";
    }
}
