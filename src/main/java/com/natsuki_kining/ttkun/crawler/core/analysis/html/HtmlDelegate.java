package com.natsuki_kining.ttkun.crawler.core.analysis.html;

import com.natsuki_kining.ttkun.context.annotation.Autowired;
import com.natsuki_kining.ttkun.context.annotation.Component;
import com.natsuki_kining.ttkun.crawler.common.excption.AnalysisException;
import com.natsuki_kining.ttkun.crawler.model.http.HttpRequest;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;

/**
 * TODO
 *
 * @Author natsuki_kining
 * @Date 2019/12/15 16:26
 **/
@Component
@Slf4j
public class HtmlDelegate implements IHtml {

    @Autowired
    private Map<String, IHtml> htmlMap;

    @Override
    public String getHtml(HttpRequest httpRequest) {
        log.info("使用：{}方式获取html。", httpRequest.getHtmlType());
        IHtml html = htmlMap.get(httpRequest.getHtmlType());
        if (html == null) {
            throw new AnalysisException("没有该" + httpRequest.getHtmlType() + "方式获取html");
        }
        return html.getHtml(httpRequest);
    }
}
