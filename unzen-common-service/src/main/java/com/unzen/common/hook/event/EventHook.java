package com.unzen.common.hook.event;

import org.springframework.context.ApplicationEvent;

import com.unzen.common.hook.Hook;


/**
 * Event钩子
 *
 * @author Beldon
 */
public interface EventHook<T extends ApplicationEvent> extends Hook {
    /**
     * Event监听
     *
     * @param event
     */
    void onApplicationEvent(T event);

    /**
     * 获取Event类做做key
     *
     * @return
     */
    Class getEventClass();
}
