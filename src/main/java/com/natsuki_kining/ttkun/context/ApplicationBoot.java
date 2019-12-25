package com.natsuki_kining.ttkun.context;

import lombok.extern.slf4j.Slf4j;

/**
 * 初始化参数、IOC容器，注册bean、实现DI等。
 *
 * @Author natsuki_kining
 * @Date 2019/12/15 17:41
 **/
@Slf4j
public class ApplicationBoot {

    public static void run(Class<?> applicationClass, String[] args) {
        try {
            //初始化参数,设置执行类型
            String[] initArguments = initArguments(args);

            //初始化容器
            ApplicationContext.getInstance().init(applicationClass,initArguments);

            //run
            ApplicationContext.getInstance().run();
        } catch (Exception e) {
            log.error(e.getMessage(),e);
            System.exit(1);
        }
    }

    private static String[] initArguments(String[] args) {
        return args;
    }

}
