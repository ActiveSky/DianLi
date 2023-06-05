package com.example.dianli.Config;

import com.example.dianli.Interceptor.JwtInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @Author JiKuiXie
 * @Date 2023/06/01
 */
@Configuration
public class JwtInterceptorConfig implements WebMvcConfigurer {
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(new JwtInterceptor())
				.addPathPatterns("/customer/**")
				.excludePathPatterns("/customer/login")
				.addPathPatterns("/seller/**")
				.excludePathPatterns("/seller/login","/seller/getAllSeller")
				.addPathPatterns("/order/**");
//				.addPathPatterns("/item/**")
//				.excludePathPatterns("/item/getItemList");
	}

}
