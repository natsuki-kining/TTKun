package com.natsuki_kining.ttkun.crawler.core.rule.json;

import com.natsuki_kining.ttkun.context.annotation.Component;
import com.natsuki_kining.ttkun.crawler.common.excption.RuleException;
import com.natsuki_kining.ttkun.crawler.model.rule.json.AbstractPosition;
import com.natsuki_kining.ttkun.crawler.model.rule.json.Attribute;
import com.natsuki_kining.ttkun.crawler.model.rule.json.Position;
import com.natsuki_kining.ttkun.crawler.model.rule.json.Tag;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.lang.reflect.Field;
import java.util.List;

/**
 * 根据定位信息获取元素
 *
 * @Author : natsuki_kining
 * @Date : 2019/12/16 21:59
 */
@Component("position")
@Slf4j
public class PositionAction {

    public Elements getElements(Element element, Position position) {
        Elements target = null;
        if (position.fieldIsNull()) {
            throw new RuleException("position 属性为空");
        }
        Field[] fields = Position.class.getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            List<AbstractPosition> abstractPositions = null;
            try {
                abstractPositions = (List<AbstractPosition>) field.get(position);
            } catch (IllegalAccessException e) {
                log.error(e.getMessage(), e);
                throw new RuleException(e.getMessage(), e);
            }
            if (abstractPositions != null && abstractPositions.size() > 0) {
                target = getElements(element, abstractPositions);
            }
        }
        return target;
    }

    public Element getElement(Element element, Position position) {
        Elements elements = getElements(element, position);
        return elements.get(0);
    }

    private Elements getElements(Element element, List<AbstractPosition> abstractPositions) {
        Elements elements = null;
        if (abstractPositions.size()>1){
            abstractPositions.sort((p1, p2) -> p1.getOrder().compareTo(p2.getOrder()));
        }
        for (int i = 0, size = abstractPositions.size(); i < size; i++) {
            AbstractPosition abstractPosition = abstractPositions.get(i);
            Integer index = abstractPosition.getIndex();
            String key = abstractPosition.getKey();
            String value = abstractPosition.getValue();
            if (abstractPosition instanceof Attribute) {
                elements = element.getElementsByAttributeValue(key, value);
            } else if (abstractPosition instanceof Tag) {
                elements = element.getElementsByTag(key);
            }

            if (index != -1) {
                element = elements.get(index);
                elements = new Elements();
                elements.add(element);
            }
        }
        return elements;
    }

}
