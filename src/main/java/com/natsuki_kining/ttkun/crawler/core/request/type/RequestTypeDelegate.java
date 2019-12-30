package com.natsuki_kining.ttkun.crawler.core.request.type;

import com.natsuki_kining.ttkun.context.annotation.Autowired;
import com.natsuki_kining.ttkun.context.annotation.Component;
import com.natsuki_kining.ttkun.crawler.common.excption.AnalysisException;
import com.natsuki_kining.ttkun.crawler.core.request.AbstractRequest;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;

/**
 * 请求类型操作委派
 *
 * @Author natsuki_kining
 * @Date 2019/12/23 9:53
 **/
@Component
@Slf4j
public class RequestTypeDelegate extends AbstractRequestType {

    @Autowired
    private Map<String, AbstractRequestType> requestTypeMap;

    @Override
    public Object doRequest(AbstractRequest request) {
        log.info("使用：{} 发送方式。", request.getType());
        AbstractRequestType requestType = requestTypeMap.get(request.getType()+"Type");
        if (requestType == null) {
            throw new AnalysisException("没有该" + request.getType() + "发送方式。");
        }
        return requestType.doRequest(request);
    }


}
