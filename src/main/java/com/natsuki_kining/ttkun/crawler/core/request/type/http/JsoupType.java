package com.natsuki_kining.ttkun.crawler.core.request.type.http;

import com.natsuki_kining.ttkun.context.annotation.Component;
import com.natsuki_kining.ttkun.crawler.common.excption.RequestException;
import com.natsuki_kining.ttkun.crawler.core.request.type.AbstractRequestType;
import com.natsuki_kining.ttkun.crawler.model.enums.RequestMethod;
import com.natsuki_kining.ttkun.crawler.core.request.AbstractRequest;
import org.jsoup.Connection;

import java.io.IOException;

/**
 * TODO
 *
 * @Author natsuki_kining
 * @Date 2019/12/15 16:33
 **/
@Component
public class JsoupType extends AbstractRequestType {

    @Override
    public Object doRequest(AbstractRequest request) {
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
