package com.daqsoft.log.util.annotation;

import java.lang.annotation.*;

@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Channel {
    //指定日志输出通道
    String value();
}
