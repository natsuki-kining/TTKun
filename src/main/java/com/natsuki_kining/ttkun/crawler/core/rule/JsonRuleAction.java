package com.natsuki_kining.ttkun.crawler.core.rule;

import com.alibaba.fastjson.JSON;
import com.natsuki_kining.ttkun.context.annotation.Component;
import com.natsuki_kining.ttkun.context.annotation.Run;
import com.natsuki_kining.ttkun.context.annotation.Value;
import com.natsuki_kining.ttkun.crawler.common.excption.RuleException;
import com.natsuki_kining.ttkun.crawler.common.utils.UrlUtil;
import com.natsuki_kining.ttkun.crawler.model.rule.JsonRule;
import com.natsuki_kining.ttkun.crawler.model.rule.json.Operate;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;

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

    public void test(){
        Operate operate = (Operate) getRule();
        operate.getType();


    }
}
