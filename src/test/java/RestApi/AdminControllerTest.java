package RestApi;

import static org.hamcrest.Matchers.containsString;
import static org.junit.Assert.assertNotNull;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;
import com.alibaba.fastjson.JSONObject;
import com.iccspace.MainApplication;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.TestRestTemplate;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.WebApplicationContext;

import static org.junit.Assert.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
/**
 * Api测试
 * @author   单红宇(365384722)
 * @myblog  http://blog.csdn.net/catoop/
 * @create    2016年2月23日
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = MainApplication.class)
//@WebAppConfiguration // 使用@WebIntegrationTest注解需要将@WebAppConfiguration注释掉
@WebIntegrationTest("server.port:0")// 使用0表示端口号随机，也可以具体指定如8888这样的固定端口
public class AdminControllerTest {

    private String dateReg;
    private Pattern pattern;
    private RestTemplate template = new TestRestTemplate();

    @Value("${local.server.port}")// 注入端口号
    private int port;

    @Test
    public void testOauthToken(){
        String url = "http://localhost:"+8896+"/Api/admin/oauth/token";
        //MultiValueMap<String, String> map = new LinkedMultiValueMap<String, String>();
        //map.add("mobile", "111111");
        //map.add("password", "111111");
        Map<String,String> map = new HashMap<String,String>();
        map.put("mobile","111111");
        map.put("password","111111");

        HttpHeaders headers = new HttpHeaders();
        MediaType type = MediaType.parseMediaType("application/json; charset=UTF-8");
        headers.setContentType(type);
        headers.add("Accept", MediaType.APPLICATION_JSON.toString());

        HttpEntity<String> httpEntity = new HttpEntity<String>(JSONObject.toJSONString(map),headers);

        //ObjectMapper objectMapper = new ObjectMapper();
        //String jsonData = objectMapper.writeValueAsString(map).toString();
        //HttpEntity<String> httpEntity = new HttpEntity<String>(jsonData,headers);

        String result = template.postForObject(url, httpEntity, String.class);
        System.out.println(result);
        assertNotNull(result);
        assertThat(result, Matchers.containsString("bearer"));
    }

    @Autowired
    private WebApplicationContext context;

    private MockMvc mockMvc;

    @Before
    public void setupMockMvc() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    /**
     * java实现的jsonpath工具测试返回消息数据
     * @throws Exception
     */
    @Test
    public void webappPublisherApi() throws Exception {
        //MockHttpServletRequestBuilder.accept方法是设置客户端可识别的内容类型
        //MockHttpServletRequestBuilder.contentType,设置请求头中的Content-Type字段,表示请求体的内容类型
        mockMvc.perform(get("/shops/cuzuList")
                .accept(MediaType.APPLICATION_JSON_UTF8))

                .andExpect(status().isOk())
                .andExpect(content().string(containsString("商业街卖场")))
                .andExpect(jsonPath("$.errcode").value(0))
                .andExpect(jsonPath("$.resultdata.list.[0].estatesType").value("住宅底商"))
                .andExpect(jsonPath("$.resultdata.list[0].estatesType").value("住宅底商"));
    }

}