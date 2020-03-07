package com.my.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexController {

	@RequestMapping("/index")
	public String doIndex() {
		return "index";
	}
	
	@RequestMapping("{index}/{indexName}")
	public String findIndex(@PathVariable String index, @PathVariable String indexName) {
		return index + "/" + indexName;
	}
}
