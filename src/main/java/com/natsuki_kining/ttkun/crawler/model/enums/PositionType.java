package com.natsuki_kining.ttkun.crawler.model.enums;

/**
 * TODO
 *
 * @Author natsuki_kining
 * @Date 2019/12/17 16:08
 **/
public enum PositionType {

    ATTRIBUTE("attribute"),
    TAG("tag");

    private String value;

    private PositionType(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }
}
