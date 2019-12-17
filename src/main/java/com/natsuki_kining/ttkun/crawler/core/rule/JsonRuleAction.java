package com.natsuki_kining.ttkun.crawler.core.rule;

import com.alibaba.fastjson.JSON;
import com.natsuki_kining.ttkun.context.annotation.Autowired;
import com.natsuki_kining.ttkun.context.annotation.Component;
import com.natsuki_kining.ttkun.context.annotation.Run;
import com.natsuki_kining.ttkun.context.annotation.Value;
import com.natsuki_kining.ttkun.crawler.common.excption.RuleException;
import com.natsuki_kining.ttkun.crawler.common.utils.UrlUtil;
import com.natsuki_kining.ttkun.crawler.core.analysis.html.HtmlDelegate;
import com.natsuki_kining.ttkun.crawler.core.download.ImageDownload;
import com.natsuki_kining.ttkun.crawler.core.rule.json.OperateAction;
import com.natsuki_kining.ttkun.crawler.model.http.HttpRequest;
import com.natsuki_kining.ttkun.crawler.model.rule.JsonRule;
import com.natsuki_kining.ttkun.crawler.model.rule.json.Operate;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

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

    @Run
    public void action(){
        Operate operate = (Operate) getRule();
        action(operate);
    }

    private void action(Operate p){
        Object object = operateAction.action(p);
        Operate operate = p.getNextStep();
        if (operate != null){
            if(object instanceof Elements){
                Elements elements = (Elements)object;
                elements.forEach(element -> {
                    operate.setElement(element);
                    operate.setLastStep(p);
                    action(operate);
                });
            }else if (object instanceof Element){
                Element element = (Element)object;
                operate.setElement(element);
                operate.setLastStep(p);
                action(operate);
            }
        }
    }

}
