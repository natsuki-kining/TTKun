package com.natsuki_kining.ttkun.crawler.model.enums;

/**
 * PositionType
 *
 * @Author natsuki_kining
 * @Date 2019/12/17 16:08
 * @Version 1.0.0
 **/
public enum PositionType {

    /**
     * 属性
     */
    ATTRIBUTE("attribute"),
    /**
     * 标签
     */
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
