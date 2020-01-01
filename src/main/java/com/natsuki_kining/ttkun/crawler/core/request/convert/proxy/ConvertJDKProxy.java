package com.natsuki_kining.ttkun.crawler.core.request.convert.proxy;

import com.natsuki_kining.ttkun.crawler.common.excption.ConvertException;
import com.natsuki_kining.ttkun.crawler.core.request.convert.IConvert;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * jdk动态代理
 * 判断转换对象是否为空
 *
 * @Author : natsuki_kining
 * @Date : 2019/12/25 23:27
 * @Version 1.0.0
 */
@Slf4j
public class ConvertJDKProxy implements InvocationHandler {

    private IConvert target;

    public ConvertJDKProxy(IConvert target) {
        this.target = target;
    }

    public IConvert getProxy() {
        return (IConvert) Proxy.newProxyInstance(target.getClass().getClassLoader(), target.getClass().getInterfaces(), this);
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if (args[1] == null) {
            throw new ConvertException("response is null!");
        }
        String jsonString = args[1].toString();
        if (StringUtils.isBlank(jsonString)) {
            throw new ConvertException("response is empty!");
        }
        return method.invoke(this.target, args);
    }
}
