package com.natsuki_kining.ttkun.crawler.core.rule.json;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.natsuki_kining.ttkun.context.annotation.Component;
import com.natsuki_kining.ttkun.context.annotation.Value;
import com.natsuki_kining.ttkun.crawler.common.excption.RuleException;
import com.natsuki_kining.ttkun.crawler.model.enums.PositionType;
import com.natsuki_kining.ttkun.crawler.model.rule.json.OperateRule;
import com.natsuki_kining.ttkun.crawler.model.rule.json.PositionRule;
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

    @Value("crawler.chapter.start")
    private String chapterStart;
    @Value("crawler.chapter")
    private String chapter;

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

        String type = positionRule.getType();
        String key = positionRule.getKey();
        String value = positionRule.getValue();

        if (operateData instanceof Element) {
            Element element = (Element) operateData;
            Elements target = null;
            if (PositionType.ATTRIBUTE.toString().equals(type)) {
                target = element.getElementsByAttributeValue(key, value);
            } else if (PositionType.TAG.toString().equals(type)) {
                target = element.getElementsByTag(key);
            } else {
                throw new RuleException("找不到该position的type：" + type);
            }
            if (index == -1) {
                return getChapters(target, operateRule);
            } else {
                return target.get(index);
            }
        } else if (operateData instanceof JSONObject) {
            JSONObject jsonObject = (JSONObject) operateData;
            if (index == -1) {
                return getChapters(jsonObject.get(key), operateRule);
            } else {
                return jsonObject.get(key);
            }
        }
        return null;
    }

    private Object getChapters(Object data, OperateRule operateRule) {
        if (!"chapter".equals(operateRule.getOperateName())) {
            return data;
        }
        if (StringUtils.isBlank(chapter) && StringUtils.isBlank(chapterStart)) {
            return data;
        }
        if (data instanceof Elements) {
            Elements elements = (Elements) data;
            if (StringUtils.isNotBlank(chapter)) {
                return elements.stream().filter(element -> element.text().contains(chapter)).findFirst().orElse(null);
            }
            Elements objects = new Elements();
            elements.stream().filter(element -> {
                objects.add(element);
                return element.text().contains(chapterStart);
            }).findAny().orElse(null);
            return objects;
        } else if (data instanceof JSONArray) {
            JSONArray jsonArray = (JSONArray) data;
            if (StringUtils.isNotBlank(chapter)) {
                return jsonArray.stream().filter(json -> json.toString().contains(chapter)).findFirst().orElse(null);
            }
            JSONArray objects = new JSONArray();
            jsonArray.stream().filter(jsonObject -> {
                objects.add(jsonObject);
                return jsonObject.toString().contains(chapterStart);
            }).findAny().orElse(null);;
            return objects;
        }
        return null;
    }


}
