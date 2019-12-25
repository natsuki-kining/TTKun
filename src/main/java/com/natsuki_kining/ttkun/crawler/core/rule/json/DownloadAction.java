package com.natsuki_kining.ttkun.crawler.core.rule.json;

import com.natsuki_kining.ttkun.context.annotation.Autowired;
import com.natsuki_kining.ttkun.context.annotation.Component;
import com.natsuki_kining.ttkun.context.annotation.Value;
import com.natsuki_kining.ttkun.context.variables.SystemVariables;
import com.natsuki_kining.ttkun.crawler.common.excption.RuleException;
import com.natsuki_kining.ttkun.crawler.common.utils.RuleUtil;
import com.natsuki_kining.ttkun.crawler.common.utils.StringUtil;
import com.natsuki_kining.ttkun.crawler.core.download.AbstractDownload;
import com.natsuki_kining.ttkun.crawler.model.enums.DownloadType;
import com.natsuki_kining.ttkun.crawler.model.pojo.DownloadPOJO;
import com.natsuki_kining.ttkun.crawler.model.rule.json.DownloadRule;
import com.natsuki_kining.ttkun.crawler.model.rule.json.OperateRule;
import com.natsuki_kining.ttkun.crawler.model.rule.json.RequestRule;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.nodes.Element;

import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * TODO
 *
 * @Author natsuki_kining
 * @Date 2019/12/17 16:31
 **/
@Component
@Slf4j
public class DownloadAction implements IOperateAction {

    @Autowired
    private Map<String, AbstractDownload> downloadMap;

    @Value("save.path")
    private String savePath;
    @Value("download.use.multithreading.enable")
    private Boolean multithreadingEnable;

    ExecutorService cachedThreadPool = Executors.newCachedThreadPool();

    @Override
    public Object action(OperateRule operateRule) {
        DownloadRule downloadRule = operateRule.getDownload();
        DownloadPOJO downloadPOJO = init(operateRule);
        AbstractDownload abstractDownload = getDownloadType(downloadRule);
        if (multithreadingEnable){
            cachedThreadPool.execute(()->{
                abstractDownload.download(downloadPOJO.getUrl(), downloadPOJO.getReferer(), downloadPOJO.getPath(), downloadPOJO.getName());
            });
        }else {
            abstractDownload.download(downloadPOJO.getUrl(), downloadPOJO.getReferer(), downloadPOJO.getPath(), downloadPOJO.getName());
        }
        return operateRule.getElement();
    }

    private AbstractDownload getDownloadType(DownloadRule downloadRule) {
        String downloadType = downloadRule.getDownloadType();
        if (StringUtils.isBlank(downloadType)){
            downloadType = DownloadType.IMAGE_DOWNLOAD.toString();
        }
        AbstractDownload abstractDownload = downloadMap.get(downloadType);
        if (abstractDownload == null){
            throw new RuleException("找不到 指定的下载类型："+downloadType);
        }
        return abstractDownload;
    }

    @Override
    public DownloadPOJO init(OperateRule operateRule){
        Element element = operateRule.getElement();
        DownloadRule downloadRule = operateRule.getDownload();

        DownloadPOJO download = new DownloadPOJO();

        //设置url
        String url = downloadRule.getUrl();
        if (StringUtils.isBlank(url)){
            url = element.attr(downloadRule.getUrlAttr());
        }
        url = StringUtil.replaceBlank(url);
        download.setUrl(url);

        //设置name
        String name = "";
        if (downloadRule.getName() == null){
            name = (operateRule.getListIndex()+1)+url.substring(url.lastIndexOf("."));
        }else{
            name = element.attr(downloadRule.getName());
        }
        name = StringUtil.replaceBlank(name);
        download.setName(name);

        //设置referer
        if (StringUtils.isBlank(download.getReferer())){
            download.setReferer(RuleUtil.getLastRequestReferer(operateRule));
        }

        //设置path
        String path = "";
        String lastUrlName = "";
        OperateRule or = getLastRequest(operateRule);
        if (or != null){
            RequestRule rr = or.getRequest();
            Element oe = or.getElement();
            if (StringUtils.isNotBlank(rr.getUrlName())){
                lastUrlName = oe.attr(rr.getUrlName());
            }
        }
        if (StringUtils.isNotBlank(downloadRule.getPath())){
            path = downloadRule.getPath();
        }else{
            path = savePath + SystemVariables.FILE_SEPARATOR + lastUrlName + SystemVariables.FILE_SEPARATOR;
        }
        download.setPath(path);

        return download;
    }


    private OperateRule getLastRequest(OperateRule operateRule){
        if (operateRule == null){
            return null;
        }
        if (operateRule.getRequest() != null){
            return operateRule;
        }
        return getLastRequest(operateRule.getLastStep());
    }

}
