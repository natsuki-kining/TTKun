package com.natsuki_kining.ttkun.crawler.core.rule.json;

import ch.qos.logback.core.joran.util.beans.BeanUtil;
import com.alibaba.fastjson.JSON;
import com.natsuki_kining.ttkun.context.annotation.Autowired;
import com.natsuki_kining.ttkun.context.annotation.Component;
import com.natsuki_kining.ttkun.crawler.core.request.HttpRequest;
import com.natsuki_kining.ttkun.crawler.core.request.type.RequestTypeDelegate;
import com.natsuki_kining.ttkun.crawler.core.request.type.http.JsoupType;
import com.natsuki_kining.ttkun.crawler.model.enums.RequestMethod;
import com.natsuki_kining.ttkun.crawler.model.rule.json.LoginRule;
import com.natsuki_kining.ttkun.crawler.model.rule.json.OperateRule;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;

/**
 * 登录获取token或者cookie
 *
 * @Author : natsuki_kining
 * @Date : 2019/12/31 23:00
 */
@Component
@Slf4j
public class LoginAction implements IOperateAction {

    @Autowired
    private RequestTypeDelegate requestTypeDelegate;

    @Autowired
    private HttpRequest httpRequest;

    @Override
    public Object action(OperateRule operateRule) {
        //深克隆
        HttpRequest request = JSON.parseObject(JSON.toJSONString(httpRequest),HttpRequest.class);
        Map<String,String> parameters = new HashMap<>();
        LoginRule loginRule = operateRule.getLogin();
        parameters.put(loginRule.getLoginAccountName(),loginRule.getAccount());
        parameters.put(loginRule.getLoginPasswordName(),loginRule.getPassword());
        request.setUrl(loginRule.getLoginUrl());
        request.setParameters(parameters);
        request.setMethod(RequestMethod.POST);
        Object o = requestTypeDelegate.doRequest(request);
        return operateRule.getOperateData();
    }

    @Override
    public Object init(OperateRule operateRule) {
        return null;
    }


}
