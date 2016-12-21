package com.iccspace.interceptor;

import org.slf4j.Logger;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 拦截器
 */
public class AdminSecurityInterceptor implements HandlerInterceptor {

	private Logger logger= org.slf4j.LoggerFactory.getLogger(this.getClass());

	@Override
    public boolean preHandle(HttpServletRequest request,
							 HttpServletResponse response, Object handler) throws Exception {
        String authorization=request.getHeader("Authorization");
        logger.info("authorization-->"+authorization);
        if(StringUtils.isEmpty(authorization)){
        	return false;
		}
        return true;
	}

	@Override
	public void postHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
	}

	@Override
	public void afterCompletion(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex)
			throws Exception {

	}

}