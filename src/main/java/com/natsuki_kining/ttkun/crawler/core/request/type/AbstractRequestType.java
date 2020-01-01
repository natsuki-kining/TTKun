package com.natsuki_kining.ttkun.crawler.core.request.type;

import com.natsuki_kining.ttkun.crawler.common.excption.RequestException;
import com.natsuki_kining.ttkun.crawler.core.request.AbstractRequest;

/**
 * 请求类型抽象类
 *
 * @Author natsuki_kining
 * @Date 2019/12/23 9:55
 * @Version 1.0.0
 **/
public abstract class AbstractRequestType {

    protected abstract Object doRequest(AbstractRequest request) throws RequestException;

}
