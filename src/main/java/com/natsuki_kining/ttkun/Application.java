package com.natsuki_kining.ttkun;

import com.natsuki_kining.ttkun.context.ApplicationBoot;
import com.natsuki_kining.ttkun.context.annotation.ComponentScan;

/**
 * 程序启动入口
 *
 * @Author natsuki_kining
 * @Date 2019/12/15 14:51
 * @Version 1.0.0
 **/
@ComponentScan(scanPackages = {"com.natsuki_kining.ttkun.crawler"})
public class Application {

    public static void main(String[] args) {
        ApplicationBoot.run(Application.class, args);
    }
}
