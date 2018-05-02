package com.daqsoft.log;

import com.daqsoft.log.util.LogFactory;
import com.daqsoft.log.util.LogInfo;
import com.daqsoft.log.util.Logger;
import com.daqsoft.log.util.annotation.Channel;
import com.daqsoft.log.util.annotation.LogModel;

@Channel("hello-world")
public class HelloWorld {
    private static final Logger LOGGER = LogFactory.getLogger(HelloWorld.class);
    @LogModel("some business")
    public static void main(String[] args) {
        LOGGER.info("channel",new LogInfo());
        LOGGER.info("hello,world!");
        String name = "ShawnShoper";
        int age = 12;
        LOGGER.info("My name is %s and %d years old", name, age);
    }
}
