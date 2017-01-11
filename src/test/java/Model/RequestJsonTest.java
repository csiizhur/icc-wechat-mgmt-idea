package Model;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.iccspace.controller.model.ShopsEditModel;
import org.junit.Test;

import java.io.IOException;
import java.sql.Timestamp;

/**
 * Created by zhur on 2017/1/11.
 *
 * @author:zhur
 * @description:
 * @date:create in 2017-01-11-13:54
 */
public class RequestJsonTest {

    /**
     * Rest 接收json
     */
    @Test
    public void RequestStringToTimestamp(){
        String a = "{\"shopsId\":\"myName\",\"releaseDate\":\"2014-11-11 19:01:58\"}";
        //String a1 = "{shopsId='zhangsan',releaseDate=1412511615062}";
        ObjectMapper objectMapper = new ObjectMapper();
        try{
            ShopsEditModel shopsEditModel = objectMapper.readValue(a,ShopsEditModel.class);
            System.err.print(shopsEditModel);

            ShopsEditModel s = new ShopsEditModel();
            s.setReleaseDate(new Timestamp(System.currentTimeMillis()));
            s.setShopsId("qwe");
            String ss = objectMapper.writeValueAsString(s);
            System.err.print(ss);
        }catch(IOException e){
            System.err.print(e);
        }
    }
}
