package com.natsuki_kining.ttkun.crawler.core.rule;

import com.alibaba.fastjson.JSON;
import com.natsuki_kining.ttkun.context.annotation.Autowired;
import com.natsuki_kining.ttkun.context.annotation.Component;
import com.natsuki_kining.ttkun.context.annotation.Value;
import com.natsuki_kining.ttkun.crawler.common.excption.RuleException;
import com.natsuki_kining.ttkun.crawler.common.utils.UrlUtil;
import com.natsuki_kining.ttkun.crawler.core.analysis.html.HtmlDelegate;
import com.natsuki_kining.ttkun.crawler.core.download.ImageDownload;
import com.natsuki_kining.ttkun.crawler.core.rule.AbstractRuleAction;
import com.natsuki_kining.ttkun.crawler.core.rule.json.OperateAction;
import com.natsuki_kining.ttkun.crawler.model.http.HttpRequest;
import com.natsuki_kining.ttkun.crawler.model.rule.JsonRule;
import com.natsuki_kining.ttkun.crawler.model.rule.Rule;
import com.natsuki_kining.ttkun.crawler.model.rule.json.Operate;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ListIterator;

/**
 * 读取自定义json规则
 *
 * @Author natsuki_kining
 * @Date 2019/12/15 11:26
 **/
@Component
@Slf4j
public class JsonRuleAction extends AbstractRuleAction {

    @Value("manga.url.world-end-crusaders")
    private String url;

    @Value
    private String ruleFilePath = "rule/";

    @Override
    public JsonRule getRule() {
        String website = UrlUtil.getWebsite(url);
        InputStream resourceAsStream = this.getClass().getClassLoader().getResourceAsStream(ruleFilePath + website + ".json");
        try {
            String jsonString = IOUtils.toString(resourceAsStream,"UTF-8");
            Operate operate = JSON.parseObject(jsonString, Operate.class);
            return operate;
        } catch (IOException e) {
            log.error(e.getMessage(),e);
            throw new RuleException(e.getMessage(),e);
        }
    }

    @Override
    public JsonRule getRule(String ruleFile) {
        File file = new File(ruleFile);
        try {
            String jsonString = FileUtils.readFileToString(file,"UTF-8");
            Operate operate = JSON.parseObject(jsonString, Operate.class);
            return operate;
        } catch (IOException e) {
            log.error(e.getMessage(),e);
            throw new RuleException(e.getMessage(),e);
        }
    }

    @Value("save.path")
    private String savePath;

    @Autowired
    private HtmlDelegate htmlDelegate;
    @Autowired
    private HttpRequest httpRequest;
    @Autowired
    private ImageDownload imageDownload;
    @Autowired
    private OperateAction operateAction;
    private String mangaName;

    public void action(){
        Operate operate = (Operate) getRule();
        operateAction.action(operate);
    }

    public void doIt() {
        httpRequest.setUrl(url);
        String html = htmlDelegate.getHtml(httpRequest);
        Document document = Jsoup.parse(html);
        Element tabTextElement = document.getElementsByClass("tab-text").get(0);
        Elements chapterElements = tabTextElement.getElementsByTag("a");
        ListIterator<Element> chapterIterator = chapterElements.listIterator();
        while (chapterIterator.hasNext()){
            Element chapterElement = chapterIterator.next();
            String saveMangaPath = savePath + "/" + mangaName + "/" + chapterElement.attr("title");
            File file = new File(saveMangaPath);
            if (!file.exists()){
                file.mkdirs();
            }
            String href = chapterElement.attr("href");
            String imageUrl = httpRequest.getReferer()+"/"+href;
            httpRequest.setUrl(imageUrl);
            html = htmlDelegate.getHtml(httpRequest);
            document = Jsoup.parse(html);

            Elements imageElements = document.getElementsByClass("chapter-img");
            ListIterator<Element> elementListIterator = imageElements.listIterator();
            int pageIndex = 1;
            while (elementListIterator.hasNext()) {
                Element next = elementListIterator.next();
                String src = next.attr("src");
                String imageSrc = src.replaceAll("\r|\n", "");
                String imageName = pageIndex+imageSrc.substring(imageSrc.lastIndexOf("."));
//                new Thread(()-> {
                log.info("download name:{}, src:{}",imageName, imageSrc);
                imageDownload.download(imageSrc, httpRequest.getReferer(), saveMangaPath, imageName);
//                }).start();
                pageIndex++;
            }
        }
    }
}
