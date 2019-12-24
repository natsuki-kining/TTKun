package com.natsuki_kining.ttkun.crawler.core.request;

import com.natsuki_kining.ttkun.context.annotation.Autowired;
import com.natsuki_kining.ttkun.context.annotation.Component;
import com.natsuki_kining.ttkun.crawler.common.excption.RequestException;
import com.natsuki_kining.ttkun.crawler.core.request.convert.ConvertDelegate;
import com.natsuki_kining.ttkun.crawler.core.request.type.AbstractRequestType;
import com.natsuki_kining.ttkun.crawler.core.request.type.RequestTypeDelegate;

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
        Object response = requestTypeDelegate.doRequest(request);
        return convertDelegate.convert(request,response);
    }

    public Object doHttpRequest(String url) throws RequestException {
        HttpRequest request = (HttpRequest)httpRequest.clone();
        request.setUrl(url);
        return doRequest(request);
    }
}
