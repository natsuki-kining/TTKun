package com.natsuki_kining.ttkun.crawler.core.rule.json;

import com.alibaba.fastjson.JSONObject;
import com.natsuki_kining.ttkun.context.annotation.Autowired;
import com.natsuki_kining.ttkun.context.annotation.Component;
import com.natsuki_kining.ttkun.crawler.core.request.HttpRequest;
import com.natsuki_kining.ttkun.crawler.core.request.type.RequestTypeDelegate;
import com.natsuki_kining.ttkun.crawler.model.rule.json.OperateRule;
import com.natsuki_kining.ttkun.crawler.model.rule.json.TokenRule;
import lombok.extern.slf4j.Slf4j;

/**
 * 设置head跟cookie
 *
 * @Author : natsuki_kining
 * @Date : 2019/12/31 23:00
 * @Version 1.1.0
 */
@Component
@Slf4j
public class TokenAction implements IOperateAction {

    @Autowired
    private RequestTypeDelegate requestTypeDelegate;

    @Autowired
    private HttpRequest httpRequest;

    @Override
    public Object action(OperateRule operateRule) {
        TokenRule token = operateRule.getToken();
        httpRequest.setHeaders(token.getHeaders());
        httpRequest.setCookies(token.getCookies());
        return new JSONObject();
    }

    @Override
    public Object init(OperateRule operateRule) {
        return null;
    }


}
