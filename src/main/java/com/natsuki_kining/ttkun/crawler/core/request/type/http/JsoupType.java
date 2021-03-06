package com.natsuki_kining.ttkun.crawler.core.request.type.http;

import com.natsuki_kining.ttkun.context.annotation.Component;
import com.natsuki_kining.ttkun.crawler.common.excption.RequestException;
import com.natsuki_kining.ttkun.crawler.core.request.AbstractRequest;
import com.natsuki_kining.ttkun.crawler.core.request.type.AbstractRequestType;
import com.natsuki_kining.ttkun.crawler.model.enums.RequestMethod;
import org.jsoup.Connection;

import java.io.IOException;

/**
 * 使用Jsoup发送请求
 *
 * @Author natsuki_kining
 * @Date 2019/12/15 16:33
 * @UpdateDate 2020/1/4 16:25:00
 * @Version 1.1.0
 **/
@Component
public class JsoupType extends AbstractRequestType {

    @Override
    protected Object doRequest(AbstractRequest request) {
        Connection.Response response = null;
        try {
            Connection connection = org.jsoup.Jsoup.connect(request.getUrl())
                    .header("Accept", request.getAccept())
                    .header("Accept-Encoding", request.getAcceptEncoding())
                    .header("Accept-Language", request.getAcceptLanguage())
                    .header("Content-Type", request.getContentType())
                    .userAgent(request.getUserAgent())
                    .timeout(request.getTimeout())
                    .ignoreContentType(true);
            if (request.getCookies() != null) {
                connection.cookies(request.getCookies());
            }
            if (request.getParameters() != null) {
                connection.data(request.getParameters());
            }
            if (request.getHeaders() != null){
                connection.headers(request.getHeaders());
            }
            if (request.getMethod() != null) {
                connection.method(getMethod(request.getMethod()));
            }
            response = connection.execute();
        } catch (IOException e) {
            throw new RequestException(e.getMessage(), e);
        }
        String html = response.body();
        return html;
    }

    private Connection.Method getMethod(RequestMethod method) {
        Connection.Method connectionMethod;
        switch (method) {
            case GET:
                connectionMethod = Connection.Method.GET;
                break;
            case POST:
                connectionMethod = Connection.Method.POST;
                break;
            default:
                connectionMethod = Connection.Method.POST;
                break;
        }
        return connectionMethod;
    }
}
