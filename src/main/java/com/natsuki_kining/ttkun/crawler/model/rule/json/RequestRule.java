package com.natsuki_kining.ttkun.crawler.model.rule.json;

import lombok.Data;

/**
 * 发送请求操作
 *
 * @Author natsuki_kining
 * @Date 2019/12/15 23:09
 * @Version 1.1.1
 **/
@Data
public class RequestRule extends AbstractRequestRule {

    private String urlName = "title";
}
