package com.natsuki_kining.ttkun.crawler.core.rule.json;

import com.natsuki_kining.ttkun.crawler.model.rule.json.OperateRule;

/**
 * 操作接口
 *
 * @Author natsuki_kining
 * @Date 2019/12/17 16:36
 * @Version 1.0.0
 **/
public interface IOperateAction {

    Object action(OperateRule operateRule);

    Object init(OperateRule operateRule);
}
