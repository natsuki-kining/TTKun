package com.natsuki_kining.ttkun.crawler.core.request.convert;

import com.natsuki_kining.ttkun.context.annotation.Autowired;
import com.natsuki_kining.ttkun.context.annotation.Component;
import com.natsuki_kining.ttkun.crawler.common.excption.RequestException;
import com.natsuki_kining.ttkun.crawler.core.request.convert.proxy.ConvertJDKProxy;
import com.natsuki_kining.ttkun.crawler.model.rule.json.RequestRule;

import java.util.Map;

/**
 * 转换委派
 *
 * @Author natsuki_kining
 * @Date 2019/12/23 14:47
 * @Version 1.0.0
 **/
@Component
public class ConvertDelegate implements IConvert {

    @Autowired
    private Map<String, IConvert> convertMap;

    @Override
    public Object convert(RequestRule requestRule, Object response) {
        IConvert convert = convertMap.get(requestRule.getConvertType() + "Convert");
        if (convert == null) {
            throw new RequestException("找不到 " + requestRule.getConvertType() + " 转换类型。");
        }
        IConvert convertProxy = new ConvertJDKProxy(convert).getProxy();
        return convertProxy.convert(requestRule, response);
    }

}
