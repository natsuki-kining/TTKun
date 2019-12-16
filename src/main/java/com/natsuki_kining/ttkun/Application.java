package com.natsuki_kining.ttkun;

import com.natsuki_kining.ttkun.context.ApplicationBoot;
import com.natsuki_kining.ttkun.context.annotation.ComponentScan;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

/**
 * TODO
 *
 * @Author natsuki_kining
 * @Date 2019/12/15 14:51
 **/
@ComponentScan(scanPackages = {"com.natsuki_kining.ttkun.crawler"})
public class Application {

    public static void main(String[] args) {
        ApplicationBoot.run(Application.class,args);
    }
}
