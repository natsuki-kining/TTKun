package com.natsuki_kining.ttkun.crawler.core.request;

import com.natsuki_kining.ttkun.context.annotation.Component;
import com.natsuki_kining.ttkun.crawler.common.excption.CloneException;
import lombok.Data;

/**
 * TODO
 *
 * @Author natsuki_kining
 * @Date 2019/12/23 9:58
 **/
@Data
@Component
public class HttpRequest extends AbstractRequest implements Cloneable {

    @Override
    public Object clone() {
        try {
            return super.clone();
        } catch (CloneNotSupportedException e) {
            throw new CloneException("HttpRequest clone失败："+e.getMessage(),e);
        }
    }

}
