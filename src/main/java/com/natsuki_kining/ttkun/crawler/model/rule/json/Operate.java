package com.natsuki_kining.ttkun.crawler.model.rule.json;

import com.natsuki_kining.ttkun.crawler.model.rule.JsonRule;
import lombok.Data;

/**
 * 一次操作，指向下一次操作
 *
 * @Author natsuki_kining
 * @Date 2019/12/15 23:02
 **/
@Data
public class Operate extends JsonRule {

    private String type;

    private Position position;

    private Download download;

    private Request request;

    private Operate operate;
}
