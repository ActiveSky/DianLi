package com.example.dianli.Interceptor;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Author JiKuiXie
 * @Date 2023/06/01
 */
@Component
public class MyInterceptor implements HandlerInterceptor {
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

		String email=request.getParameter("email");
		if (email==null || email.trim().equals("")){
			System.out.println("拦截器失效");
			return false;
		}else {
			System.out.println("拦截器生效");
			System.out.println(email);
			String password=request.getParameter("password");
			System.out.println(password);
			String spassword="123457";
			request.setAttribute("spassword",spassword);

			return true;
		}


	}
}
