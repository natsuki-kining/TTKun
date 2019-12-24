package com.natsuki_kining.ttkun.crawler.common.excption;

/**
 * 克隆异常
 *
 * @Author natsuki_kining
 * @Date 2019/12/24 16:30
 **/
public class CloneException extends RuntimeException {

    public CloneException(String message){
        super(message);
    }

    public CloneException(String message, Exception e){
        super(message,e);
    }
}
