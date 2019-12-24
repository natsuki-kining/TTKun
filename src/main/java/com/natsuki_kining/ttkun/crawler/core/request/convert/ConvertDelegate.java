package com.natsuki_kining.ttkun.crawler.core.request.convert;

import com.natsuki_kining.ttkun.context.annotation.Autowired;
import com.natsuki_kining.ttkun.context.annotation.Component;
import com.natsuki_kining.ttkun.crawler.common.excption.RequestException;
import com.natsuki_kining.ttkun.crawler.core.request.AbstractRequest;

import java.util.Map;

/**
 * TODO
 *
 * @Author natsuki_kining
 * @Date 2019/12/23 14:47
 **/
@Component
public class ConvertDelegate implements IConvert {

    @Autowired
    private Map<String,IConvert> convertMap;

    @Override
    public Object convert(AbstractRequest request,Object response) {
        IConvert convert = convertMap.get(request.getConvertType()+"Convert");
        if (convert == null){
            throw new RequestException("找不到 "+request.getConvertType()+" 转换类型。");
        }
        return convert.convert(request,response);
    }
}
