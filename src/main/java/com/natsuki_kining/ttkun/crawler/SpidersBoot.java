package com.natsuki_kining.ttkun.crawler;

import com.natsuki_kining.ttkun.context.annotation.Component;
import com.natsuki_kining.ttkun.context.annotation.Run;
import lombok.extern.slf4j.Slf4j;

/**
 * TODO
 *
 * @Author natsuki_kining
 * @Date 2019/12/15 11:26
 **/
@Component
@Slf4j
public class SpidersBoot {

    @Run
    public void run(){
        log.info("run method");
    }
}
