package com.natsuki_kining.ttkun.crawler.core.rule.json;

import com.natsuki_kining.ttkun.context.annotation.Autowired;
import com.natsuki_kining.ttkun.context.annotation.Component;
import com.natsuki_kining.ttkun.crawler.common.excption.RuleException;
import com.natsuki_kining.ttkun.crawler.core.rule.json.proxy.ActionJDKProxy;
import com.natsuki_kining.ttkun.crawler.model.rule.json.OperateRule;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;

/**
 * 操作委派
 *
 * @Author natsuki_kining
 * @Date 2019/12/17 16:32
 * @Version 1.0.0
 **/
@Component
@Slf4j
public class OperateAction implements IOperateAction {

    @Autowired
    private Map<String, IOperateAction> operateActionMap;

    @Override
    public Object action(OperateRule operateRule) {
        IOperateAction proxy = new ActionJDKProxy(getAction(operateRule)).getProxy();
        return proxy.action(operateRule);
    }

    @Override
    public Object init(OperateRule operateRule) {
        return null;
    }

    private IOperateAction getAction(OperateRule operateRule) {
        IOperateAction action = operateActionMap.get(operateRule.getType() + "Action");
        if (action == null) {
            throw new RuleException("找不到 " + operateRule.getType() + " action。");
        }
        return action;
    }
}
