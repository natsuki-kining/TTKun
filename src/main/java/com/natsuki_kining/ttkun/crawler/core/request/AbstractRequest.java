package com.natsuki_kining.ttkun.crawler.core.request;

import com.natsuki_kining.ttkun.context.annotation.Value;
import com.natsuki_kining.ttkun.crawler.model.enums.RequestMethod;
import lombok.Data;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

import java.util.Map;

/**
 * request 抽象类
 *
 * @Author : natsuki_kining
 * @Date : 2019/12/19 0:24
 */
@Getter
public abstract class AbstractRequest {

    @Value("request.timeout")
    private Integer timeout;//请求超时时间,默认20秒

    @Value("request.wait.for.background.java.script")
    private Integer waitForBackgroundJavaScript;//等待异步JS执行时间,默认20秒

    @Value("request.accept")
    private String accept;//客户端接受的响应类型

    private String acceptEncoding;//浏览器发给服务器,声明浏览器支持的编码类型

    private String acceptLanguage;//浏览器所支持的语言类型

    @Value("request.user.agent")
    private String userAgent;//请求设备

    @Value("request.content.type")
    private String contentType;//指示资源的MIME类型

    private String url;//请求地址

    private Map<String, String> cookies;//cookie

    private String referer;//referer

    private RequestMethod method;//请求类型、post、get、head……

    @Value("request.type")
    private String type="htmlUnit";//发送请求的方式的类型，默认htmlUnit



    //method

    public void setUrl(String url) {
        this.url = url;
    }

    public void appendUrl(String url){
        if (StringUtils.isBlank(this.url)){
            this.url = url;
        }else{
            this.url = this.url.substring(0,this.url.lastIndexOf("/"))+"/"+url;
        }
    }

    public void setReferer(String referer) {
        this.referer = referer;
    }
}
