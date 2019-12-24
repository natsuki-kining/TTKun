package com.natsuki_kining.ttkun.crawler.core.request;

import com.natsuki_kining.ttkun.context.annotation.Autowired;
import com.natsuki_kining.ttkun.context.annotation.Component;
import com.natsuki_kining.ttkun.crawler.common.excption.RequestException;
import com.natsuki_kining.ttkun.crawler.core.request.convert.ConvertDelegate;
import com.natsuki_kining.ttkun.crawler.core.request.type.AbstractRequestType;
import com.natsuki_kining.ttkun.crawler.core.request.type.RequestTypeDelegate;
import com.natsuki_kining.ttkun.crawler.model.pojo.RequestPOJO;

/**
 * TODO
 *
 * @Author : natsuki_kining
 * @Date : 2019/12/19 0:25
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

    public Object doHttpRequest(RequestPOJO requestPOJO) throws RequestException {
        HttpRequest request = (HttpRequest)httpRequest.clone();
        request.setUrl(requestPOJO.getUrl());
        request.setReferer(requestPOJO.getReferer());
        Object response = doRequest(request);
        Object convert = convertDelegate.convert(requestPOJO,response);
        return convert;
    }
}
