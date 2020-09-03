package com.natsuki_kining.ttkun.crawler.core.request.type.http;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.NicelyResynchronizingAjaxController;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.util.Cookie;
import com.natsuki_kining.ttkun.context.annotation.Component;
import com.natsuki_kining.ttkun.crawler.core.request.AbstractRequest;
import com.natsuki_kining.ttkun.crawler.core.request.type.AbstractRequestType;
import lombok.extern.slf4j.Slf4j;

/**
 * 使用HtmlUnit发送请求
 *
 * @Author natsuki_kining
 * @Date 2019/12/15 16:25
 * @UpdateDate 2020/1/4 16:25:00
 * @Version 1.1.0
 **/
@Slf4j
@Component
public class HtmlUnitType extends AbstractRequestType {

    @Override
    protected Object doRequest(AbstractRequest request) {
        String result = "";

        final WebClient webClient = new WebClient(BrowserVersion.CHROME);
        //当JS执行出错的时候是否抛出异常
        webClient.getOptions().setThrowExceptionOnScriptError(false);
        //当HTTP的状态非200时是否抛出异常
        webClient.getOptions().setThrowExceptionOnFailingStatusCode(false);
        webClient.getOptions().setActiveXNative(false);
        //是否启用CSS
        webClient.getOptions().setCssEnabled(false);
        //很重要，启用JS
        webClient.getOptions().setJavaScriptEnabled(true);
        //很重要，设置支持AJAX
        webClient.setAjaxController(new NicelyResynchronizingAjaxController());
        //设置“浏览器”的请求超时时间
        webClient.getOptions().setTimeout(request.getTimeout());
        //设置JS执行的超时时间
        webClient.setJavaScriptTimeout(request.getTimeout());
        if (request.getHeaders() != null) {
            for (String key : request.getHeaders().keySet()) {
                webClient.addRequestHeader(key, request.getHeaders().get(key));
            }
        }
        if (request.getCookies() != null) {
            for (String key : request.getCookies().keySet()) {
                Cookie cookie = new Cookie(request.getReferer(), key, request.getCookies().get(key));
                webClient.getCookieManager().addCookie(cookie);
            }
        }
        HtmlPage page = null;
        try {
            page = webClient.getPage(request.getUrl());
        } catch (Exception e) {
            webClient.close();
            log.error(e.getMessage(), e);
        }
        //该方法阻塞线程
        webClient.waitForBackgroundJavaScript(request.getWaitForBackgroundJavaScript());

        result = page.asXml();
        webClient.close();

        return result;
    }

}
