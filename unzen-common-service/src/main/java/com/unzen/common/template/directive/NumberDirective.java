package com.unzen.common.template.directive;

import org.springframework.stereotype.Component;

import com.unzen.common.template.DirectiveHandler;
import com.unzen.common.template.TemplateDirective;

/**
 * 数字处理
 * - 大于1000的显示1k
 * - 大于10000的显示1m
 * Created by langhsu on 2015/10/8.
 */
@Component
public class NumberDirective extends TemplateDirective {
    @Override
    public String getName() {
        return "num";
    }

    @Override
    public void execute(DirectiveHandler handler) throws Exception {
        Integer value = handler.getInteger("value", 1);
        String out = value.toString();

        if (value > 1000) {
            out = value / 1000 + "k";
        } else if (value > 10000) {
            out = value / 10000 + "m";
        }
        handler.renderString(out);
    }

}
