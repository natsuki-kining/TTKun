package com.natsuki_kining.ttkun.crawler.core.rule.json;

import com.natsuki_kining.ttkun.crawler.model.rule.json.Operate;

/**
 * TODO
 *
 * @Author natsuki_kining
 * @Date 2019/12/17 16:36
 **/
public interface IOperateAction {

    Object action(Operate operate);
}
