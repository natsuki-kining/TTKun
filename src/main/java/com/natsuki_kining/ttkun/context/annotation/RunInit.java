package com.natsuki_kining.ttkun.context.annotation;

import java.lang.annotation.*;

/**
 * IOC容器bean的初始化方法调用方法注解
 *
 * @Author natsuki_kining
 * @Date 2019/12/15 16:16
 * @Version 1.0.0
 **/
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RunInit {
}
