package com.natsuki_kining.ttkun.crawler.common.excption;

/**
 * 下载异常
 *
 * @Author natsuki_kining
 * @Date 2019/12/15 16:30
 **/
public class DownloadException extends RuntimeException {

    public DownloadException(String message){
        super(message);
    }

    public DownloadException(String message, Exception e){
        super(message,e);
    }
}
