package com.my;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.my.mapper")
public class MyBlogCommentServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(MyBlogCommentServiceApplication.class);
	}
}
