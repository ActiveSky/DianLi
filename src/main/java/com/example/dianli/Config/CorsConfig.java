package com.example.dianli.Config;

import com.example.dianli.Interceptor.CorsInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @Author JiKuiXie
 * @Date 2023/06/05
 */
//@Configuration
public class CorsConfig implements WebMvcConfigurer {
	@Autowired
	private CorsInterceptor corsInterceptor;

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(corsInterceptor)
				.addPathPatterns("/**");
	}
}
