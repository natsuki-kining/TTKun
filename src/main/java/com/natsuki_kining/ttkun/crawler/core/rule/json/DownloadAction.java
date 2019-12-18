package com.natsuki_kining.ttkun.crawler.core.rule.json;

import com.natsuki_kining.ttkun.context.annotation.Autowired;
import com.natsuki_kining.ttkun.context.annotation.Component;
import com.natsuki_kining.ttkun.context.annotation.Value;
import com.natsuki_kining.ttkun.context.variables.SystemVariables;
import com.natsuki_kining.ttkun.crawler.common.excption.RuleException;
import com.natsuki_kining.ttkun.crawler.common.utils.StringUtil;
import com.natsuki_kining.ttkun.crawler.core.download.AbstractDownload;
import com.natsuki_kining.ttkun.crawler.core.download.ImageDownload;
import com.natsuki_kining.ttkun.crawler.model.enums.DownloadType;
import com.natsuki_kining.ttkun.crawler.model.http.HttpRequest;
import com.natsuki_kining.ttkun.crawler.model.rule.json.Download;
import com.natsuki_kining.ttkun.crawler.model.rule.json.Operate;
import com.natsuki_kining.ttkun.crawler.model.rule.json.Request;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.nodes.Element;

import java.util.Map;

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
    private Map<String, AbstractDownload> downloadMap;

    @Autowired
    private HttpRequest httpRequest;

    @Value("save.path")
    private String savePath;
//    @Value("download.use.multithreading.enable")
    private Boolean multithreadingEnable=false;

    @Override
    public Object action(Operate operate) {
        Download download = operate.getDownload();
        if (download == null){
            throw new RuleException("download 为空。");
        }
        init(operate);
        log.info("下载文件：{}",download.getUrl());
        AbstractDownload abstractDownload = getDownloadType(download);
        if (multithreadingEnable){
            new Thread(()->{
                abstractDownload.download(download.getUrl(), download.getReferer(), download.getPath(), download.getName());
            }).start();
        }else {
            abstractDownload.download(download.getUrl(), download.getReferer(), download.getPath(), download.getName());
        }
        return operate.getElement();
    }

    private AbstractDownload getDownloadType(Download download) {
        String downloadType = download.getDownloadType();
        if (StringUtils.isBlank(downloadType)){
            downloadType = DownloadType.IMAGE_DOWNLOAD.toString();
        }
        AbstractDownload abstractDownload = downloadMap.get(downloadType);
        if (abstractDownload == null){
            log.error("找不到 指定的下载类型：{}",downloadType);
            throw new RuleException("找不到 指定的下载类型："+downloadType);
        }
        return abstractDownload;
    }

    private void init(Operate operate){
        Element element = operate.getElement();
        Download download = operate.getDownload();

        //设置url
        String url = element.attr(download.getUrl());
        url = StringUtil.replaceBlank(url);
        download.setUrl(url);

        //设置name
        String name = "";
        if (download.getName() == null){
            int index = element.elementSiblingIndex() + 1;
            name = index+url.substring(url.lastIndexOf("."));
        }else{
            name = element.attr(download.getName());
        }
        name = StringUtil.replaceBlank(name);
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
