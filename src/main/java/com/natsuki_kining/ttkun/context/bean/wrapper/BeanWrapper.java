package com.natsuki_kining.ttkun.context.bean.wrapper;

import lombok.Data;

/**
 * 包装Bean
 *
 * @Author natsuki_kining
 * @Date 2019/12/15 17:15
 **/
@Data
public class BeanWrapper {

    protected Object beanInstance;

    protected Class<?> beanClass;

    protected String beanName;

    protected Object beanMapKey;

}
