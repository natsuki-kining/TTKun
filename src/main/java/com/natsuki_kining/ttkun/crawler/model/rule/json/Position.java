package com.natsuki_kining.ttkun.crawler.model.rule.json;

import lombok.Data;

import java.util.List;

/**
 * 定位
 *
 * @Author natsuki_kining
 * @Date 2019/12/15 23:03
 **/
@Data
public class Position {

    private List<Attribute> attributes;

    private List<Tag> tags;

    public boolean fieldIsNull(){
        return (attributes == null && attributes.size() == 0
        && tags == null && tags.size() == 0);
    }
}
