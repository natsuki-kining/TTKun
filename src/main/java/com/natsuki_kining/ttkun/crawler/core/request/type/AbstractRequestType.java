package com.natsuki_kining.ttkun.crawler.core.request.type;

import com.natsuki_kining.ttkun.crawler.common.excption.RequestException;
import com.natsuki_kining.ttkun.crawler.core.request.AbstractRequest;

/**
 * TODO
 *
 * @Author natsuki_kining
 * @Date 2019/12/23 9:55
 **/
public abstract class AbstractRequestType {

    protected abstract Object doRequest(AbstractRequest request) throws RequestException;

}
