package com.natsuki_kining.ttkun.crawler.core.rule;

import com.natsuki_kining.ttkun.crawler.model.rule.Rule;

/**
 * 自定义规则
 *
 * @Author natsuki_kining
 * @Date 2019/12/15 11:26
 * @Version 1.0.0
 **/
public abstract class AbstractRuleAction {

    public abstract void action();

    public abstract Rule getRule();

}
