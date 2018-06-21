package com.icss.hr.common;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * 过滤器
 */
public class CommonFilter implements Filter {

	public void destroy() {

	}

	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain)
			throws IOException, ServletException {

		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) resp;

		// 设置编码
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");

		// 设置允许其他域请求
		response.setHeader("Access-Control-Allow-Origin", "*");

		// 判断用户是否登录
		String uri = request.getRequestURI();
		System.out.println("请求uri：" + uri);

		String baseUrl = request.getContextPath();// 返回web应用名称
				
		// 判断请求url地址是否需要用户已经登录
		if (!uri.equals(baseUrl + "/") 
				&& !uri.equals(baseUrl + "/login.html")
				&& !uri.equals(baseUrl + "/logout.jsp")
				&& !uri.equals(baseUrl + "/emp/login")
				&& !uri.startsWith(baseUrl + "/css") 
				&& !uri.startsWith(baseUrl + "/js")
				&& !uri.startsWith(baseUrl + "/images")) {

			// 判断session是否存在
			HttpSession session = request.getSession();

			String empLoginName = (String) session.getAttribute("empLoginName");

			if (empLoginName == null) {

				// 判断是否是ajax请求
				if (request.getHeader("x-requested-with") != null
						&& request.getHeader("x-requested-with").equalsIgnoreCase("XMLHttpRequest")) {
					// 设置一个自定义报头
					response.setHeader("sessionStatus", "timeout");
				} else {
					response.sendRedirect(baseUrl + "/logout.jsp");
				}

				return;
			}

		}		
								
		// 继续向下执行
		chain.doFilter(request, response);
			
	}

	public void init(FilterConfig fConfig) throws ServletException {

	}

}