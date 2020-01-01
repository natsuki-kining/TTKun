package com.natsuki_kining.ttkun.context.bean.factory;

/**
 * 生产Bean的工厂，由FactoryBean生成
 *
 * @Author natsuki_kining
 * @Date 2019/12/15 17:23
 * @Version 1.0.0
 **/
public interface BeanFactory {

    <T> Object create(Class<T> beanClass) throws Exception;
}
