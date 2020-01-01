package com.natsuki_kining.ttkun.crawler.model.pojo;

import lombok.Data;
import org.apache.commons.lang3.StringUtils;

/**
 * RequestPOJO
 *
 * @Author : natsuki_kining
 * @Date : 2019/12/18 22:34
 * @Version 1.0.0
 */
@Data
public class RequestPOJO {

    private String url;
    private String urlName;
    private String referer;
    private String convertType;//转换类型。html转成document。json转成jsonObject

    public String getReferer() {
        if (StringUtils.isBlank(referer) && StringUtils.isNotBlank(url) && url.startsWith("http")){
            this.referer = this.url.substring(0,this.url.indexOf("/",url.indexOf("//")+2)+1);
        }
        return referer;
    }
}
