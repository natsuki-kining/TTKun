package com.natsuki_kining.ttkun.crawler.core.request.convert;

import com.alibaba.fastjson.JSON;
import com.natsuki_kining.ttkun.context.annotation.Component;
import com.natsuki_kining.ttkun.crawler.common.excption.ConvertException;
import com.natsuki_kining.ttkun.crawler.model.rule.json.RequestRule;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

/**
 * 转换成json
 *
 * @Author natsuki_kining
 * @Date 2019/12/15 16:26
 **/
@Component
@Slf4j
public class JsonConvert implements IConvert {

    @Override
    public Object convert(RequestRule requestRule, Object response) {
        if (response == null) {
            throw new ConvertException("response is null!");
        }
        String jsonString = response.toString();
        if (StringUtils.isBlank(jsonString)) {
            throw new ConvertException("response is empty!");
        }
        return JSON.parse(jsonString);
    }

}
