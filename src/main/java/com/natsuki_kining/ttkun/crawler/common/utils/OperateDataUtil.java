package com.natsuki_kining.ttkun.crawler.common.utils;

import com.alibaba.fastjson.JSONObject;
import org.jsoup.nodes.Element;

/**
 * 处理OperateData工具类
 *
 * @Author : natsuki_kining
 * @Date : 2019/12/26 23:43
 * @Version 1.0.0
 */
public class OperateDataUtil {

    public static String get(Object object, String key) {
        if (object instanceof JSONObject) {
            JSONObject jsonObject = (JSONObject) object;
            Object value = jsonObject.get(key);
            if (value == null) {
                return "";
            }
            return value.toString();
        } else if (object instanceof Element) {
            Element element = (Element) object;
            return element.attr(key);
        }
        return "";
    }
}
