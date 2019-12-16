package com.natsuki_kining.ttkun.context.bean.factory;

/**
 * 生产Bean的工厂，由FactoryBean生成
 *
 * @Author natsuki_kining
 * @Date 2019/12/15 17:23
 **/
public interface BeanFactory {

    Object create(Class<?> beanClass) throws Exception;
}
