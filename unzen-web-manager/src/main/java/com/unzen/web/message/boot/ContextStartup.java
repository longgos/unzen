package com.unzen.web.message.boot;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import javax.servlet.ServletContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import org.springframework.web.context.ServletContextAware;

import com.unzen.base.context.AppContext;
import com.unzen.base.lang.Consts;
import com.unzen.base.print.Printer;
import com.unzen.common.core.data.Config;
import com.unzen.common.service.ChannelService;
import com.unzen.common.service.ConfigService;

/**
 * 加载配置信息到系统
 *
 */
@Component
public class ContextStartup implements ApplicationRunner, Ordered, ServletContextAware {
    @Autowired
    private ConfigService configService;
    @Autowired
    private ChannelService channelService;
    @Autowired
    private AppContext appContext;

    private ServletContext servletContext;

    @Override
    public void run(ApplicationArguments applicationArguments) throws Exception {
        Timer timer = new Timer("init config");
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                Printer.info("initialization ...");
                List<Config> configs = configService.findAll();
                Map<String, String> configMap = new HashMap<>();

                if (configs.isEmpty()) {
                    Printer.error("------------------------------------------------------------");
                    Printer.error("- ERROR: No initialization data is imported (db_mblog.sql) -");
                    Printer.error("-       Import the initial database and start again.       -");
                    Printer.error("------------------------------------------------------------");
                    System.exit(1);
                } else {

                    if (configs.size() < 13) {
                        Printer.error("-----------------------------------------------------------------");
                        Printer.error("- ERROR: The data is not complete, Please import (db_mblog.sql) -");
                        Printer.error("-----------------------------------------------------------------");
                    }
                    /** jdk1.7不兼容1.8版本新循环格式。暂时挂起选用旧循环方式 */
                    for (int i = 0; i < configs.size(); i++) {
                    	servletContext.setAttribute(configs.get(i).getKey(), configs.get(i).getValue());
                    	configMap.put(configs.get(i).getKey(), configs.get(i).getValue());
					}
                  
                    /*((Iterable<Config>) configs).forEach(conf -> {
//						servletContext.setAttribute(conf.getKey(), conf.getValue());
                        configMap.put(conf.getKey(), conf.getValue());
                    });*/
                }

                appContext.setServletContext(servletContext);
                appContext.setConfig(configMap);

                servletContext.setAttribute("channels", channelService.findAll(Consts.STATUS_NORMAL));

                Printer.info("OK, completed");
            }
        }, 1 * Consts.TIME_MIN);
    }

    @Override
    public int getOrder() {
        return 2;
    }

    @Override
    public void setServletContext(ServletContext servletContext) {
        this.servletContext = servletContext;
    }
}
