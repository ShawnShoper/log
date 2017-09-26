package com.daqsoft.log.util.annotation;

import java.lang.annotation.*;

/**
 * Created by ShawnShoper on 2017/4/18.
 */
@Target({ElementType.METHOD,ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface LogModel {
    String value() default "";
}
