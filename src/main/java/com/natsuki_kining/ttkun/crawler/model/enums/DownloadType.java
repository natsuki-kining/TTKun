package com.natsuki_kining.ttkun.crawler.model.enums;

/**
 * 下载类型
 *
 * @Author natsuki_kining
 * @Date 2019/12/17 16:08
 * @Version 1.0.0
 **/
public enum DownloadType {

    IMAGE_DOWNLOAD("imageDownload"),
    ;

    private String value;

    private DownloadType(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }
}
