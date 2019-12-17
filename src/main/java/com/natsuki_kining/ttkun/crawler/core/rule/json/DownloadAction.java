package com.natsuki_kining.ttkun.crawler.core.rule.json;

import com.natsuki_kining.ttkun.context.annotation.Autowired;
import com.natsuki_kining.ttkun.context.annotation.Component;
import com.natsuki_kining.ttkun.context.annotation.Value;
import com.natsuki_kining.ttkun.crawler.common.excption.RuleException;
import com.natsuki_kining.ttkun.crawler.core.download.ImageDownload;
import com.natsuki_kining.ttkun.crawler.model.http.HttpRequest;
import com.natsuki_kining.ttkun.crawler.model.rule.json.Download;
import com.natsuki_kining.ttkun.crawler.model.rule.json.Operate;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

/**
 * TODO
 *
 * @Author natsuki_kining
 * @Date 2019/12/17 16:31
 **/
@Component("download")
@Slf4j
public class DownloadAction implements IOperateAction {

    @Override
    public Object action(Operate operate) {
        Download download = operate.getDownload();
        if (download == null){
            throw new RuleException("download 为空。");
        }
        download(download);
        return null;
    }

    @Autowired
    private ImageDownload imageDownload;
    @Autowired
    private HttpRequest httpRequest;

    @Value("save.path")
    private String savePath;

    private void download(Download download){
        String referer = httpRequest.getReferer();
        if (StringUtils.isNotBlank(download.getReferer())){
            referer = download.getReferer();
        }
        String saveMangaPath = savePath;
        if (StringUtils.isNotBlank(download.getPath())){
            saveMangaPath = download.getPath();
        }
        String imageName = download.getName();
        imageDownload.download(download.getUrl(), referer, saveMangaPath, imageName);
    }


}
