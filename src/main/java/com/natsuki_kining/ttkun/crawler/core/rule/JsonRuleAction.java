package com.natsuki_kining.ttkun.crawler.core.rule;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.natsuki_kining.ttkun.context.annotation.Autowired;
import com.natsuki_kining.ttkun.context.annotation.Component;
import com.natsuki_kining.ttkun.context.annotation.Value;
import com.natsuki_kining.ttkun.context.variables.SystemVariables;
import com.natsuki_kining.ttkun.crawler.common.excption.RuleException;
import com.natsuki_kining.ttkun.crawler.common.utils.UrlUtil;
import com.natsuki_kining.ttkun.crawler.concurrent.FixedThreadPool;
import com.natsuki_kining.ttkun.crawler.core.rule.json.OperateAction;
import com.natsuki_kining.ttkun.crawler.model.rule.JsonRule;
import com.natsuki_kining.ttkun.crawler.model.rule.json.OperateRule;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.stream.Stream;

/**
 * 读取自定义json规则
 *
 * @Author natsuki_kining
 * @Date 2019/12/15 11:26
 * @Version 1.0.0
 **/
@Component
@Slf4j
public class JsonRuleAction extends AbstractRuleAction {

    @Value("crawler.url")
    private String url;
    @Value("crawler.rule.path")
    private String rulePath;
    @Value("download.use.multithreading.enable")
    private Boolean multithreadingEnable;

    @Autowired
    private OperateAction operateAction;
    @Autowired
    private FixedThreadPool fixedThreadPool;

    private String defaultRuleFilePath = "rule/";

    @Override
    public void action() {
        OperateRule operateRule = (OperateRule) getRule();
        action(operateRule);
    }

    private void action(OperateRule p) {
        Object object = operateAction.action(p);
        OperateRule operateRule = p.getNextStep();
        if (operateRule != null) {
            if ("chapter".equals(operateRule.getOperateName()) && multithreadingEnable) {
                fixedThreadPool.downloadChapterThreadPool.execute(() -> {
                    action(object, operateRule, p);
                });
            } else {
                action(object, operateRule, p);
            }
        }
    }

    private void action(Object object, OperateRule operateRule, OperateRule p) {
        if (object instanceof Elements) {
            Elements elements = (Elements) object;
            Stream.iterate(0, i -> i + 1)
                    .limit(elements.size())
                    .forEach(index -> {
                        Element element = elements.get(index);
                        OperateRule operateRuleClone = (OperateRule) operateRule.clone();
                        operateRuleClone.setListIndex(index);
                        setOperateRuleProperties(operateRuleClone, p, element);
                    });
        } else if (object instanceof Element) {
            Element element = (Element) object;
            setOperateRuleProperties(operateRule, p, element);
        } else if (object instanceof JSONObject) {
            JSONObject jsonObject = (JSONObject) object;
            setOperateRuleProperties(operateRule, p, jsonObject);
        } else if (object instanceof JSONArray) {
            JSONArray jsonArray = (JSONArray) object;
            Stream.iterate(0, i -> i + 1)
                    .limit(jsonArray.size())
                    .forEach(index -> {
                        JSONObject jsonObject = jsonArray.getJSONObject(index);
                        OperateRule operateRuleClone = (OperateRule) operateRule.clone();
                        operateRuleClone.setListIndex(index);
                        setOperateRuleProperties(operateRuleClone, p, jsonObject);
                    });
        }
    }

    private void setOperateRuleProperties(OperateRule operateRule, OperateRule p, Object object) {
        operateRule.setOperateData(object);
        operateRule.setLastStep(p);
        action(operateRule);
    }

    @Override
    public JsonRule getRule() {
        if (StringUtils.isBlank(url)) {
            throw new RuleException("url 不能为空。");
        }
        String website = UrlUtil.getWebsite(url);
        boolean useDirectory = StringUtils.isBlank(rulePath) ? false : new File(rulePath).isDirectory();
        String jsonString = null;
        if (useDirectory) {
            jsonString = getRuleByDirectory(website);
        } else {
            jsonString = getRuleByClasspath(website);
        }
        OperateRule operateRule = JSON.parseObject(jsonString, OperateRule.class);
        return operateRule;
    }

    public String getRuleByClasspath(String website) {
        InputStream resourceAsStream = this.getClass().getClassLoader().getResourceAsStream(defaultRuleFilePath + SystemVariables.FILE_SEPARATOR + website + ".json");
        try {
            String jsonString = IOUtils.toString(resourceAsStream, "UTF-8");
            return jsonString;
        } catch (IOException e) {
            log.error(e.getMessage(), e);
            throw new RuleException(e.getMessage(), e);
        }
    }

    public String getRuleByDirectory(String website) {
        File file = new File(rulePath + SystemVariables.FILE_SEPARATOR + website + ".json");
        if (!file.exists() || !file.isFile()) {
            throw new RuleException("规则文件不存在");
        }
        try {
            String jsonString = FileUtils.readFileToString(file, "UTF-8");
            return jsonString;
        } catch (IOException e) {
            log.error(e.getMessage(), e);
            throw new RuleException(e.getMessage(), e);
        }
    }

}
