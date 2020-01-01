package com.natsuki_kining.ttkun.context.annotation;

import java.lang.annotation.*;

/**
 * IOC 交给context创建对象
 *
 * @Author natsuki_kining
 * @Date 2019/12/15
 * @Version 1.0.0
 **/
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Component {

    String value() default "";

    String beanMapKey() default "";
}