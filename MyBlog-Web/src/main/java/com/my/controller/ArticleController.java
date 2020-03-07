package com.my.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.dubbo.config.annotation.Reference;
import com.my.pojo.Article;
import com.my.pojo.Comment;
import com.my.pojo.User;
import com.my.service.ArticleService;
import com.my.service.CommentService;
import com.my.util.CookieUtil;
import com.my.util.JedisPoolUtil;
import com.my.util.ObjectMapperUtil;
import com.my.vo.SysResult;

@Controller
@RequestMapping("/article")
public class ArticleController {
	
	@Reference(check = false)
	private ArticleService articleService;
	
	@Autowired
	JedisPoolUtil jedisPoolUtil;
	
	@Reference(check = false)
	private CommentService commentService;
	
	@RequestMapping("/query")
	@ResponseBody
	public SysResult findAllPages(Integer pageNum, Integer colNum) {
		if(pageNum == null) pageNum = 1;
		return SysResult.success(articleService.findAllPages(pageNum, colNum));
	}
	@RequestMapping("/queryTotalNum")
	@ResponseBody
	public SysResult findPageNumb(@RequestParam(required = false) Integer columnNum) {
		 return SysResult.success(articleService.findTotalCount(columnNum));
	}
	
	@RequestMapping("/upLoadArt")
	@ResponseBody
	public SysResult uploadArt(Article article, HttpServletRequest request) {
		System.out.println(article);
		if(article.getColumnId() == null || article.getTitle() == null || article.getContent() == null) {
			return SysResult.failure("缺少参数");
		}
		String cookieValue = CookieUtil.getCookieValue(request, "MY_TICKET");
		String userInfo = jedisPoolUtil.getJedisCluster().get(cookieValue);
		User user = ObjectMapperUtil.toObj(userInfo, User.class);
		articleService.uploadArt(article, user.getUserId());
		return SysResult.success();
	}
	
	@RequestMapping("/detail")
	public String articleDetail(Long articleId, Model model, HttpServletRequest request) {
		Article article = articleService.getArticleInfoById(articleId);
		List<Comment> comments = commentService.getCommentsByArticleId(articleId);
		article.setCommentsNum(comments.size());
		String cookieValue = CookieUtil.getCookieValue(request, "MY_TICKET");
		String userInfo = jedisPoolUtil.getJedisCluster().get(cookieValue);
		User user = ObjectMapperUtil.toObj(userInfo, User.class);
		model.addAttribute("user", user);
		model.addAttribute("article", article);
		model.addAttribute("comments", comments);
		return "article/detail";
	}
	
}
