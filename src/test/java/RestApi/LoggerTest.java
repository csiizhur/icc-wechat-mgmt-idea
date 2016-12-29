package RestApi;

import com.iccspace.MainApplication;
import com.iccspace.controller.AdminController;

import org.hamcrest.Matchers;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.boot.test.rule.OutputCapture;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.assertThat;

/**
 * 捕获输出日志
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = MainApplication.class)
//@WebAppConfiguration // 使用@WebIntegrationTest注解需要将@WebAppConfiguration注释掉
@WebIntegrationTest("server.port:0")// 使用0表示端口号随机，也可以具体指定如8888这样的固定端口
public class LoggerTest {

    @Value("${local.server.port}")// 注入端口号
    private int port;

    private static final Logger logger = LoggerFactory.getLogger(AdminController.class);

    @Rule
    // 这里注意，使用@Rule注解必须要用public
    public OutputCapture capture = new OutputCapture();

    @Test
    public void testLog(){
        System.out.println("HelloWorld");
        logger.info("logo日志也会被capture捕获测试输出");
        assertThat(capture.toString(), Matchers.containsString("World"));
    }
}