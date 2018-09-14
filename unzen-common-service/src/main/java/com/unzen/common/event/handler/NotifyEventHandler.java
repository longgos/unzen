package com.unzen.common.event.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import com.unzen.base.lang.Consts;
import com.unzen.common.core.data.Notify;
import com.unzen.common.core.data.Post;
import com.unzen.common.event.NotifyEvent;
import com.unzen.common.service.NotifyService;
import com.unzen.common.service.PostService;

/**
 * @author langhsu on 2015/8/31.
 */
@Component
public class NotifyEventHandler implements ApplicationListener<NotifyEvent> {
    @Autowired
    private NotifyService notifyService;
    @Autowired
    private PostService postService;

    @Async
    @Override
    public void onApplicationEvent(NotifyEvent event) {
        Notify nt = new Notify();
        nt.setPostId(event.getPostId());
        nt.setFromId(event.getFromUserId());
        nt.setEvent(event.getEvent());

        switch (event.getEvent()) {
            case Consts.NOTIFY_EVENT_FAVOR_POST:
                Post p = postService.get(event.getPostId());
                nt.setOwnId(p.getAuthorId());
                break;
            case Consts.NOTIFY_EVENT_COMMENT:
            case Consts.NOTIFY_EVENT_COMMENT_REPLY:
                Post p2 = postService.get(event.getPostId());
                nt.setOwnId(p2.getAuthorId());

                // 自增评论数
                postService.identityComments(event.getPostId());

                break;
            default:
                nt.setOwnId(event.getToUserId());
        }

        notifyService.send(nt);
    }
}
