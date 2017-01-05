package Dao;

import com.iccspace.MainApplication;
import com.iccspace.controller.model.ShopsListRequest;
import com.iccspace.mapper.AuditMapper;
import com.iccspace.mapper.ShopsHistoryMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import static org.junit.Assert.assertArrayEquals;

/**
 * Created by zhur on 2016/12/29.
 * @author:zhur
 * @description:
 * @date:create in 2016-12-29-9:46
 */
@RunWith(SpringJUnit4ClassRunner.class) // SpringJUnit支持，由此引入Spring-Test框架支持！
@SpringApplicationConfiguration(classes = MainApplication.class) // Spring-boot-test 指定我们SpringBoot工程的Application启动类
@WebAppConfiguration // 由于是Web项目，Junit需要模拟ServletContext，因此我们需要给我们的测试类加上@WebAppConfiguration。
public class ShopsTest {

    @Autowired
    private ShopsHistoryMapper shopsHistoryMapper;

    @Test
    public void test(){
        ShopsListRequest shopsListRequest = new ShopsListRequest();
        shopsListRequest.setReleaseType(1);
        shopsListRequest.setEstatesType(null);
        shopsHistoryMapper.queryShopsHistoryList(shopsListRequest);
    }

    @Test
    public void testString(){
        String str="";
        String a=null;
        System.out.print(str+a);
    }
}