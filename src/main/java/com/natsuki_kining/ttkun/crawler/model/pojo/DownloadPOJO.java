package com.natsuki_kining.ttkun.crawler.model.pojo;

import lombok.Data;

/**
 * DownloadPOJO
 *
 * @Author : natsuki_kining
 * @Date : 2019/12/18 22:34
 * @Version 1.2.0
 */
@Data
public class DownloadPOJO {

    private String url;
    private String referer;
    private String path;
    private String name;

    public String getUrl() {
        if (!url.startsWith("http")){
            if (url.startsWith("//")){
                url = getReferer().substring(0,getReferer().lastIndexOf(":")+1)+url;
            }else{
                url = getReferer()+"/"+url;
            }
        }
        return url;
    }
}
