package com.natsuki_kining.ttkun.crawler.core.rule.json;

import com.natsuki_kining.ttkun.context.annotation.Autowired;
import com.natsuki_kining.ttkun.context.annotation.Component;
import com.natsuki_kining.ttkun.crawler.common.excption.RuleException;
import com.natsuki_kining.ttkun.crawler.model.rule.json.Operate;
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
    public Object action(Operate operate) {
        Object object = getAction(operate).action(operate);
        return object;
    }

    private IOperateAction getAction(Operate operate){
        IOperateAction action = operateActionMap.get(operate.getType());
        if (action == null){
            throw new RuleException("action 为空。");
        }
        return action;
    }
}
