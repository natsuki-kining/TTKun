package com.natsuki_kining.ttkun.crawler.model.rule.json;

import lombok.Data;

/**
 * 下载操作
 *
 * @Author natsuki_kining
 * @Date 2019/12/15 23:08
 **/
@Data
public class DownloadRule extends AbstractRequestRule {

    private String name;
    private String path;
    private String downloadType;
}
