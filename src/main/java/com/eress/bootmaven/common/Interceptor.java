package com.eress.bootmaven.common;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Interceptor extends HandlerInterceptorAdapter {
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		request.getSession().setAttribute("userId", "test");
		if (request.getSession().getAttribute("userId") == null && !request.getRequestURI().contains("/login/")) {
			response.sendRedirect("/login/loginForm.do");
			return false;
		} else {
			return true;
		}
	}
}
