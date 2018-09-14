package com.unzen.common.template.directive;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.unzen.common.service.ChannelService;
import com.unzen.common.template.DirectiveHandler;
import com.unzen.common.template.TemplateDirective;

@Component
public class ChannelDirective extends TemplateDirective {
    @Autowired
    private ChannelService channelService;

    @Override
    public String getName() {
        return "channel";
    }

    @Override
    public void execute(DirectiveHandler handler) throws Exception {
        Integer id = handler.getInteger("id", 0);
        handler.put(RESULT, channelService.get(id)).render();
    }
}