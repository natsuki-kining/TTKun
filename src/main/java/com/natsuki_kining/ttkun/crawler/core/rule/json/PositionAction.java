package com.natsuki_kining.ttkun.crawler.core.rule.json;

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
@Component("position")
@Slf4j
public class PositionAction implements IOperateAction {

    @Override
    public Object action(OperateRule operateRule) {
        PositionRule positionRule = operateRule.getPosition();
        if (positionRule == null){
            throw new RuleException("positionRule 为空。");
        }
        Element element = operateRule.getElement();
        Integer index = positionRule.getIndex();
        if (index == -1){
            return getElements(element,positionRule);
        }else{
            return getElement(element,positionRule);
        }
    }

    private Elements getElements(Element element, PositionRule positionRule) {
        Elements target = null;
        if (positionRule == null) {
            throw new RuleException("positionRule 为空");
        }
        String type = positionRule.getType();
        if (StringUtils.isBlank(type)) {
            throw new RuleException("positionRule type为空");
        }
        String key = positionRule.getKey();
        String value = positionRule.getValue();
        Integer index = positionRule.getIndex();
        if (PositionType.ATTRIBUTE.toString().equals(type)){
            target = element.getElementsByAttributeValue(key, value);
        }else if(PositionType.TAG.toString().equals(type)){
            target = element.getElementsByTag(key);
        }else{
            throw new RuleException("找不到该position的type："+type);
        }
        if (index != -1) {
            Element e = target.get(index);
            target = new Elements();
            target.add(e);
        }
        return target;
    }

    private Element getElement(Element element, PositionRule positionRule) {
        Elements elements = getElements(element, positionRule);
        return elements.get(0);
    }

}
