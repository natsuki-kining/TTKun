package com.natsuki_kining.ttkun.crawler.common.utils;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

/**
 * TODO
 *
 * @Author natsuki_kining
 * @Date 2019/12/15 17:31
 **/
public class Connection {

    public static String doGetExpansion(String url, String charset) {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpGet httpGet = null;
        String result = null;
        try {
            httpGet = new HttpGet(url);
            // 设置通用的请求属性
            httpGet.addHeader("Cache-Control", "no-cache");
            httpGet.addHeader("Pragma", "no-cache");
            httpGet.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/72.0.3626.121 Safari/537.36");
            httpGet.addHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8");
            httpGet.addHeader("Accept-Encoding", "gzip, deflate");
            httpGet.addHeader("accept-secret", "");
            httpGet.addHeader("Accept-Language", "zh-CN,zh;q=0.9");
            httpGet.addHeader("Upgrade-Insecure-Requests", "1");
            httpGet.addHeader("Connection", "keep-alive");

//            for (int i = 0; i < cookies.size(); i++) {
//                httpGet.addHeader("Cookie", cookies.get(i));
//            }

            HttpResponse response = httpClient.execute(httpGet);
            if (response != null) {
                for (Header header : response.getAllHeaders()) {
                    if (header.getName().equalsIgnoreCase("Set-Cookie")) {
                        //cookies.add(header.getValue());
                    }
                }
                HttpEntity resEntity = response.getEntity();
                if (resEntity != null) {
                    result = EntityUtils.toString(resEntity, charset);
                    httpGet.releaseConnection();
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            httpGet.releaseConnection();
        }
        return result;
    }


}
