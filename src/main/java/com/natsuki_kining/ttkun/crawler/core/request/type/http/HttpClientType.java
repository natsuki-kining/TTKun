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
 * @UpdateDate 2020/1/4 16:25:00
 * @Version 1.1.0
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
            if (request.getHeaders() != null) {
                for (String key : request.getHeaders().keySet()) {
                    httpGet.setHeader(key, request.getHeaders().get(key));
                }
            }
            if (request.getCookies() != null) {
                StringBuilder builder = new StringBuilder();
                for (String key : request.getCookies().keySet()) {
                    builder.append(key);
                    builder.append("=");
                    builder.append(request.getCookies().get(key));
                    builder.append(";");
                }
                builder.deleteCharAt(builder.length() - 1);
                httpGet.setHeader("cookie", builder.toString());
            }
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
