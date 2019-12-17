package com.natsuki_kining.ttkun.crawler.core.rule.json;

import com.natsuki_kining.ttkun.context.annotation.Component;
import com.natsuki_kining.ttkun.crawler.common.excption.RuleException;
import com.natsuki_kining.ttkun.crawler.model.rule.json.Operate;
import com.natsuki_kining.ttkun.crawler.model.rule.json.Position;
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
    public Object action(Operate operate) {
        Position position = operate.getPosition();
        if (position == null){
            throw new RuleException("position 为空。");
        }

        Integer index = position.getIndex();
        if (index == -1){
            return getElements(null,position);
        }else{
            return getElement(null,position);
        }
    }

    private Elements getElements(Element element, Position position) {
        Elements target = null;
        if (position == null) {
            throw new RuleException("position 为空");
        }
        String type = position.getType();
        if (StringUtils.isBlank(type)) {
            throw new RuleException("position type为空");
        }
        String key = position.getKey();
        String value = position.getValue();
        Integer index = position.getIndex();
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
            target.add(element);
        }
        return target;
    }

    private Element getElement(Element element, Position position) {
        Elements elements = getElements(element, position);
        return elements.get(0);
    }

}
