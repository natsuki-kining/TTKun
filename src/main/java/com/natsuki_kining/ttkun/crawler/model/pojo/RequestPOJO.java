package com.natsuki_kining.ttkun.crawler.model.pojo;

import lombok.Data;

/**
 * TODO
 *
 * @Author : natsuki_kining
 * @Date : 2019/12/18 22:34
 */
@Data
public class RequestPOJO {

    private String url;
    private String urlName;
    private String referer;
    private String convertType;//转换类型。html转成document。json转成jsonObject
}
