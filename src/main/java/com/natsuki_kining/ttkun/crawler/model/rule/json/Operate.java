package com.natsuki_kining.ttkun.crawler.model.rule.json;

import com.natsuki_kining.ttkun.crawler.model.rule.JsonRule;
import lombok.Data;
import org.jsoup.nodes.Element;

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

    private Operate lastStep;//上一步

    private Operate nextStep;//下一步

    private Element element;

}
