package Des;

import com.iccspace.controller.code.DesUtil;
import com.iccspace.token.MyMD5Utils;
import org.junit.Test;

/**
 * Created by zhur on 2016/12/29.
 *
 * @author:zhur
 * @description:
 * @date:create in 2016-12-29-15:49
 */
public class DesUtilTest {

    @Test
    public void testEnc(){
        DesUtil desObj = new DesUtil();
        String key1 = "1";
        String key2 = "2";
        String key3 = "3";
        String data = "admin";
        String str = desObj.strEnc(data, key1, key2, key3);
        System.out.println(str);
        String dec = desObj.strDec(str, key1, key2, key3);
        System.out.println(dec);
    }

    @Test
    public void testMyMd5(){
        String md5p = MyMD5Utils.getMD5("111111salt");//685e1ca3c6bb2562df2ccf84d5182d23
        System.err.println(md5p);
    }
}
