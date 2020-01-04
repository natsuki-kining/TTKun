package com.natsuki_kining.ttkun.crawler.concurrent;

import com.natsuki_kining.ttkun.context.annotation.Component;
import com.natsuki_kining.ttkun.context.annotation.RunInit;
import com.natsuki_kining.ttkun.context.annotation.Value;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * 定长线程池
 *
 * @Author : natsuki_kining
 * @Date : 2019/12/28 0:03
 * @UpdateDate 2020/1/4 16:25:00
 * @Version 1.1.0
 */
@Component
public class FixedThreadPool {


    @Value
    private Integer downloadMangaThreadPoolSize;
    @Value
    private Integer downloadChapterThreadPoolSize;
    @Value
    private Integer downloadMangaThreadPoolTimeout;
    @Value
    private Integer downloadChapterThreadPoolTimeout;

    public ExecutorService downloadMangaThreadPool;
    public ExecutorService downloadChapterThreadPool;

    @RunInit
    public void init() {
        downloadMangaThreadPool = Executors.newFixedThreadPool(downloadMangaThreadPoolSize);
        downloadChapterThreadPool = Executors.newFixedThreadPool(downloadChapterThreadPoolSize);
    }

    public void mangaThreadPoolAwait() throws InterruptedException {
        downloadMangaThreadPool.awaitTermination(downloadMangaThreadPoolTimeout, TimeUnit.SECONDS);
        downloadMangaThreadPool.shutdown();
    }

    public void chapterThreadPoolAwait() throws InterruptedException {
        downloadChapterThreadPool.awaitTermination(downloadChapterThreadPoolTimeout, TimeUnit.SECONDS);
        downloadChapterThreadPool.shutdown();
    }

    public void await() throws InterruptedException {
        mangaThreadPoolAwait();
        chapterThreadPoolAwait();
    }
}
