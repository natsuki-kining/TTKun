package com.natsuki_kining.ttkun.ui;

import com.natsuki_kining.ttkun.context.annotation.Run;

/**
 * TODO
 *
 * @Author natsuki_kining
 * @Date 2020/5/4 17:40
 **/
//@Component
public class SummerUIBoot {

    @Run
    public void run(){
        Class<?>[] classes = Object.class.getClasses();
        for (Class<?> clazz : classes) {
            System.out.println(clazz.toString());
        }
        System.out.println("summer ui");

        System.out.println(System.getProperty("os.name"));
    }
}
