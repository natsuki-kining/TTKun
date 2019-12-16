package com.natsuki_kining.ttkun.context.bean.wrapper;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.lang.annotation.Annotation;

/**
 * 注解包装Bean
 *
 * @Author natsuki_kining
 * @Date 2019/12/15 21:46
 **/
@Data
@Slf4j
public class AnnotationBeanWrapper extends BeanWrapper {

    private Annotation[] annotations;

}
