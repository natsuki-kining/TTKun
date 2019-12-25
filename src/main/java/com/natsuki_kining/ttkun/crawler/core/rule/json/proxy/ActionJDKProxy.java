package com.natsuki_kining.ttkun.crawler.core.rule.json.proxy;

import com.natsuki_kining.ttkun.crawler.common.excption.RuleException;
import com.natsuki_kining.ttkun.crawler.core.rule.json.IOperateAction;
import com.natsuki_kining.ttkun.crawler.model.rule.Rule;
import com.natsuki_kining.ttkun.crawler.model.rule.json.OperateRule;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * jdk动态代理
 * 判断操作对象是否为空
 *
 * @Author : natsuki_kining
 * @Date : 2019/12/25 23:27
 */
@Slf4j
public class ActionJDKProxy implements InvocationHandler {

    private IOperateAction target;

    public ActionJDKProxy(IOperateAction target) {
        this.target = target;
    }

    public IOperateAction getProxy() {
        return (IOperateAction) Proxy.newProxyInstance(target.getClass().getClassLoader(), target.getClass().getInterfaces(), this);
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if(args[0] == null){
            throw new RuleException("operate is null!");
        }
        OperateRule operateRules = (OperateRule)args[0];
        Field field = OperateRule.class.getDeclaredField(operateRules.getType());
        field.setAccessible(true);
        if(field.get(operateRules) == null){
            throw new RuleException(operateRules.getType()+"Rule is null!");
        }

        Object init = target.init(operateRules);
        Rule rule = (Rule)field.get(operateRules);
        rule.setData(init);
        return method.invoke(this.target,args);
    }
}
