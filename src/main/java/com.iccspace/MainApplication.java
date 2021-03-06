package com.iccspace;

import java.util.*;
import javax.servlet.MultipartConfigElement;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.iccspace.servlet.MyServlet;
import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.embedded.ConfigurableEmbeddedServletContainer;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.boot.context.embedded.FilterRegistrationBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;
import com.iccspace.token.Audience;
import com.iccspace.token.HTTPBasicAuthorizeAttribute;
import com.iccspace.token.HTTPBearerAuthorizeAttribute;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;



@SpringBootApplication
@EnableConfigurationProperties(Audience.class)
@ComponentScan("com.iccspace.controller,com.iccspace.service,com.iccspace.interceptor,com.iccspace.config")
//@Configuration
@MapperScan("com.iccspace.mapper")
public class MainApplication extends WebMvcConfigurerAdapter implements EmbeddedServletContainerCustomizer,CommandLineRunner {
	
	private static Logger logger=LoggerFactory.getLogger(MainApplication.class);
	
	@Value("${app.name}")
    private String name;

    /**
     * 设置port
     * EmbeddedServletContainerCustomizer
     * @param container
     */
	@Override
	public void customize(ConfigurableEmbeddedServletContainer container) { 
		container.setPort(8896);
		container.setContextPath("/Api");

	}

	public static void main(String[] args) {
        //SpringApplication.run(MainApplication.class, args);
		ConfigurableApplicationContext context=SpringApplication.run(MainApplication.class, args);
		String[] names = context.getBeanDefinitionNames();  
        Arrays.sort(names);
        for (String string : names) {  
        	logger.info(string);
        }
	}

    /**
     * application启动运行业务
     * 模拟GET和POST
     * @param args
     * @throws Exception
     */
	@Override  
    public void run(String... args) throws Exception {  
        RestTemplate template = new RestTemplate();  
        //Object greeting = template.getForObject("http://localhost:8896/Api/oauth/token?name=323", Object.class);
        //System.err.println("restTemplate:GET--->"+greeting);

        HttpHeaders headers = new HttpHeaders();
        MediaType type = MediaType.parseMediaType("application/json; charset=UTF-8");
        headers.setContentType(type);
        headers.add("Accept", MediaType.APPLICATION_JSON.toString());

        Map<String,String> params=new HashMap<>();
        params.put("userName","测试号");
        params.put("mobile","13916176592");
        params.put("password","111111");
        params.put("clientId","098f6bcd4621d373cade4e832627b4f6");

        String url="http://127.0.0.1:8896/Api/admin/oauth/token";

        ObjectMapper mapper=new ObjectMapper();
        String jsonObj = mapper.writeValueAsString(params);

        HttpEntity<String> formEntity = new HttpEntity<String>(jsonObj, headers);

        //String result = template.postForObject(url, formEntity, String.class);
        //logger.info("restTemplate:POST--->"+result);
    }
	
	/**
	 * 注册filter
	 * @return
	 */
	@Bean  
    public FilterRegistrationBean  basicFilterRegistrationBean() {

        logger.trace("Trace Message!");
        logger.debug("Debug Message!");
        logger.info("Info Message!");
        logger.warn("Warn Message!");
        logger.error("Error Message!");

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
        urlPatterns.add("/admin/editPassword");
        urlPatterns.add("/shops/*");
        urlPatterns.add("/rents/*");
        registrationBean.setUrlPatterns(urlPatterns);
        return registrationBean;  
    }
    @Override
    public void addCorsMappings(CorsRegistry registry) {

        registry.addMapping("/**").allowedHeaders("*")
                .allowedMethods("*")
                .allowedOrigins("*");

    }
    @Bean
    public FilterRegistrationBean corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.addAllowedOrigin("http://localhost:9000");
        config.addAllowedOrigin("*");
        config.addAllowedOrigin("null");
        config.addAllowedHeader("*");
        config.addAllowedMethod("*");
        source.registerCorsConfiguration("/**", config); // CORS 配置对所有接口都有效
        FilterRegistrationBean bean = new FilterRegistrationBean(new CorsFilter(source));
        bean.setOrder(0);
        return bean;
    }
    /**
     * 注册servlet（Springmvc的DispatcherServlet）
     * @return
     */
    /*public DispatcherServlet dispatcherServlet(){
        DispatcherServlet ds=new DispatcherServlet();
        String nameSpace=ds.getNamespace();
        logger.error(nameSpace);
        return ds;
    }*/

    /**
     * 修改dispatcherServlet默认拦截配置("/")
     * @param
     * @return
     */
    /*@Bean
    public ServletRegistrationBean dispatcherRegistration(DispatcherServlet dispatcherServlet){
        ServletRegistrationBean reg=new ServletRegistrationBean(dispatcherServlet);
        reg.getUrlMappings().clear();
        reg.addUrlMappings("/app/dispatcher*//*");
        reg.addUrlMappings("*.json");
        return reg;

    }*/


    /**
     * 注册一个新的servlet 过滤/druid/*
     * @return
     */
    @Bean
    public ServletRegistrationBean statViewServlet(){
        ServletRegistrationBean srb=new ServletRegistrationBean();
        //srb.setServlet(new StatViewServlet());druid的servlet
        srb.setServlet(new MyServlet());
        srb.addUrlMappings("/druid/*");
        return srb;
    }

    /**
     * characterEncoding编码
     * @return
     */
    @Bean
    public CharacterEncodingFilter characterEncodingFilter() {
        CharacterEncodingFilter filter = new CharacterEncodingFilter();
        filter.setEncoding("UTF-8");
        filter.setForceEncoding(true);
        return filter;
    }

    /**
     * extends WebMvcConfigurerAdapter
     * @param registry
     */
    /*@Override
    public void addInterceptors(InterceptorRegistry registry){
        registry.addInterceptor(new AdminSecurityInterceptor()).addPathPatterns("/admin/putCache");
        super.addInterceptors(registry);
    }*/

    /**
     * inner class extends WebMvcConfigurerAdapter
     */
    /*@Configuration
    static class WebMvcConfigurer extends WebMvcConfigurerAdapter {

        *//*public void addInterceptors(InterceptorRegistry registry) {
            registry.addInterceptor(new HandlerInterceptorAdapter() {

                @Override
                public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
                                         Object handler) throws Exception {
                    request.getContextPath();
                    System.out.println("interceptor====");
                    return true;
                }
            }).addPathPatterns("/admin/**");
        }*//*

        public void addInterceptors(InterceptorRegistry registry){
            logger.info("interceptor 检查 authorization");
            registry.addInterceptor(new AdminSecurityInterceptor()).addPathPatterns("/admin/putCache");
        }
    }*/

    /**
     * 将MultipartConfigElement 注册到DispatcherServlet
     * 单个文件上传5MB
     * 总文件大小10MB
     * @return
     */
    @Bean
    public MultipartConfigElement multipartConfigElement(){
        MultipartConfigFactory multipartConfigFactory=new MultipartConfigFactory();
        multipartConfigFactory.setMaxFileSize("5MB");
        multipartConfigFactory.setMaxRequestSize("10MB");
        String osName = System.getProperty("os.name");
        if(osName!=null && osName.toLowerCase().indexOf("linux")>-1){
            multipartConfigFactory.setLocation("/mnt/iccspace/upload/temp/");
        }else{
            multipartConfigFactory.setLocation("D://upload//");
        }

        return multipartConfigFactory.createMultipartConfig();
    }

    /**
     * static resource
     * urlpattern 去掉contextpath
     * @param resourceHandlerRegistry
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry resourceHandlerRegistry){
        String relativelyPath = System.getProperty("user.dir");
        resourceHandlerRegistry.addResourceHandler("p/**").addResourceLocations("file:"+relativelyPath);
        resourceHandlerRegistry.addResourceHandler("/logs/**").addResourceLocations("classpath:/logs/log4j/");
        String os_name = System.getProperty("os.name");
        if(os_name!=null && os_name.toLowerCase().indexOf("linux")>-1){
            resourceHandlerRegistry.addResourceHandler("/photo/**").addResourceLocations("file:/mnt/iccspace/upload/mgmt_photo/");

        }else{
            resourceHandlerRegistry.addResourceHandler("/photo/**").addResourceLocations("file:D:/upload/photo/");

        }
        //处理多级目录访问

        super.addResourceHandlers(resourceHandlerRegistry);
    }

    /*@Bean
    public Converter<String, Timestamp> addNewConvert() {
        return new Converter<String, Timestamp>() {
            @Override
            public Timestamp convert(String source) {
                try {
                    Timestamp timestamp = new Timestamp(System.currentTimeMillis());
                    timestamp = Timestamp.valueOf(source);
                    return timestamp;
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return null;
            }
        };
    }*/
}
