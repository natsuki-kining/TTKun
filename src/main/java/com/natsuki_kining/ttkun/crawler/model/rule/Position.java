package com.natsuki_kining.ttkun.crawler.model.rule;

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


}
