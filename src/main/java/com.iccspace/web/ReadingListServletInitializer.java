package com.iccspace.web;

import com.iccspace.MainApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Configuration;

/**
 * Created by zhur on 2016/12/26.
 * @author:zhur
 * @description:
 * @date:create in 2016-12-26-17:06
 */
@Configuration
public class ReadingListServletInitializer extends SpringBootServletInitializer{

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(MainApplication.class);
        //return super.configure(builder);
    }
}
