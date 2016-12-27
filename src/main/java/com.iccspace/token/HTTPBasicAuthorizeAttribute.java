package com.iccspace.token;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.binary.Base64;

import com.fasterxml.jackson.databind.ObjectMapper;
/**
 * 如果请求的Header中存在Authorization: Basic 头信息，且用户名密码正确，则继续原来的请求，否则返回没有权限的错误信息
 * @description
 * @author zhur
 * @date 2016年12月6日-上午9:50:02
 */
@SuppressWarnings("restriction")  
public class HTTPBasicAuthorizeAttribute implements Filter{  
      
    private static String Name = "test";  
    private static String Password = "test";  
  
    @Override  
    public void destroy() {  
        // TODO Auto-generated method stub  
          
    }  
  
    @Override  
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)  
            throws IOException, ServletException {  
        // TODO Auto-generated method stub  
          
        ResultStatusCode resultStatusCode = checkHTTPBasicAuthorize(request);  
        if (resultStatusCode != ResultStatusCode.OK)  
        {  
            HttpServletResponse httpResponse = (HttpServletResponse) response;  
            httpResponse.setCharacterEncoding("UTF-8");
            httpResponse.setContentType("application/json; charset=utf-8");   
            httpResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);  
  
            ObjectMapper mapper = new ObjectMapper();  
              
            ResultMsg resultMsg = new ResultMsg(ResultStatusCode.PERMISSION_DENIED.getErrcode(), ResultStatusCode.PERMISSION_DENIED.getErrmsg(), null);  
            httpResponse.getWriter().write(mapper.writeValueAsString(resultMsg));  
            return;  
        }  
        else  
        {  
            chain.doFilter(request, response);  
        }  
    }  
  
    @Override  
    public void init(FilterConfig arg0) throws ServletException {  
        // TODO Auto-generated method stub  
          
    }  
      
    private ResultStatusCode checkHTTPBasicAuthorize(ServletRequest request)  
    {  
        try  
        {  
            HttpServletRequest httpRequest = (HttpServletRequest)request;  
            String auth = httpRequest.getHeader("Authorization");  
            if ((auth != null) && (auth.length() > 6))  
            {  
                String HeadStr = auth.substring(0, 5).toLowerCase();  
                if (HeadStr.compareTo("basic") == 0)  
                {  
                    auth = auth.substring(6, auth.length());    
                    String decodedAuth = getFromBASE64(auth);  
                    if (decodedAuth != null)  
                    {  
                        String[] UserArray = decodedAuth.split(":");  
                          
                        if (UserArray != null && UserArray.length == 2)  
                        {  
                            if (UserArray[0].compareTo(Name) == 0  
                                    && UserArray[1].compareTo(Password) == 0)  
                            {  
                                return ResultStatusCode.OK;  
                            }  
                        }  
                    }  
                }  
            }  
            return ResultStatusCode.PERMISSION_DENIED;  
        }  
        catch(Exception ex)  
        {  
            return ResultStatusCode.PERMISSION_DENIED;  
        }  
          
    }  
      
    private String getFromBASE64(String s) {    
        if (s == null)    
            return null;    
        //BASE64Decoder decoder = new BASE64Decoder();
        Base64 base=new Base64();
        try {
            byte[] b = base.decode(s);    
            return new String(b);    
        } catch (Exception e) {    
            return null;    
        }    
    }  
  
}