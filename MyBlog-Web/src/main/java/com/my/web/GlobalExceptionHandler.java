package com.my.web;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.my.vo.SysResult;

@RestControllerAdvice
public class GlobalExceptionHandler {
	
	@ExceptionHandler(RuntimeException.class)
	public SysResult doHandleRuntimeException(RuntimeException e) {
		e.printStackTrace();
		return SysResult.failure("业务繁忙");
	}
   
}
