package com.unzen.web.message.boot;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import com.unzen.web.message.web.interceptor.BaseInterceptor;

/**
 *	静态资源映射
 */
@Configuration
public class WebMvcConfig extends WebMvcConfigurerAdapter {
    @Autowired
    private BaseInterceptor baseInterceptor;
    @Autowired
    private FastJsonHttpMessageConverter fastJsonHttpMessageConverter;

    @Override
    public void configurePathMatch(PathMatchConfigurer configurer) {
        super.configurePathMatch(configurer);
        configurer.setUseSuffixPatternMatch(false);
    }

    /**
     * Add intercepter
     *
     * @param registry
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        super.addInterceptors(registry);
        registry.addInterceptor(baseInterceptor).addPathPatterns("/**").excludePathPatterns("/dist/**", "/store/**", "/static/**");
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
    	registry.addResourceHandler("/image/**").addResourceLocations("file:F:/word/");
    	registry.addResourceHandler("/avatar/**").addResourceLocations("file:F:/word/avatar/");
        registry.addResourceHandler("/dist/**").addResourceLocations("/dist/");
        registry.addResourceHandler("/static/**").addResourceLocations("/static/");
        registry.addResourceHandler("/store/**").addResourceLocations("/store/");
        super.addResourceHandlers(registry);
    }

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        converters.add(fastJsonHttpMessageConverter);
    }
}
