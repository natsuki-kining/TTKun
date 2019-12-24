package com.natsuki_kining.ttkun.crawler.common.excption;

/**
 * 解析规则异常
 *
 * @Author natsuki_kining
 * @Date 2019/12/15 16:30
 **/
public class RuleException extends RuntimeException {

    public RuleException(String message){
        super(message);
    }

    public RuleException(String message, Exception e){
        super(message,e);
    }
}
