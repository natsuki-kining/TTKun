package com.natsuki_kining.ttkun.crawler.model.http;

import com.natsuki_kining.ttkun.context.annotation.Component;
import com.natsuki_kining.ttkun.context.annotation.Value;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

import java.util.Map;

/**
 * TODO
 *
 * @Author natsuki_kining
 * @Date 2019/12/15 16:07
 **/
@Component
@Getter
public class HttpRequest {

    @Value("request.timeout")
    private Integer timeout;//请求超时时间,默认20秒

    @Value("request.wait.for.background.java.script")
    private Integer waitForBackgroundJavaScript;//等待异步JS执行时间,默认20秒

    @Value("request.html.type")
    private String htmlType;//获取html方式的类型，默认htmlUnit

    @Value("request.accept")
    private String accept;//客户端接受的响应类型

    @Value("request.user.agent")
    private String userAgent;//请求设备

    @Value("request.content.type")
    private String contentType;//指示资源的MIME类型

    private String url;//请求地址
    private Map<String, String> cookies;//cookie
    private String referer;

    public void setUrl(String url) {
        this.url = url;
        this.referer = this.url.substring(0,this.url.indexOf("/",8));
    }

    public void setCookies(Map<String, String> cookies) {
        this.cookies = cookies;
    }

    public void setReferer(String referer) {
        this.referer = referer;
    }

    public void appendUrl(String url){
        if (StringUtils.isBlank(this.url)){
            this.url = url;
        }else{
            this.url = this.url.substring(0,this.url.lastIndexOf("/"))+"/"+url;
        }
    }

    //请求类型
    public interface RequestHtmlType {
        String HTML_UNIT = "htmlUnit";
        String JSONP = "jsoup";
    }

}