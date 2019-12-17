package com.natsuki_kining.ttkun.crawler.model.rule.json;

import com.natsuki_kining.ttkun.crawler.model.rule.JsonRule;
import lombok.Data;

/**
 * TODO
 *
 * @Author natsuki_kining
 * @Date 2019/12/17 16:28
 **/
@Data
public abstract class AbstractRequest extends JsonRule {

    private String url;
    private String name;
    private String referer;
    private String method;

}
