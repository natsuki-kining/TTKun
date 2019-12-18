package com.natsuki_kining.ttkun.crawler.core.request.html;

import com.natsuki_kining.ttkun.context.annotation.Component;
import com.natsuki_kining.ttkun.crawler.model.http.HttpRequest;
import lombok.SneakyThrows;
import org.jsoup.Connection;

/**
 * TODO
 *
 * @Author natsuki_kining
 * @Date 2019/12/15 16:33
 **/
@Component
public class Jsoup implements IHtml {

    @SneakyThrows
    @Override
    public String getHtml(HttpRequest httpRequest) {
        Connection connect = org.jsoup.Jsoup.connect(httpRequest.getUrl());
        Connection.Response response = org.jsoup.Jsoup.connect(httpRequest.getUrl())
                .header("Accept", httpRequest.getAccept())
                .header("Accept-Encoding", "gzip, deflate")
                .header("Accept-Language", "zh-CN,zh;q=0.8,en-US;q=0.5,en;q=0.3")
                .header("Content-Type", "application/json;charset=UTF-8")
                .userAgent("Mozilla/5.0 (Windows NT 6.1; rv:30.0) Gecko/20100101 Firefox/30.0")
                .timeout(httpRequest.getTimeout())
                .ignoreContentType(true)
                .execute();
        String html = response.body();
        return html;
    }
}
