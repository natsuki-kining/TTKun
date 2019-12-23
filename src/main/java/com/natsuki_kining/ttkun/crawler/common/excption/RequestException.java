package com.natsuki_kining.ttkun.crawler.common.excption;

/**
 * 请求异常
 *
 * @Author natsuki_kining
 * @Date 2019/12/15 16:30
 **/
public class RequestException extends RuntimeException {

    public RequestException(String message){
        super(message);
    }

    public RequestException(String message, Exception e){
        super(message,e);
    }
}
