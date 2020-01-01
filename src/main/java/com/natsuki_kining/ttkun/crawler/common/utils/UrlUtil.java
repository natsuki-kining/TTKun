package com.natsuki_kining.ttkun.crawler.common.utils;

import com.natsuki_kining.ttkun.crawler.common.excption.ConvertException;
import org.apache.commons.lang3.StringUtils;

/**
 * UrlUtil
 *
 * @Author natsuki_kining
 * @Date 2019/12/15 23:19
 * @Version 1.0.0
 **/
public class UrlUtil {

    public static String getWebsite(String url) {
        if (StringUtils.isBlank(url)) {
            throw new ConvertException("url 为空");
        }
        url = url.substring(url.indexOf("//") + 2);
        int index = url.indexOf("/");
        if (index != -1) {
            url = url.substring(0, index);
        }
        return url;
    }

}
