package com.natsuki_kining.ttkun.crawler.core.rule.json;

import com.natsuki_kining.ttkun.context.annotation.Autowired;
import com.natsuki_kining.ttkun.context.annotation.Component;
import com.natsuki_kining.ttkun.crawler.common.excption.RuleException;
import com.natsuki_kining.ttkun.crawler.model.rule.json.OperateRule;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;

/**
 * TODO
 *
 * @Author natsuki_kining
 * @Date 2019/12/17 16:32
 **/
@Component("operate")
@Slf4j
public class OperateAction implements IOperateAction {

    @Autowired
    private Map<String,IOperateAction> operateActionMap;

    @Override
    public Object action(OperateRule operateRule) {
        return getAction(operateRule).action(operateRule);
    }

    private IOperateAction getAction(OperateRule operateRule){
        IOperateAction action = operateActionMap.get(operateRule.getType());
        if (action == null){
            throw new RuleException("action 为空。");
        }
        return action;
    }
}
