package com.natsuki_kining.ttkun.context.annotation;

import java.lang.annotation.*;

/**
 * DI 自动注入属性
 *
 * @Author natsuki_kining
 * @Date 2019/12/15
 * @Version 1.0.0
 **/
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Autowired {

    String value() default "";

}
