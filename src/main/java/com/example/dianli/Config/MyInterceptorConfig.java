package com.example.dianli.Config;

import com.example.dianli.Interceptor.MyInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @Author JiKuiXie
 * @Date 2023/06/01
 */

//@Configuration
public class MyInterceptorConfig implements WebMvcConfigurer {

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(new MyInterceptor())
				.addPathPatterns("/customer/**")
				.excludePathPatterns("/customer/login");

	}
}
