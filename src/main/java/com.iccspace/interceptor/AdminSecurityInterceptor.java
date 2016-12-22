package com.iccspace.interceptor;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.iccspace.token.ResultMsg;
import com.iccspace.token.ResultStatusCode;
import org.slf4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * interceptor
 * Created by Administrator on 2016/12/22.
 */
@Component
public class AdminSecurityInterceptor implements HandlerInterceptor {

	private Logger logger= org.slf4j.LoggerFactory.getLogger(this.getClass());

	@Override
    public boolean preHandle(HttpServletRequest request,
							 HttpServletResponse response, Object handler) throws Exception {
        String authorization=request.getHeader("Authorization");
        logger.info("authorization-->"+authorization);

		//HttpSession session = request.getSession(false);
		String requestUrl = request.getRequestURI();
		if (StringUtils.isEmpty(authorization) || !authorization.contains("bearer")) {
			if (requestUrl.endsWith("/admin/oauth/token") || requestUrl.endsWith("/signUpAccount")) {
				return true;
			} else {
				//response.sendRedirect("www.iccspace.cn/a.html");
				HttpServletResponse httpResponse = (HttpServletResponse) response;
				httpResponse.setCharacterEncoding("UTF-8");
				httpResponse.setContentType("application/json; charset=utf-8");
				httpResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

				ObjectMapper mapper = new ObjectMapper();

				ResultMsg resultMsg = new ResultMsg(ResultStatusCode.INVALID_TOKEN.getErrcode(), ResultStatusCode.INVALID_TOKEN.getErrmsg(), null);
				httpResponse.getWriter().write(mapper.writeValueAsString(resultMsg));
				return false;
			}
		} else {
			/*userVariable.set((User) session.getAttribute(Constants.SESSION_USER));
			String tempUrl = requestUrl.substring(requestUrl.indexOf("/"), requestUrl.lastIndexOf("/"));
			@SuppressWarnings("unchecked")
			List<Resource> resources = (List<Resource>) session.getAttribute(Constants.SESSION_RESOURCE);
			if (null != resources && !resources.isEmpty()) {
				for (Resource resource : resources) {
					if (StringUtils.hasText(resource.getUrl()) && (tempUrl.equals(resource.getUrl()) || requestUrl.equals(resource.getUrl()))) {
						return true;
					}
				}
			}
			response.sendRedirect("/unentitled.htm");
			return false;*/
			return true;
		}
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