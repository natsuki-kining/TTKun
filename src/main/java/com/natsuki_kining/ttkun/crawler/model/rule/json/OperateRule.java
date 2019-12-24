package com.natsuki_kining.ttkun.crawler.model.rule.json;

import com.natsuki_kining.ttkun.crawler.common.excption.CloneException;
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
public class OperateRule extends JsonRule implements Cloneable {

    private String type;

    private Integer index;

    private PositionRule position;

    private DownloadRule download;

    private RequestRule request;

    private OperateRule lastStep;//上一步

    private OperateRule nextStep;//下一步

    private Element element;

    private int listIndex;//集合下标

    @Override
    public Object clone() {
        try {
            return super.clone();
        } catch (CloneNotSupportedException e) {
            throw new CloneException("OperateRule clone失败："+e.getMessage(),e);
        }
    }
}
