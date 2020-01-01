package com.natsuki_kining.ttkun.crawler.concurrent;

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
 * @Version 1.0.0
 */
@Component
public class FixedThreadPool {


    @Value
    private Integer downloadMangaThreadPoolSize;
    @Value
    private Integer downloadChapterThreadPoolSize;

    public ExecutorService downloadMangaThreadPool;
    public ExecutorService downloadChapterThreadPool;

    @RunInit
    public void init() {
        downloadMangaThreadPool = Executors.newFixedThreadPool(downloadMangaThreadPoolSize);
        downloadChapterThreadPool = Executors.newFixedThreadPool(downloadChapterThreadPoolSize);
    }

}
