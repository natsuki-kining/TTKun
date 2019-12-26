package com.natsuki_kining.ttkun.crawler.core.rule.json;

import com.alibaba.fastjson.JSONObject;
import com.natsuki_kining.ttkun.context.annotation.Component;
import com.natsuki_kining.ttkun.crawler.common.excption.RuleException;
import com.natsuki_kining.ttkun.crawler.model.rule.json.OperateRule;
import com.natsuki_kining.ttkun.crawler.model.rule.json.PositionRule;
import com.natsuki_kining.ttkun.crawler.model.enums.PositionType;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * 根据定位信息获取元素
 *
 * @Author : natsuki_kining
 * @Date : 2019/12/16 21:59
 */
@Component
@Slf4j
public class PositionAction implements IOperateAction {

    @Override
    public Object action(OperateRule operateRule) {
        return operateRule.getPosition().getData();
    }

    @Override
    public Object init(OperateRule operateRule) {
        PositionRule positionRule = operateRule.getPosition();
        if (StringUtils.isBlank(positionRule.getType())) {
            throw new RuleException("positionRule type为空");
        }
        Object operateData = operateRule.getOperateData();
        Integer index = positionRule.getIndex();
        if (index == -1) {
            return getObjects(operateData, positionRule);
        } else {
            return getObject(operateData, positionRule);
        }
    }

    private Object getObjects(Object operateData, PositionRule positionRule) {
        if (operateData instanceof Element) {
            Element element = (Element) operateData;
            return getElements(element, positionRule);
        } else if (operateData instanceof JSONObject) {
            JSONObject jsonObject = (JSONObject) operateData;
            return getJSONArray(jsonObject, positionRule);
        }
        return null;
    }

    private Object getObject(Object operateData, PositionRule positionRule) {
        if (operateData instanceof Element) {
            Element element = (Element) operateData;
            return getElements(element, positionRule).get(0);
        } else if (operateData instanceof JSONObject) {
            JSONObject jsonObject = (JSONObject) operateData;
            return getJSONArray(jsonObject, positionRule);
        }
        return null;
    }

    private Elements getElements(Element element, PositionRule positionRule) {
        String type = positionRule.getType();
        String key = positionRule.getKey();
        String value = positionRule.getValue();
        Integer index = positionRule.getIndex();
        Elements target = null;
        if (PositionType.ATTRIBUTE.toString().equals(type)) {
            target = element.getElementsByAttributeValue(key, value);
        } else if (PositionType.TAG.toString().equals(type)) {
            target = element.getElementsByTag(key);
        } else {
            throw new RuleException("找不到该position的type：" + type);
        }
        if (index != -1) {
            Element e = target.get(index);
            target = new Elements();
            target.add(e);
        }
        return target;
    }

    private Elements getJSONArray(JSONObject jsonObject, PositionRule positionRule) {
        return null;
    }

}
