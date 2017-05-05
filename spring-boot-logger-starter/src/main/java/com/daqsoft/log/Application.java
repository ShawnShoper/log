package com.daqsoft.log;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.concurrent.TimeUnit;

/**
 * Created by ShawnShoper on 2017/5/3.
 */
@SpringBootApplication
public class Application {
    public static void main(String[] args) throws InterruptedException {
        SpringApplication.run(Application.class,args);
        TimeUnit.SECONDS.sleep(2);
    }
}
