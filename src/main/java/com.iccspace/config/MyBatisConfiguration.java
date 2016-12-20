package com.iccspace.config;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.github.pagehelper.PageHelper;

/**
 * 注册MyBatis分页插件PageHelper
 * @author Angel(QQ:412887952)
 * @version v.0.1
 */
@Configuration
public class MyBatisConfiguration {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Bean
    public PageHelper pageHelper() {
        logger.info("MyBatisConfiguration.pageHelper()");
        PageHelper pageHelper = new PageHelper();
        Properties p = new Properties();
        p.setProperty("offsetAsPageNum", "true");
        p.setProperty("rowBoundsWithCount", "true");
        p.setProperty("reasonable", "true");
        pageHelper.setProperties(p);
        return pageHelper;
    }
       
}