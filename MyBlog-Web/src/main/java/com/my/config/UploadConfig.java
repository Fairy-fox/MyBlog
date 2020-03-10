package com.my.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

@Configuration
public class UploadConfig {

	@Bean("multipartResolver")
	public CommonsMultipartResolver getCommonsMultipartResolver() {
		return new CommonsMultipartResolver();
	}
}