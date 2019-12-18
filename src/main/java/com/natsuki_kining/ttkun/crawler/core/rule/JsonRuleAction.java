package com.natsuki_kining.ttkun.crawler.core.rule;

import com.alibaba.fastjson.JSON;
import com.natsuki_kining.ttkun.context.annotation.Autowired;
import com.natsuki_kining.ttkun.context.annotation.Component;
import com.natsuki_kining.ttkun.context.annotation.Run;
import com.natsuki_kining.ttkun.context.annotation.Value;
import com.natsuki_kining.ttkun.crawler.common.excption.RuleException;
import com.natsuki_kining.ttkun.crawler.common.utils.UrlUtil;
import com.natsuki_kining.ttkun.crawler.core.rule.json.OperateAction;
import com.natsuki_kining.ttkun.crawler.model.rule.JsonRule;
import com.natsuki_kining.ttkun.crawler.model.rule.json.OperateRule;
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
            String jsonString = IOUtils.toString(resourceAsStream, "UTF-8");
            OperateRule operateRule = JSON.parseObject(jsonString, OperateRule.class);
            return operateRule;
        } catch (IOException e) {
            log.error(e.getMessage(), e);
            throw new RuleException(e.getMessage(), e);
        }
    }

    @Override
    public JsonRule getRule(String ruleFile) {
        File file = new File(ruleFile);
        try {
            String jsonString = FileUtils.readFileToString(file, "UTF-8");
            OperateRule operateRule = JSON.parseObject(jsonString, OperateRule.class);
            return operateRule;
        } catch (IOException e) {
            log.error(e.getMessage(), e);
            throw new RuleException(e.getMessage(), e);
        }
    }

    @Value("save.path")
    private String savePath;

    @Autowired
    private OperateAction operateAction;

    @Run
    public void action() {
        OperateRule operateRule = (OperateRule) getRule();
        action(operateRule);
    }

    private void action(OperateRule p) {
        Object object = operateAction.action(p);
        OperateRule operateRule = p.getNextStep();
        if (operateRule != null) {
            if (object instanceof Elements) {
                Elements elements = (Elements) object;
                elements.forEach(element -> action(operateRule, p, element));
            } else if (object instanceof Element) {
                Element element = (Element) object;
                action(operateRule, p, element);
            }
        }
    }

    private void action(OperateRule operateRule, OperateRule p, Element element) {
        operateRule.setElement(element);
        operateRule.setLastStep(p);
        action(operateRule);
    }

}
