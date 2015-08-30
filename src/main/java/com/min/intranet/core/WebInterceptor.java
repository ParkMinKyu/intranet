package com.min.intranet.core;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;


@Controller
public class WebInterceptor implements HandlerInterceptor{

	private static final Logger logger = LoggerFactory.getLogger(WebInterceptor.class);
		
	@Override
	public void afterCompletion(HttpServletRequest request,
			HttpServletResponse response, Object arg2, Exception arg3)
			throws Exception {
		// TODO Auto-generated method stub
		logger.info("afterCompletion");
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response,
			Object arg2, ModelAndView arg3) throws Exception {
		// TODO Auto-generated method stub
		logger.info("postHandle");
	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
			Object arg2) throws Exception {
		// TODO Auto-generated method stub
		HttpSession httpSession = request.getSession();
		if(httpSession.getAttribute(CommonUtil.SESSION_USER) == null){
			response.sendRedirect(request.getContextPath()+"/common/error.do?error=sessionfalse");
			return false;
		}
		return true;
	}

}
