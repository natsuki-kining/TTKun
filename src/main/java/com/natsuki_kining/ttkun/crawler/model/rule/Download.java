package com.natsuki_kining.ttkun.crawler.model.rule;

import lombok.Data;

/**
 * 下载操作
 *
 * @Author natsuki_kining
 * @Date 2019/12/15 23:08
 **/
@Data
public class Download {

    private String url;
    private String referer;
    private String path;
    private String name;
}
