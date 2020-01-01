package com.natsuki_kining.ttkun.crawler.core.request.type.http;

import com.natsuki_kining.ttkun.crawler.common.excption.RequestException;
import com.natsuki_kining.ttkun.crawler.core.request.AbstractRequest;
import com.natsuki_kining.ttkun.crawler.core.request.type.AbstractRequestType;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

/**
 * 使用httpclient发送
 *
 * @Author natsuki_kining
 * @Date 2019/12/15 17:31
 * @Version 1.0.0
 **/
public class HttpClientType extends AbstractRequestType {

    @Override
    protected Object doRequest(AbstractRequest request) throws RequestException {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpGet httpGet = null;
        String result = null;
        try {
            httpGet = new HttpGet(request.getUrl());
            // 设置通用的请求属性
            httpGet.addHeader("Cache-Control", "no-cache");
            httpGet.addHeader("Pragma", "no-cache");
            httpGet.addHeader("User-Agent", request.getUserAgent());
            httpGet.addHeader("Accept", request.getAccept());
            httpGet.addHeader("Accept-Encoding", request.getAcceptEncoding());
            httpGet.addHeader("Accept-Language", request.getAcceptLanguage());

            HttpResponse response = httpClient.execute(httpGet);
            if (response != null) {
                HttpEntity resEntity = response.getEntity();
                if (resEntity != null) {
                    result = EntityUtils.toString(resEntity);
                    httpGet.releaseConnection();
                }
            }
        } catch (Exception e) {
            httpGet.releaseConnection();
            throw new RequestException(e.getMessage(), e);
        }
        return result;
    }
}
