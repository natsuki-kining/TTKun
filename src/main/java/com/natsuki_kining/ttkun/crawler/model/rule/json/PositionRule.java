package com.natsuki_kining.ttkun.crawler.model.rule.json;

import com.natsuki_kining.ttkun.crawler.model.rule.JsonRule;
import lombok.Data;

import java.util.List;

/**
 * 定位
 *
 * @Author natsuki_kining
 * @Date 2019/12/15 23:03
 * @Version 1.0.0
 **/
@Data
public class PositionRule extends JsonRule {

    private String type;
    private String key;
    private String value;
    private Integer index;

}
