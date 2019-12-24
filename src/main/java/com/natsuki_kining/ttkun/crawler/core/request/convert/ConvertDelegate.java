package com.natsuki_kining.ttkun.crawler.core.request.convert;

import com.natsuki_kining.ttkun.context.annotation.Autowired;
import com.natsuki_kining.ttkun.context.annotation.Component;
import com.natsuki_kining.ttkun.crawler.common.excption.RequestException;
import com.natsuki_kining.ttkun.crawler.model.pojo.RequestPOJO;

import java.util.Map;

/**
 * TODO
 *
 * @Author natsuki_kining
 * @Date 2019/12/23 14:47
 **/
@Component
public class ConvertDelegate {

    @Autowired
    private Map<String,IConvert> convertMap;

    public Object convert(RequestPOJO requestPOJO, Object response) {
        IConvert convert = convertMap.get(requestPOJO.getConvertType()+"Convert");
        if (convert == null){
            throw new RequestException("找不到 "+requestPOJO.getConvertType()+" 转换类型。");
        }
        return convert.convert(response);
    }

}
