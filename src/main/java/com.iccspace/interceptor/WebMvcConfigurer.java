package com.iccspace.interceptor;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Configuration
public class WebMvcConfigurer extends WebMvcConfigurerAdapter {

  public void addInterceptors(InterceptorRegistry registry) {
      registry.addInterceptor(new HandlerInterceptorAdapter() {

          @Override
          public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
                                   Object handler) throws Exception {
              System.out.println("interceptor====222");
              return true;
          }
      }).addPathPatterns("/*");
  }

  public void addInterceptors2(InterceptorRegistry registry){
      registry.addInterceptor(new AdminSecurityInterceptor()).addPathPatterns("/shops/**");
  }
}