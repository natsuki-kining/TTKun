package com.natsuki_kining.ttkun.crawler.common.excption;

/**
 * 转换异常
 *
 * @Author natsuki_kining
 * @Date 2019/12/15 16:30
 **/
public class ConvertException extends RuntimeException {

    public ConvertException(String message){
        super(message);
    }

    public ConvertException(String message, Exception e){
        super(message,e);
    }
}
