package com.natsuki_kining.ttkun.crawler.core.rule;

import com.natsuki_kining.ttkun.context.annotation.Autowired;
import com.natsuki_kining.ttkun.context.annotation.Component;
import com.natsuki_kining.ttkun.context.annotation.Run;
import com.natsuki_kining.ttkun.context.annotation.Value;
import com.natsuki_kining.ttkun.crawler.model.rule.Rule;

import java.util.Map;

/**
 * TODO
 *
 * @Author natsuki_kining
 * @Date 2019/12/24 17:24
 **/
@Component
public class RuleAction extends AbstractRuleAction {

    @Autowired
    private Map<String,AbstractRuleAction> ruleActionMap;

    @Value("rule.action.type")
    private String type;

    @Override
    public void action() {
        ruleActionMap.get(type).action();
    }

    @Override
    public Rule getRule() {
        return ruleActionMap.get(type).getRule();
    }

    @Override
    public Rule getRule(String ruleFile) {
        return ruleActionMap.get(type).getRule(ruleFile);
    }
}
