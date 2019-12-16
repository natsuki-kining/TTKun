package com.natsuki_kining.ttkun.context.annotation;

import java.lang.annotation.*;

/**
 * 业务代码的启动入口
 *
 * @Author natsuki_kining
 * @Date 2019/12/15 11:01
 **/
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Run {
}
