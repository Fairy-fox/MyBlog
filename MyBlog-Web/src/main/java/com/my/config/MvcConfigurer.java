package com.my.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.my.myInterceptor.UserInterceptor;

@Configuration
public class MvcConfigurer implements WebMvcConfigurer {

	@Autowired
	private UserInterceptor userInterceptor;
	
	@Override
	public void configurePathMatch(PathMatchConfigurer configurer) {
		configurer.setUseSuffixPatternMatch(true);
	}
	
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		List<String> list = new ArrayList<>();
		list.add("/article/add");
		list.add("/article/detail");
		list.add("/user/index");
		registry.addInterceptor(userInterceptor).addPathPatterns(list);
	}
	
	
}
