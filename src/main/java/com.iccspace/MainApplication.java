package com.iccspace;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.annotation.PostConstruct;

import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.embedded.ConfigurableEmbeddedServletContainer;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.boot.context.embedded.FilterRegistrationBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.client.RestTemplate;


import com.iccspace.token.Audience;
import com.iccspace.token.HTTPBasicAuthorizeAttribute;
import com.iccspace.token.HTTPBearerAuthorizeAttribute;
@SpringBootApplication
@EnableConfigurationProperties(Audience.class)
@ComponentScan("com.iccspace.controller,com.iccspace.service")

@MapperScan("com.iccspace.mapper")
public class MainApplication implements EmbeddedServletContainerCustomizer,CommandLineRunner {
	
	private static Logger logger=LoggerFactory.getLogger(MainApplication.class);
	
	@Value("${app.name}")
    private String name;

    
	@Override
	public void customize(ConfigurableEmbeddedServletContainer container) { 
		container.setPort(8896);

	}

	public static void main(String[] args) {
		ConfigurableApplicationContext context=SpringApplication.run(MainApplication.class, args);
		String[] names = context.getBeanDefinitionNames();  
        Arrays.sort(names);
        for (String string : names) {  
        	logger.info(string);
        }
	}
	@Override  
    public void run(String... args) throws Exception {  
        RestTemplate template = new RestTemplate();  
        //Greeting greeting = template.getForObject("http://localhost:8080/greeting?name=323", Greeting.class);  
        //System.err.println(greeting);         
    }
	
	/**
	 * 注册filter
	 * @return
	 */
	@Bean  
    public FilterRegistrationBean  basicFilterRegistrationBean() {  
        FilterRegistrationBean registrationBean = new FilterRegistrationBean();  
        
        HTTPBasicAuthorizeAttribute httpBasicFilter = new HTTPBasicAuthorizeAttribute();  
        registrationBean.setFilter(httpBasicFilter);  
        List<String> urlPatterns = new ArrayList<String>();  
        urlPatterns.add("/user/getuser");  
        registrationBean.setUrlPatterns(urlPatterns);  
        return registrationBean;  
    }  
      
    @Bean  
    public FilterRegistrationBean jwtFilterRegistrationBean(){  
        FilterRegistrationBean registrationBean = new FilterRegistrationBean();  
        HTTPBearerAuthorizeAttribute httpBearerFilter = new HTTPBearerAuthorizeAttribute();  
        registrationBean.setFilter(httpBearerFilter);  
        List<String> urlPatterns = new ArrayList<String>();  
        urlPatterns.add("/user/getUser");  
        registrationBean.setUrlPatterns(urlPatterns);  
        return registrationBean;  
    }

}
