package com.natsuki_kining.ttkun.crawler.model.rule.json;

import com.natsuki_kining.ttkun.crawler.model.rule.JsonRule;
import lombok.Data;

import java.util.List;

/**
 * 定位
 *
 * @Author natsuki_kining
 * @Date 2019/12/15 23:03
 **/
@Data
public class Position extends JsonRule {

    private String type;
    private String key;
    private String value;
    private Integer index;


    /*private List<Attribute> attributes;

    private List<Tag> tags;

    public boolean fieldIsNull(){
        return (attributes == null && attributes.size() == 0
        && tags == null && tags.size() == 0);
    }*/
}
