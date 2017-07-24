package com.flash.toolbar.omp.common.filter;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.flash.toolbar.omp.common.util.Constant;

public class LoginFilter implements Filter{
	/*
	 * 鉴权失败跳转地址
	 */
	private String authenFailUrl;

	/**
	 * 忽略的地址
	 */
	private List<String> excludeUrls;
	
	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void doFilter(ServletRequest req, ServletResponse res,
			FilterChain filter) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) res;
		HttpSession session  =  request.getSession();
		Cookie[] cookies = request.getCookies();
		if (cookies != null) {
			for (int i = 0; i < cookies.length; i++) {
				String value = cookies[i].getName();
				if (value.equals("JSESSIONID")) {
					cookies[i].setMaxAge(600);
					response.addCookie(cookies[i]);
				}
			}
		}		
		if(ignoreRequest(request)){
			filter.doFilter(req, res);
		}else{
			if(session!=null && session.getAttribute(Constant.USER_MODEL_SESSION)!=null){
				filter.doFilter(req, res);
			}else{
				response.sendRedirect(authenFailUrl);
			}
		}
	}
	
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		// 权限失败后跳转页面
		this.authenFailUrl = filterConfig.getInitParameter("authenFailUrl");
		// 例外的页面，登录页面是sso的例外
		String excludeUrlStr = filterConfig.getInitParameter("excludeUrls");
		if (excludeUrlStr != null && !"".equals(excludeUrlStr.trim())) {
			excludeUrls = Arrays.asList(excludeUrlStr.split(","));
		}
		
	}
	
	/**
	 * 对于配置的url进行忽略
	 * 
	 * @param httpRequest
	 * @return
	 */
	private boolean ignoreRequest(HttpServletRequest httpRequest) {
		boolean ignore = false;
		StringBuffer aaa = httpRequest.getRequestURL();
		if (excludeUrls != null && !excludeUrls.isEmpty()) {
			for (String url : excludeUrls) {
				if (httpRequest.getRequestURL().indexOf(url) != -1) {
					ignore = true;
					break;
				}
			}
		}
		return ignore;
	}	
}
