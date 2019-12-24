package com.natsuki_kining.ttkun.crawler;

import com.natsuki_kining.ttkun.context.annotation.Autowired;
import com.natsuki_kining.ttkun.context.annotation.Component;
import com.natsuki_kining.ttkun.context.annotation.Run;
import com.natsuki_kining.ttkun.crawler.core.rule.RuleAction;
import lombok.extern.slf4j.Slf4j;

/**
 * 程序入口
 *
 * @Author natsuki_kining
 * @Date 2019/12/15 11:26
 **/
@Component
@Slf4j
public class CrawlerBoot {

    @Autowired
    private RuleAction ruleAction;

    @Run
    public void run(){
        ruleAction.action();
    }
}
