
package com.natsuki_kining.ttkun.context.annotation;

import java.lang.annotation.*;

/**
 * 如果是字符串数组接收，则自动根据逗号分隔
 *
 * @Author natsuki_kining
 * @Date 2019/12/15 23:07
 * @Version 1.0.0
 **/
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Value {

    String value() default "";

}
