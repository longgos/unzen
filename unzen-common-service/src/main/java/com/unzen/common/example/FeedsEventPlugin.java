package com.unzen.common.example;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.unzen.common.event.FeedsEvent;
import com.unzen.common.hook.event.FeedsEventHook;

/**
 * @author Beldon 2015/10/29
 */
@Component
public class FeedsEventPlugin implements FeedsEventHook.FeedsEventListener {
    private Logger log = LoggerFactory.getLogger(getClass());
    @Override
    public void onEvent(FeedsEvent event) {
        log.debug("插件发出来的消息:您又派发动态了");
    }
}
