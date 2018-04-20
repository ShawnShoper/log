package com.daqsoft.log;

import com.daqsoft.log.util.LogFactory;
import com.daqsoft.log.util.Logger;

public class HelloWorld {
    private static final Logger LOGGER = LogFactory.getLogger(HelloWorld.class);

    public static void main(String[] args) {
        LOGGER.info("hello,world!");

        String name = "ShawnShoper";
        int age = 12;
        LOGGER.info("My name is %s and %d years old", name, age);
    }
}
