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
public class OperateRule extends JsonRule {

    private String type;

    private Integer index;

    private PositionRule position;

    private DownloadRule download;

    private RequestRule request;

    private OperateRule lastStep;//上一步

    private OperateRule nextStep;//下一步

    private Element element;

}
