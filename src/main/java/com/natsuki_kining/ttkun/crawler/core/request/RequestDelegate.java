package com.natsuki_kining.ttkun.crawler.core.request;

import com.natsuki_kining.ttkun.context.annotation.Autowired;
import com.natsuki_kining.ttkun.context.annotation.Component;
import com.natsuki_kining.ttkun.crawler.common.excption.RequestException;
import com.natsuki_kining.ttkun.crawler.core.request.convert.ConvertDelegate;
import com.natsuki_kining.ttkun.crawler.core.request.type.AbstractRequestType;
import com.natsuki_kining.ttkun.crawler.core.request.type.RequestTypeDelegate;
import com.natsuki_kining.ttkun.crawler.model.pojo.RequestPOJO;
import com.natsuki_kining.ttkun.crawler.model.rule.json.RequestRule;
import org.apache.commons.lang3.StringUtils;

/**
 * 请求委派
 *
 * @Author : natsuki_kining
 * @Date : 2019/12/19 0:25
 * @Version 1.2.0
 */
@Component
public class RequestDelegate extends AbstractRequestType {

    @Autowired
    private RequestTypeDelegate requestTypeDelegate;

    @Autowired
    private ConvertDelegate convertDelegate;

    @Autowired
    private HttpRequest httpRequest;

    @Override
    protected Object doRequest(AbstractRequest request) throws RequestException {
        return requestTypeDelegate.doRequest(request);
    }

    public Object doHttpRequest(RequestRule requestRule) throws RequestException {
        HttpRequest request = (HttpRequest) httpRequest.clone();
        RequestPOJO requestPOJO = (RequestPOJO) requestRule.getData();
        request.setUrl((requestPOJO).getUrl());
        request.setReferer(requestPOJO.getReferer());
        if (StringUtils.isNotBlank(requestRule.getRequestType())){
            request.setType(requestRule.getRequestType());
        }
        Object response = doRequest(request);
        Object convert = convertDelegate.convert(requestRule, response);
        return convert;
    }
}
