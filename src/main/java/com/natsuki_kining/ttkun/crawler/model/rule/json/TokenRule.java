package com.natsuki_kining.ttkun.crawler.model.rule.json;

import com.natsuki_kining.ttkun.crawler.model.rule.JsonRule;
import lombok.Data;

import java.util.Map;

/**
 * 用户登录的token
 *
 * @Author : natsuki_kining
 * @Date : 2019/12/31 22:58
 * @Version 1.1.0
 */
@Data
public class TokenRule extends JsonRule {

    private Map<String, String> headers;

    private Map<String, String> cookies;

}
