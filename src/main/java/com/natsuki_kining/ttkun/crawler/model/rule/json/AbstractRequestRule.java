package com.natsuki_kining.ttkun.crawler.model.rule.json;

import com.natsuki_kining.ttkun.crawler.model.rule.JsonRule;
import lombok.Data;

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

}
