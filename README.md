# icc-wechat-mgmt-idea
# application 实现 interceptor 的三种方式
    1,application 主类 extends WebMvcConfigurerAdapter 重写 addInterceptors 方法
    2,定义 application 的 inner class extends WebMvcConfigurerAdapter 重写 addInterceptors 方法
    3,@Configuration 配置 extends WebMvcConfigurerAdapter 的bean(application确保能扫描到该bean信息)

#在SSH session执行 & 代表在后台运行
    [root@iZuf6ayj54m6qqykp29r2bZ iccspace]# java -jar icc-wechat-mgmt-0.0.1-SNAPSHOT.jar  &
    [1] 18736
    CTRL+C关闭当前窗口，程序不终止
    推出窗口会话时，程序终止
#nohup nohup 意思是不挂断运行命令,当账户退出或终端关闭时,程序仍然运行
#>out.file是将command的输出重定向到out.file文件，即输出内容不打印到屏幕上，而是输出到out.file文件中
    [root@iZuf6ayj54m6qqykp29r2bZ iccspace]# nohup java -jar icc-wechat-mgmt-0.0.1-SNAPSHOT.jar >logs/start.txt &
    [1] 18899
#jobs 列出后台作业
    [root@iZuf6ayj54m6qqykp29r2bZ iccspace]# jobs
    [1]+  Running                 nohup java -jar icc-wechat-mgmt-0.0.1-SNAPSHOT.jar > logs/start.txt &
#fg 就会列出所有后台执行的作业，并且每个作业前面都有个编号。如果想将某个作业调回前台控制，只需要 fg + 编号即可。
    [root@iZuf6ayj54m6qqykp29r2bZ iccspace]# fg 1
    nohup java -jar icc-wechat-mgmt-0.0.1-SNAPSHOT.jar > logs/start.txt

#netstat -nlp |grep :8896(某端口占用的线程pid)
    [root@iZuf6ayj54m6qqykp29r2bZ iccspace]# netstat -nlp |grep :8896
    tcp        0      0 0.0.0.0:8896                0.0.0.0:*                   LISTEN      18899/java
