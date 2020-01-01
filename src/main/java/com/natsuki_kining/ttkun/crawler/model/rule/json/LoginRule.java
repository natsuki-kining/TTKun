package com.natsuki_kining.ttkun.crawler.model.rule.json;

import com.natsuki_kining.ttkun.crawler.model.rule.JsonRule;
import lombok.Data;

/**
 * 登录规则对应的实体
 *
 * @Author : natsuki_kining
 * @Date : 2019/12/31 22:58
 * @Version 1.0.0
 */
@Data
public class LoginRule extends JsonRule {

    private String account;

    private String password;

    private String referer;

    private String loginUrl;

    private String loginAccountName;

    private String loginPasswordName;

    private String loginRequestMethod = "post";
}
