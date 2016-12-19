package com.iccspace.token;

import java.security.MessageDigest;
/**
 * 获取md5的方法类
 * @description
 * @author zhur
 * @date 2016年12月6日-上午9:23:00
 */
public class MyMD5Utils {  
    public static String getMD5(String inStr) {  
        MessageDigest md5 = null;  
        try {  
            md5 = MessageDigest.getInstance("MD5");  
        } catch (Exception e) {  
              
            e.printStackTrace();  
            return "";  
        }  
        char[] charArray = inStr.toCharArray();  
        byte[] byteArray = new byte[charArray.length];  
   
        for (int i = 0; i < charArray.length; i++)  
            byteArray[i] = (byte) charArray[i];  
   
        byte[] md5Bytes = md5.digest(byteArray);  
   
        StringBuffer hexValue = new StringBuffer();  
   
        for (int i = 0; i < md5Bytes.length; i++) {  
            int val = ((int) md5Bytes[i]) & 0xff;  
            if (val < 16)  
                hexValue.append("0");  
            hexValue.append(Integer.toHexString(val));  
        }  
   
        return hexValue.toString();  
    } 
    /*public static void main(String[] args) {
		System.err.println(getMD5("111111salt"));//685e1ca3c6bb2562df2ccf84d5182d23
	}*/
}