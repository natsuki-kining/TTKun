package com.natsuki_kining.ttkun.context.annotation;

import java.lang.annotation.*;

/**
 * 扫包
 *
 * @Author natsuki_kining
 * @Date 2019/12/15 17:44
 **/
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ComponentScan {

    String[] scanPackages();
}
