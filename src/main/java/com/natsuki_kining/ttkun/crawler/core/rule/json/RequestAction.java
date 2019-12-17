package com.natsuki_kining.ttkun.crawler.core.rule.json;

import com.natsuki_kining.ttkun.context.annotation.Autowired;
import com.natsuki_kining.ttkun.context.annotation.Component;
import com.natsuki_kining.ttkun.crawler.common.excption.RuleException;
import com.natsuki_kining.ttkun.crawler.core.analysis.html.HtmlDelegate;
import com.natsuki_kining.ttkun.crawler.model.http.HttpRequest;
import com.natsuki_kining.ttkun.crawler.model.rule.json.Operate;
import com.natsuki_kining.ttkun.crawler.model.rule.json.Request;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

/**
 * TODO
 *
 * @Author natsuki_kining
 * @Date 2019/12/17 16:31
 **/
@Component("request")
@Slf4j
public class RequestAction implements IOperateAction {

    @Autowired
    private HtmlDelegate htmlDelegate;
    @Autowired
    private HttpRequest httpRequest;

    @Override
    public Document action(Operate operate) {
        Request request = operate.getRequest();
        if(request == null){
            throw new RuleException("request 为空。");
        }
        String html = getHtml(request);
        return Jsoup.parse(html);
    }

    private String getHtml(Request request){
        httpRequest.setUrl(request.getUrl());
        if (StringUtils.isNotBlank(request.getReferer())){
            httpRequest.setReferer(request.getReferer());
        }
        String html = htmlDelegate.getHtml(httpRequest);
        return html;
    }

}
