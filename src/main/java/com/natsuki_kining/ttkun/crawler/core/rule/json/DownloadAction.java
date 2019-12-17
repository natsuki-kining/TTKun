package com.natsuki_kining.ttkun.crawler.core.rule.json;

import com.natsuki_kining.ttkun.context.annotation.Autowired;
import com.natsuki_kining.ttkun.context.annotation.Component;
import com.natsuki_kining.ttkun.context.annotation.Value;
import com.natsuki_kining.ttkun.context.variables.SystemVariables;
import com.natsuki_kining.ttkun.crawler.common.excption.RuleException;
import com.natsuki_kining.ttkun.crawler.core.download.ImageDownload;
import com.natsuki_kining.ttkun.crawler.model.http.HttpRequest;
import com.natsuki_kining.ttkun.crawler.model.rule.json.Download;
import com.natsuki_kining.ttkun.crawler.model.rule.json.Operate;
import com.natsuki_kining.ttkun.crawler.model.rule.json.Request;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.nodes.Element;

/**
 * TODO
 *
 * @Author natsuki_kining
 * @Date 2019/12/17 16:31
 **/
@Component("download")
@Slf4j
public class DownloadAction implements IOperateAction {

    @Autowired
    private ImageDownload imageDownload;
    @Autowired
    private HttpRequest httpRequest;

    @Value("save.path")
    private String savePath;
    @Value("download.use.multithreading.enable")
    private Boolean multithreadingEnable;

    @Override
    public Object action(Operate operate) {
        Download download = operate.getDownload();
        if (download == null){
            throw new RuleException("download 为空。");
        }
        init(operate);
        if (multithreadingEnable){
            new Thread(()->{
                imageDownload.download(download.getUrl(), download.getReferer(), download.getPath(), download.getName());
            }).start();
        }else {
            imageDownload.download(download.getUrl(), download.getReferer(), download.getPath(), download.getName());
        }
        return operate.getElement();
    }

    private void init(Operate operate){
        Element element = operate.getElement();
        Download download = operate.getDownload();

        //设置url
        String url = element.attr(download.getUrl());
        download.setUrl(url);

        //设置name
        String name = "";
        if (download.getName() == null){
            int index = element.elementSiblingIndex() + 1;
            name = index+url.substring(url.lastIndexOf("."));
        }else{
            name = element.attr(download.getName());
        }
        download.setName(name);

        //设置referer
        String referer = httpRequest.getReferer();
        if (StringUtils.isNotBlank(download.getReferer())){
            referer = download.getReferer();
        }
        download.setReferer(referer);

        //设置path
        String path = "";
        String lastUrlName = "";
        Request request = getLastRequest(operate);
        if (request != null){
            lastUrlName = request.getUrlName();
        }
        if (StringUtils.isNotBlank(download.getPath())){
            path = download.getPath();
        }else{
            path = savePath + SystemVariables.FILE_SEPARATOR + lastUrlName + SystemVariables.FILE_SEPARATOR;
        }
        download.setPath(path);
    }


    private Request getLastRequest(Operate operate){
        if (operate == null){
            return null;
        }
        if (operate.getRequest() != null){
            return operate.getRequest();
        }
        return getLastRequest(operate.getLastStep());
    }

}
