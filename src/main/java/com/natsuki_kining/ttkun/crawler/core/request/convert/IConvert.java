package com.natsuki_kining.ttkun.crawler.core.request.convert;

import com.natsuki_kining.ttkun.crawler.model.rule.json.RequestRule;

/**
 * 转换接口
 *
 * @Author natsuki_kining
 * @Date 2019/12/23 14:33
 **/
public interface IConvert {

    Object convert(RequestRule requestRule, Object response);
}
