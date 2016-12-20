package com.iccspace.content;

import org.springframework.boot.context.embedded.ConfigurableEmbeddedServletContainer;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.boot.web.servlet.ErrorPage;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
public class MyCustomizationBean implements EmbeddedServletContainerCustomizer {
    /**
     * @param container 
     * @see org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer#customize(org.springframework.boot.context.embedded.ConfigurableEmbeddedServletContainer) 
     */  
    @Override  
    public void customize(ConfigurableEmbeddedServletContainer container) {
         container.setContextPath("/Api");
         container.setPort(8081);  
         container.setSessionTimeout(30);

         //错误
        container.addErrorPages(new ErrorPage(HttpStatus.BAD_REQUEST, "/screen/error"));
    }  
       
}