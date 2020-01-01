package com.natsuki_kining.ttkun.crawler.model.pojo;

import lombok.Data;

/**
 * DownloadPOJO
 *
 * @Author : natsuki_kining
 * @Date : 2019/12/18 22:34
 * @Version 1.0.0
 */
@Data
public class DownloadPOJO {

    private String url;
    private String referer;
    private String path;
    private String name;
}
