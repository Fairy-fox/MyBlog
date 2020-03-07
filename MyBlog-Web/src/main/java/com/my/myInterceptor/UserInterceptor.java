package com.my.myInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;

import com.my.pojo.User;
import com.my.util.CookieUtil;
import com.my.util.JedisPoolUtil;
import com.my.util.ObjectMapperUtil;

@Component
public class UserInterceptor implements HandlerInterceptor{

	@Autowired
	JedisPoolUtil jedisPoolUtil;
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		 String ticket = CookieUtil.getCookieValue(request, "MY_TICKET");
		 if(!StringUtils.isEmpty(ticket)) {
	         String userJSON = jedisPoolUtil.getJedisCluster().get(ticket);
	         if(!StringUtils.isEmpty(userJSON)) {
	        	request.setAttribute("myUser", ObjectMapperUtil.toObj(userJSON, User.class));
	            return true; //请求放行
	         }else {
	            CookieUtil.deleteCookie(response,"MY_TICKET","my.com", "/");
	         }
	      }
	      response.sendRedirect("/user/login.html");
	      return false;
	}
}
