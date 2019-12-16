package com.natsuki_kining.ttkun.crawler.model.rule;

import lombok.Data;

/**
 * 获取属性值操作
 *
 * @Author natsuki_kining
 * @Date 2019/12/15 23:05
 **/
@Data
public class Attribute {

    private String key;
    private String value;
    private Integer index;
    private Integer order;
}
