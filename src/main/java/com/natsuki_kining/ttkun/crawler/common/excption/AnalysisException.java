package com.natsuki_kining.ttkun.crawler.common.excption;

/**
 * 解析异常
 *
 * @Author natsuki_kining
 * @Date 2019/12/15 16:30
 **/
public class AnalysisException extends RuntimeException {

    public AnalysisException(String message){
        super(message);
    }

    public AnalysisException(String message,Exception e){
        super(message,e);
    }
}
