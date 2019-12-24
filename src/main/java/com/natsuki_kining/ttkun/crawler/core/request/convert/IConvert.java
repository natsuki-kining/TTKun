package com.natsuki_kining.ttkun.crawler.core.request.convert;

import com.natsuki_kining.ttkun.crawler.core.request.AbstractRequest;

/**
 * TODO
 *
 * @Author natsuki_kining
 * @Date 2019/12/23 14:33
 **/
public interface IConvert {

    Object convert(AbstractRequest request, Object response);
}
