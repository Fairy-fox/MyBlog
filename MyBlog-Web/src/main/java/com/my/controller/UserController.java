package com.my.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.dubbo.config.annotation.Reference;
import com.fasterxml.jackson.databind.util.JSONPObject;
import com.my.pojo.Article;
import com.my.pojo.Comment;
import com.my.pojo.User;
import com.my.service.ArticleService;
import com.my.service.CommentService;
import com.my.service.ImageService;
import com.my.service.UserService;
import com.my.util.CookieUtil;
import com.my.util.JedisPoolUtil;
import com.my.util.ObjectMapperUtil;
import com.my.vo.SysResult;

import io.micrometer.core.instrument.util.StringUtils;
import redis.clients.jedis.JedisCluster;

@Controller
@RequestMapping("/user")
public class UserController {

	@Autowired
	JedisPoolUtil jedisPoolUtil;

	@Reference(check = false)
	private UserService userService;

	@Reference(check = false)
	private ArticleService articleService;

	@Reference(check = false)
	private CommentService commentService;

	@Reference(check = false)
	private ImageService imageService;

	@RequestMapping("queryEmail")
	@ResponseBody
	public SysResult queryEmail(String email) {
		User user = userService.findUserByEmail(email);
		if(user != null) 
			return SysResult.failure();
		return SysResult.success();
	}

	@RequestMapping("queryUsername")
	@ResponseBody
	public SysResult queryUsername(String username) {
		User user = userService.findUserByUsername(username);
		if(user != null) 
			return SysResult.failure();
		return SysResult.success();
	}

	@RequestMapping("saveUser")
	@ResponseBody
	public SysResult saveUser(User user) {
		return userService.saveUser(user);
	}

	@RequestMapping("editUser")
	@ResponseBody
	public SysResult editUser(User user, HttpServletRequest request) {
		User myUser = (User) request.getAttribute("myUser");
		return userService.saveUser(user.setUserId(myUser.getUserId()));
	}

	@RequestMapping("doLogin")
	@ResponseBody
	public SysResult login(String email, String pass, HttpServletResponse response) {
		String ticket = userService.login(email, pass);
		if(ticket == null) {
			return SysResult.failure("账号或密码错误");
		}
		Cookie cookie = new Cookie("MY_TICKET", ticket);
		cookie.setDomain("myblog.com");
		cookie.setPath("/");
		cookie.setMaxAge(60*60*8);
		response.addCookie(cookie);
		return SysResult.success();
	}

	@RequestMapping("loginquery/{ticket}")
	@ResponseBody
	public JSONPObject loginCheck(@PathVariable String ticket, String callback, HttpServletResponse response) {
		JedisCluster jedis = jedisPoolUtil.getJedisCluster();
		String userJson = jedis.get(ticket);
		if(userJson == null) {
			Cookie cookie = new Cookie("MY_TICKET", "");
			cookie.setDomain("myblog.com");
			cookie.setPath("/");
			cookie.setMaxAge(0);
			response.addCookie(cookie);
			return new JSONPObject(callback, SysResult.failure());
		}
		User user = ObjectMapperUtil.toObj(userJson, User.class);
		return new JSONPObject(callback, SysResult.success(userService.findUserByUserId(user.getUserId())));
	}

	@RequestMapping("logout")
	public String logout(HttpServletRequest request, HttpServletResponse response){
		String ticket = CookieUtil.getCookieValue(request, "MY_TICKET");
		if(!StringUtils.isEmpty(ticket)) {
			jedisPoolUtil.getJedisCluster().del(ticket);
			CookieUtil.deleteCookie(response, "MY_TICKET", "myblog.com", "/");
		}
		return "redirect:/";
	}

	@RequestMapping("/home")
	public String userHome(Long userId, HttpServletRequest request, HttpServletResponse response) {
		User user = null;
		if(userId == null) {
			String ticket = CookieUtil.getCookieValue(request, "MY_TICKET");
			String userInfo = jedisPoolUtil.getJedisCluster().get(ticket);
			if(StringUtils.isEmpty(userInfo)) {
				jedisPoolUtil.getJedisCluster().del(ticket);
				CookieUtil.deleteCookie(response, "MY_TICKET", "myblog.com", "/");
				return "redirect:/user/login";
			}
			user = ObjectMapperUtil.toObj(userInfo, User.class);
		} else {
			user = userService.findUserByUserId(userId);
		}
		request.setAttribute("user", user);
		System.out.println(user);
		List<Article> articles = (List<Article>) articleService.findMyArticleByUserId(0, user.getUserId()).get("articleList");
		List<Comment> comments = commentService.findMyCommentByUserId(user.getUserId());
		request.setAttribute("articles", articles);
		request.setAttribute("comments", comments);
		return "user/home";
	}

	@RequestMapping("/queryPostArts")
	@ResponseBody
	public SysResult userArticles(Integer pageNum, HttpServletRequest request) {
		if(pageNum == null) pageNum = 1;
		User user = (User) request.getAttribute("myUser");
		Map<String, Object> m = articleService.findMyArticleByUserId(pageNum, user.getUserId());
		return SysResult.success(m);
	}


	@RequestMapping("/queryCollectArts")
	@ResponseBody
	public SysResult userCollect(Integer pageNum, HttpServletRequest request) {
		if(pageNum == null) pageNum = 1;
		User user = (User) request.getAttribute("myUser");
		Map<String, Object> m = articleService.findMyCollectionByUserId(pageNum, user.getUserId());
		System.out.println(m);
		return SysResult.success(m);
	}

	@RequestMapping("/uploadPic")
	@ResponseBody
	public SysResult uploadUserPin(MultipartFile file, HttpServletRequest request) throws IOException {
		String ticket = CookieUtil.getCookieValue(request, "MY_TICKET");
		if(StringUtils.isEmpty(ticket)) {
			return SysResult.failure("请登录");
		}
		String userJSON = jedisPoolUtil.getJedisCluster().get(ticket);
		User user = ObjectMapperUtil.toObj(userJSON, User.class);
		String fileName = file.getOriginalFilename();
		byte[] imgBytes = file.getBytes();
		String url = imageService.uploadPic(imgBytes, fileName);
		if(url == null) {
			return SysResult.failure("上传失败");
		}
		Map<String, String> m = new HashMap<>();
		m.put("src", url);
		userService.updatePicture(user.getUserId(), url);
		return SysResult.success(m);
	}
	
	@PostMapping("/editPwd")
	@ResponseBody
	public SysResult editPassword(String pwdNow, String pwdNew, HttpServletRequest request) {
		if(pwdNow == null || pwdNew == null || pwdNew.length() > 16 || pwdNew.length() < 6) {
			return SysResult.failure("参数非法");
		} else {
			User user = (User) request.getAttribute("myUser");
			boolean flag = userService.editPwd(user.getUserId(), pwdNow, pwdNew);
			if(flag) return SysResult.success();
			return SysResult.failure("密码不正确");
		}
	}
}
