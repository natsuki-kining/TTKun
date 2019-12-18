package com.natsuki_kining.ttkun.crawler.core.request.html;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.NicelyResynchronizingAjaxController;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.natsuki_kining.ttkun.context.annotation.Component;
import com.natsuki_kining.ttkun.crawler.model.http.HttpRequest;
import lombok.extern.slf4j.Slf4j;

/**
 * TODO
 *
 * @Author natsuki_kining
 * @Date 2019/12/15 16:25
 **/
@Slf4j
@Component
public class HtmlUnit implements IHtml {


    @Override
    public String getHtml(HttpRequest httpRequest) {
        String result = "";

        final WebClient webClient = new WebClient(BrowserVersion.CHROME);

        webClient.getOptions().setThrowExceptionOnScriptError(false);//当JS执行出错的时候是否抛出异常
        webClient.getOptions().setThrowExceptionOnFailingStatusCode(false);//当HTTP的状态非200时是否抛出异常
        webClient.getOptions().setActiveXNative(false);
        webClient.getOptions().setCssEnabled(false);//是否启用CSS
        webClient.getOptions().setJavaScriptEnabled(true); //很重要，启用JS
        webClient.setAjaxController(new NicelyResynchronizingAjaxController());//很重要，设置支持AJAX

        webClient.getOptions().setTimeout(httpRequest.getTimeout());//设置“浏览器”的请求超时时间
        webClient.setJavaScriptTimeout(httpRequest.getTimeout());//设置JS执行的超时时间

        HtmlPage page = null;
        try {
            page = webClient.getPage(httpRequest.getUrl());
        } catch (Exception e) {
            webClient.close();
            log.error(e.getMessage(), e);
        }
        webClient.waitForBackgroundJavaScript(httpRequest.getWaitForBackgroundJavaScript());//该方法阻塞线程

        result = page.asXml();
        webClient.close();

        return result;
    }
}