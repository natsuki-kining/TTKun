package com.natsuki_kining.ttkun.crawler.core.request.convert;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.natsuki_kining.ttkun.context.annotation.Component;
import com.natsuki_kining.ttkun.crawler.core.request.AbstractRequest;
import lombok.extern.slf4j.Slf4j;

/**
 * TODO
 *
 * @Author natsuki_kining
 * @Date 2019/12/15 16:26
 **/
@Component
@Slf4j
public class JsonConvert implements IConvert {

    @Override
    public Object convert(AbstractRequest request, Object response) {
        JSONObject jsonObject = JSON.parseObject(response.toString());
        return jsonObject;
    }

}
