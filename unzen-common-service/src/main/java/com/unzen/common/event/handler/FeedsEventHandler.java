/*
+--------------------------------------------------------------------------
|   Mblog [#RELEASE_VERSION#]
|   ========================================
|   Copyright (c) 2014, 2015 mtons. All Rights Reserved
|   http://www.mtons.com
|
+---------------------------------------------------------------------------
*/
package com.unzen.common.event.handler;

import java.text.MessageFormat;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import com.unzen.base.lang.Consts;
import com.unzen.common.core.data.Feeds;
import com.unzen.common.event.FeedsEvent;
import com.unzen.common.service.FeedsService;


/**
 * @author langhsu on 2015/8/18.
 */
@Component
public class FeedsEventHandler implements ApplicationListener<FeedsEvent> {
    private Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private FeedsService feedsService;

    @Async
    @Override
    public void onApplicationEvent(FeedsEvent event) {
        if (event == null) {
            return;
        }

        // 创建动态对象
        Feeds feeds = new Feeds();
        feeds.setType(Consts.FEEDS_TYPE_POST);
        feeds.setOwnId(event.getAuthorId());
        feeds.setPostId(event.getPostId());
        feeds.setAuthorId(event.getAuthorId());

        int ret = feedsService.add(feeds);

        log.debug(MessageFormat.format("成功派发 {0} 条动态!", ret));
    }
}
