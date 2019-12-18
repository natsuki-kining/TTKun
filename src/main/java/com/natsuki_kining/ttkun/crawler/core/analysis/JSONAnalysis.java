package com.natsuki_kining.ttkun.crawler.core.analysis;

import com.natsuki_kining.ttkun.context.annotation.Autowired;
import com.natsuki_kining.ttkun.context.annotation.Component;
import com.natsuki_kining.ttkun.context.annotation.Value;
import com.natsuki_kining.ttkun.crawler.core.request.html.HtmlDelegate;
import com.natsuki_kining.ttkun.crawler.core.download.ImageDownload;
import com.natsuki_kining.ttkun.crawler.model.http.HttpRequest;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.util.ListIterator;

/**
 * 根据json规则解析文本数据
 *
 * @Author natsuki_kining
 * @Date 2019/12/15 14:31
 **/
@Component
@Slf4j
public class JSONAnalysis extends AbstractAnalysis {

    @Value("manga.url.world-end-crusaders")
    private String url;
    @Value("manga.zh-cn.world-end-crusaders")
    private String mangaName;
    @Value("save.path")
    private String savePath;

    @Autowired
    private HtmlDelegate htmlDelegate;
    @Autowired
    private HttpRequest httpRequest;
    @Autowired
    private ImageDownload imageDownload;

//    @Run
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

    public void json(){

    }

}
