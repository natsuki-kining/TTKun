package com.natsuki_kining.ttkun.crawler.common.config;

import com.natsuki_kining.ttkun.context.annotation.Component;
import com.natsuki_kining.ttkun.context.annotation.Value;
import lombok.Getter;

/**
 * TODO
 *
 * @Author natsuki_kining
 * @Date 2019/12/23 10:56
 **/
@Component
@Getter
public class PropertiesConfig {

    @Value("request.timeout")
    private Integer requestTimeout;//请求超时时间,默认20秒

    @Value("request.wait.for.background.java.script")
    private Integer requestWaitForBackgroundJavaScript;//等待异步JS执行时间,默认20秒

    @Value("request.type")
    private String requestType;//发送请求的方式的类型，默认htmlUnit

    @Value("request.accept")
    private String requestAccept;//客户端接受的响应类型

    @Value("request.user.agent")
    private String requestUserAgent;//请求设备

    @Value("request.content.type")
    private String requestContentType;//指示资源的MIME类型

}
