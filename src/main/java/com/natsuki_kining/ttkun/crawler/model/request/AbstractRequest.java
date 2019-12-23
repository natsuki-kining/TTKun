package com.natsuki_kining.ttkun.crawler.model.request;

import com.natsuki_kining.ttkun.crawler.model.enums.RequestMethod;
import lombok.Data;

import java.util.Map;

/**
 * TODO
 *
 * @Author : natsuki_kining
 * @Date : 2019/12/19 0:24
 */
@Data
public abstract class AbstractRequest {

    private Integer timeout;//请求超时时间,默认20秒

    private Integer waitForBackgroundJavaScript;//等待异步JS执行时间,默认20秒

    private String accept;//客户端接受的响应类型

    private String acceptEncoding;//浏览器发给服务器,声明浏览器支持的编码类型

    private String acceptLanguage;//浏览器所支持的语言类型

    private String userAgent;//请求设备

    private String contentType;//指示资源的MIME类型

    private String url;//请求地址

    private Map<String, String> cookies;//cookie

    private String referer;//referer

    private RequestMethod method;//请求类型、post、get、head……

    private String convertType;//转换类型。html转成document。json转成jsonObject
}
