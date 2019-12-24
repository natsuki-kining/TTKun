package com.natsuki_kining.ttkun.crawler.model.enums;

/**
 * TODO
 *
 * @Author natsuki_kining
 * @Date 2019/12/17 16:08
 **/
public enum RequestType {

    HTML_UNIT("htmlUnit"),
    JSONP("jsoup");

    private String value;

    private RequestType(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }
}
