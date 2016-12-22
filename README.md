# icc-wechat-mgmt-idea
# application 实现 interceptor 的三种方式
    1,application 主类 extends WebMvcConfigurerAdapter 重写 addInterceptors 方法
    2,定义 application 的 inner class extends WebMvcConfigurerAdapter 重写 addInterceptors 方法
    3,@Configuration 配置 extends WebMvcConfigurerAdapter 的bean(application确保能扫描到该bean信息)