package com.natsuki_kining.ttkun.crawler.core.rule.json;

import com.natsuki_kining.ttkun.context.ApplicationContext;
import com.natsuki_kining.ttkun.context.annotation.Autowired;
import com.natsuki_kining.ttkun.context.annotation.Component;
import com.natsuki_kining.ttkun.crawler.common.utils.OperateDataUtil;
import com.natsuki_kining.ttkun.crawler.common.utils.RuleUtil;
import com.natsuki_kining.ttkun.crawler.core.request.HttpRequest;
import com.natsuki_kining.ttkun.crawler.core.request.RequestDelegate;
import com.natsuki_kining.ttkun.crawler.model.pojo.RequestPOJO;
import com.natsuki_kining.ttkun.crawler.model.rule.json.OperateRule;
import com.natsuki_kining.ttkun.crawler.model.rule.json.RequestRule;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

/**
 * 处理请求操作
 *
 * @Author natsuki_kining
 * @Date 2019/12/17 16:31
 * @Version 1.0.0
 **/
@Component
@Slf4j
public class RequestAction implements IOperateAction {

    @Autowired
    private RequestDelegate requestDelegate;
    @Autowired
    private HttpRequest httpRequest;

    @Override
    public Object action(OperateRule operateRule) {
        return requestDelegate.doHttpRequest(operateRule.getRequest());
    }

    @Override
    public RequestPOJO init(OperateRule operateRule) {
        RequestPOJO requestPOJO = new RequestPOJO();

        Object operateData = operateRule.getOperateData();
        RequestRule requestRule = operateRule.getRequest();

        requestPOJO.setConvertType(requestRule.getConvertType());

        String url = requestRule.getUrl();
        if (StringUtils.isNotBlank(url) && !url.startsWith("http")) {
            if (url.contains("$") || url.contains("#") || url.contains("%")) {
                url = getUrlValue(operateRule, url, operateData);
            } else {
                url = OperateDataUtil.get(operateData, requestRule.getUrl());
            }
        }
        requestPOJO.setUrl(url);

        if (StringUtils.isNotBlank(requestRule.getReferer())) {
            requestPOJO.setReferer(requestRule.getReferer());
        }

        String urlName = "";
        if (StringUtils.isNotBlank(requestRule.getUrlName())) {
            urlName = OperateDataUtil.get(operateData, requestRule.getUrlName());
        } else {
            urlName = requestPOJO.getUrl().replace(requestPOJO.getReferer(), "");
        }
        requestPOJO.setUrlName(urlName);

        return requestPOJO;
    }

    private String getUrlValue(OperateRule operateRule, String url, Object operateData) {
        String urlValue = "";
        String flag = "";
        if (url.contains("$")) {
            flag = "$";
        } else if (url.contains("#")) {
            flag = "#";
        } else if (url.contains("%attr")) {
            flag = "%attr";
        } else {
            return url;
        }

        int index1 = url.indexOf(flag);
        int index2 = url.indexOf("}");
        String value1 = url.substring(0, index1);
        String key = url.substring(index1 + (flag.length() + 1), index2);
        String value3 = url.substring(index2 + 1);
        String value2 = "";
        if ("$".equals(flag)) {
            value2 = ApplicationContext.getInstance().getReader().getConfig().getProperty(key);
        }
        if ("#".equals(flag)) {
            if ("referer".equals(key)) {
                value2 = RuleUtil.getLastRequestReferer(operateRule);
            }
        } else if ("%attr".equals(flag)) {
            value2 = OperateDataUtil.get(operateData, key);
        }
        urlValue = value1 + value2 + value3;

        if (urlValue.contains("$") || urlValue.contains("%")) {
            urlValue = getUrlValue(operateRule, urlValue, operateData);
        }
        return urlValue;
    }

}
