package com.natsuki_kining.ttkun.crawler.common.utils;

import com.natsuki_kining.ttkun.crawler.model.pojo.RequestPOJO;
import com.natsuki_kining.ttkun.crawler.model.rule.json.OperateRule;

/**
 * RuleUtil
 *
 * @Author : natsuki_kining
 * @Date : 2019/12/26 0:49
 * @Version 1.0.0
 */
public class RuleUtil {

    public static String getLastRequestReferer(OperateRule operateRule) {
        if (operateRule != null && operateRule.getRequest() != null && operateRule.getRequest().getData() != null) {
            return ((RequestPOJO) (operateRule.getRequest().getData())).getReferer();
        }
        if (operateRule.getLastStep() != null) {
            return getLastRequestReferer(operateRule.getLastStep());
        }
        return null;
    }
}
