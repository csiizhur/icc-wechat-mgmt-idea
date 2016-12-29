package Service;

import com.alibaba.fastjson.JSONObject;
import com.iccspace.MainApplication;
import com.iccspace.service.PropertyDetailService;
import com.iccspace.token.ResultMsg;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

/**
 * Created by zhur on 2016/12/29.
 *
 * @author:zhur
 * @description:
 * @date:create in 2016-12-29-17:15
 */

@RunWith(SpringJUnit4ClassRunner.class)
//@SpringBootApplication(scanBasePackageClasses = MainApplication.class)
@SpringApplicationConfiguration(classes = MainApplication.class)
@WebAppConfiguration
public class PropertyDetailServiceTest {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private PropertyDetailService propertyDetailService;

    @Test
    public void testQueryProperty(){
        String shopsId = "d366181ddddf4de595d2731b3c3dc27a";
        String releaseUserId = "f85ea646c33211e6b0d000163e020977";

        ResultMsg resultMsg = propertyDetailService.propertyDetails(shopsId,releaseUserId);

        logger.info(JSONObject.toJSONString(resultMsg));
    }

}
