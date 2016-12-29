package Dao;

import com.iccspace.MainApplication;
import com.iccspace.mapper.AuditMapper;
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
public class DateTest{

    @Autowired
    private AuditMapper auditMapper;

    @Test
    public void testQueryTime(){
        assertArrayEquals(
                new Object[]{
                        auditMapper.queryTimestamp()!= null,
                        auditMapper.queryDate() !="",
                        auditMapper.queryDateNotByMap()!=null
                },
                new Object[]{
                        true,
                        false,
                        true
                }
        );
    }

    @Test
    public void test(){
        String t=auditMapper.queryTimestamp();
        String t2=auditMapper.queryDate();
        String t3=auditMapper.queryDateNotByMap();
    }
}