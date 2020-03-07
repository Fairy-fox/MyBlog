package com.my.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import lombok.Data;

@Configuration
@PropertySource("classpath:/properties/Pagination.properties")
@Data
public class PaginationConfig {

	@Value("${pagination.pagecount}")
	private Integer pageCount;
}
