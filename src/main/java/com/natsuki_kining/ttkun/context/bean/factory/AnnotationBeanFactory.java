package com.natsuki_kining.ttkun.context.bean.factory;

import com.natsuki_kining.ttkun.context.bean.wrapper.AnnotationBeanWrapper;
import lombok.extern.slf4j.Slf4j;

/**
 * 注解Bean工厂
 *
 * @Author natsuki_kining
 * @Date 2019/12/15 17:25
 * @Version 1.0.0
 **/
@Slf4j
public class AnnotationBeanFactory implements BeanFactory {

    private static AnnotationBeanFactory annotationBeanFactory = new AnnotationBeanFactory();

    private AnnotationBeanFactory() {
    }

    public static AnnotationBeanFactory getInstance() {
        return annotationBeanFactory;
    }

    @Override
    public <T> T create(Class<T> beanClass) {
        Object o = null;
        try {
            o = beanClass.newInstance();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            System.exit(1);
        }
        return (T) o;
    }

    public AnnotationBeanWrapper createBeanWrapper() {
        AnnotationBeanWrapper annotationBeanWrapper = new AnnotationBeanWrapper();
        return annotationBeanWrapper;
    }
}
