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
import org.jsoup.nodes.Element;

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
        Element element = operate.getElement();
        if(request == null){
            throw new RuleException("request 为空。");
        }
        init(request,element);

        httpRequest.setUrl(request.getUrl());
        if (StringUtils.isNotBlank(request.getReferer())){
            httpRequest.setReferer(request.getReferer());
        }
        String html = htmlDelegate.getHtml(httpRequest);
        return Jsoup.parse(html);
    }

    private void init(Request request,Element element) {
        String url = request.getUrl();
        if (StringUtils.isNotBlank(url) && !url.startsWith("http")){
            if (url.contains("$") || url.contains("%")){
                url = getUrlValue(url,element);
            }else{
                url = element.attr(request.getUrl());
            }
        }
        request.setUrl(url);

        if (StringUtils.isNotBlank(request.getUrlName())){
            String urlName = element.attr(request.getUrlName());
            request.setUrlName(urlName);
        }
    }

    private String getUrlValue(String url, Element element) {
        String urlValue = "";
        String flag = "";
        if (url.contains("$")){
            flag = "$";
        }else if(url.contains("%attr")){
            flag = "%attr";
        }else {
            return url;
        }

        int index1 = url.indexOf(flag);
        int index2 = url.indexOf("}");
        String value1 = url.substring(0, index1);
        String key = url.substring(index1+(flag.length()+1), index2);
        String value3 = url.substring(index2+1);
        String value2 = "";
        if ("$".equals(flag)){
            if ("referer".equals(key)){
                value2 = httpRequest.getReferer();
            }
        }else if("%attr".equals(flag)){
            value2 = element.attr(key);
        }
        urlValue = value1+value2+value3;

        if (urlValue.contains("$") || urlValue.contains("%")){
            urlValue = getUrlValue(urlValue,element);
        }
        return urlValue;
    }

}
