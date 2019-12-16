package com.natsuki_kining.ttkun.crawler.core.rule;

import com.alibaba.fastjson.JSON;
import com.natsuki_kining.ttkun.context.annotation.Component;
import com.natsuki_kining.ttkun.context.annotation.Run;
import com.natsuki_kining.ttkun.context.annotation.Value;
import com.natsuki_kining.ttkun.crawler.common.utils.UrlUtil;
import com.natsuki_kining.ttkun.crawler.model.rule.Operate;
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
public class JsonRule extends AbstractRule {

    @Value("manga.url.world-end-crusaders")
    private String url;

    private String ruleFilePath = "rule/";

    @Run
    @Override
    public void readRule() {
        String website = UrlUtil.getWebsite(url);
        InputStream resourceAsStream = this.getClass().getClassLoader().getResourceAsStream(ruleFilePath + website + ".json");
        try {
            String jsonString = IOUtils.toString(resourceAsStream,"UTF-8");
            Operate operate = JSON.parseObject(jsonString, Operate.class);
            System.out.println(operate.getType());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
