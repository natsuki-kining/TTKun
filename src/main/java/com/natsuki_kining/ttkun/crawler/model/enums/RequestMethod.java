package com.natsuki_kining.ttkun.crawler.model.enums;

/**
 * RequestMethod
 *
 * @Author natsuki_kining
 * @Date 2019/12/23 10:14
 * @Version 1.0.0
 **/
public enum RequestMethod {

    GET("get"),
    POST("post");

    private String value;

    private RequestMethod(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }

}
