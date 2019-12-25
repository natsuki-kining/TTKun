package com.natsuki_kining.ttkun.crawler.model.rule.json;

import com.natsuki_kining.ttkun.crawler.model.rule.JsonRule;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

/**
 * 发送请求的抽象类
 *
 * @Author natsuki_kining
 * @Date 2019/12/17 16:28
 **/
@Data
public abstract class AbstractRequestRule extends JsonRule {

    private String url;
    private String urlAttr;
    private String referer;
    private String method;
    private String convertType = "html";//转换类型。html转成document。json转成jsonObject

}
