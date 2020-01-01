package com.natsuki_kining.ttkun.crawler.core.request.convert;

import com.natsuki_kining.ttkun.context.annotation.Component;
import com.natsuki_kining.ttkun.crawler.model.rule.json.RequestRule;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

/**
 * 转换成document
 *
 * @Author natsuki_kining
 * @Date 2019/12/15 16:26
 * @Version 1.0.0
 **/
@Component
@Slf4j
public class HtmlConvert implements IConvert {

    @Override
    public Object convert(RequestRule requestRule, Object response) {
        Document document = Jsoup.parse(response.toString());
        return document;
    }
}
