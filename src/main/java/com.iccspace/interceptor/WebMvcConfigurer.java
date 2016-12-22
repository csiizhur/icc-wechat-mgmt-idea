package com.iccspace.interceptor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by Administrator on 2016/12/22.
 */
@Configuration
public class WebMvcConfigurer extends WebMvcConfigurerAdapter {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    public void addInterceptors2(InterceptorRegistry registry) {
        registry.addInterceptor(new HandlerInterceptorAdapter() {

            @Override
            public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
                                   Object handler) throws Exception {

                return true;
            }
        }).addPathPatterns("/Api/admin/putCache");
    }

    /**
     * 将 interceptor注册进interceptor链
     * @param registry
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry){
        logger.info("interceptor 检查 authorization");
        registry.addInterceptor(new AdminSecurityInterceptor()).addPathPatterns("/**");
        super.addInterceptors(registry);
    }
}