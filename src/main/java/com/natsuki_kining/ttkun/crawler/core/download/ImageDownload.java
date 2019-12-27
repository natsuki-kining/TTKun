package com.natsuki_kining.ttkun.crawler.core.download;

import com.natsuki_kining.ttkun.context.annotation.Component;

/**
 * 图片下载
 *
 * @Author natsuki_kining
 * @Date 2019/12/15 11:35
 **/
@Component
public class ImageDownload extends AbstractDownload {

    @Override
    public void download(String url, String referer, String savePath, String fileName) {
        super.download(url, referer, savePath, fileName);
    }

}
