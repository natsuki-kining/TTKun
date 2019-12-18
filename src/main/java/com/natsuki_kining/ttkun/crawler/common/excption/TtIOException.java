package com.natsuki_kining.ttkun.crawler.common.excption;

/**
 * 将io受检异常改为运行时异常、因为lamda表达式不能抛出异常
 *
 * @Author natsuki_kining
 * @Date 2019/12/15 16:30
 **/
public class TtIOException extends RuntimeException {

    public TtIOException(String message){
        super(message);
    }

    public TtIOException(String message, Exception e){
        super(message,e);
    }
}
