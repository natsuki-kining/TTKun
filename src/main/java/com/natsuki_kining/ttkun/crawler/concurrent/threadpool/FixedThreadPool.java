package com.natsuki_kining.ttkun.crawler.concurrent.threadpool;

import com.natsuki_kining.ttkun.context.annotation.Component;
import com.natsuki_kining.ttkun.context.annotation.RunInit;
import com.natsuki_kining.ttkun.context.annotation.Value;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 定长线程池
 *
 * @Author : natsuki_kining
 * @Date : 2019/12/28 0:03
 */
@Component
public class FixedThreadPool {

    @Value("thread.pool.size")
    private Integer size;

    public ExecutorService threadPool;

    @RunInit
    public void init() {
        threadPool = Executors.newFixedThreadPool(size);
    }

}
