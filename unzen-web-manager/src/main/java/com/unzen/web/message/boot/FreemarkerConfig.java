package com.unzen.web.message.boot;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import com.unzen.common.template.directive.AuthcDirective;
import com.unzen.common.template.directive.AuthorContentsDirective;
import com.unzen.common.template.directive.BannerDirective;
import com.unzen.common.template.directive.ChannelDirective;
import com.unzen.common.template.directive.ContentsDirective;
import com.unzen.common.template.directive.NumberDirective;
import com.unzen.common.template.directive.ResourceDirective;
import com.unzen.common.template.method.TimeAgoMethod;
import com.unzen.web.message.shiro.tags.ShiroTags;

import freemarker.template.Configuration;
import freemarker.template.TemplateModelException;

/**
 * Created by langhsu on 2017/11/14.
 */
@Component
public class FreemarkerConfig {
    @Autowired
    private Configuration configuration;
    @Autowired
    private ApplicationContext applicationContext;

    @PostConstruct
    public void setSharedVariable() throws TemplateModelException {
        configuration.setSharedVariable("author_contents", applicationContext.getBean(AuthorContentsDirective.class));
        configuration.setSharedVariable("channel", applicationContext.getBean(ChannelDirective.class));
        configuration.setSharedVariable("contents", applicationContext.getBean(ContentsDirective.class));
        configuration.setSharedVariable("num", applicationContext.getBean(NumberDirective.class));
        configuration.setSharedVariable("resource", applicationContext.getBean(ResourceDirective.class));
        configuration.setSharedVariable("authc", applicationContext.getBean(AuthcDirective.class));
        configuration.setSharedVariable("banner", applicationContext.getBean(BannerDirective.class));

        configuration.setSharedVariable("timeAgo", new TimeAgoMethod());
        configuration.setSharedVariable("shiro", new ShiroTags());
    }
}
