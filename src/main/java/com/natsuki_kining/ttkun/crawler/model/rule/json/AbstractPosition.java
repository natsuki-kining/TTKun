package com.natsuki_kining.ttkun.crawler.model.rule.json;

import lombok.Data;

/**
 * TODO
 *
 * @Author : natsuki_kining
 * @Date : 2019/12/16 23:55
 */
@Data
public abstract class AbstractPosition {

    private String key;
    private String value;
    private Integer index;
    private Integer order;
}
