package com.fr.chain.web.filter;

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

import org.apache.commons.lang3.StringUtils;

public class SessionOutHandle implements Filter {

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		// TODO Auto-generated method stub
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		
		String loginUrl = "/login";
		String usernameKey = "username";
		
		String path = ((HttpServletRequest) request).getServletPath();
		boolean login = false;
		if (StringUtils.contains(path, loginUrl)) {
			login = true;
		}
		HttpSession session = ((HttpServletRequest) request).getSession();
		String name = (String) session.getAttribute(usernameKey);
		if (name == null&&!login) {
			if (((HttpServletRequest) request).getHeader("x-requested-with") != null
					&& ((HttpServletRequest) request).getHeader(
							"x-requested-with").equalsIgnoreCase( // ajax超时处理
							"XMLHttpRequest")) {
				((HttpServletResponse) response).addHeader("sessionstatus",
						"timeout");
			} else {// http超时的处理
				((HttpServletRequest) request).getRequestDispatcher(
						"/login.html").forward(request, response);
			}
		} else {
			chain.doFilter(request, response);
		}
	}

	@Override
	public void destroy() {

	}

}
