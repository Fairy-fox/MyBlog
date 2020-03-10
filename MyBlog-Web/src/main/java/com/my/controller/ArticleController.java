package com.my.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.dubbo.config.annotation.Reference;
import com.my.pojo.Article;
import com.my.pojo.Comment;
import com.my.pojo.User;
import com.my.service.ArticleService;
import com.my.service.CommentService;
import com.my.service.ImageService;
import com.my.util.CookieUtil;
import com.my.util.JedisPoolUtil;
import com.my.util.ObjectMapperUtil;
import com.my.vo.SysResult;

import io.micrometer.core.instrument.util.StringUtils;

@Controller
@RequestMapping("/article")
public class ArticleController {

	@Reference(check = false)
	private ArticleService articleService;

	@Autowired
	JedisPoolUtil jedisPoolUtil;

	@Reference(check = false)
	private CommentService commentService;

	@Reference(check = false)
	private ImageService imageService;
	
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
		if(article.getColumnId() == null || article.getTitle() == null || article.getContent() == null) {
			return SysResult.failure("缺少参数");
		}
		User user = (User) request.getAttribute("myUser");
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
		List<Long> likes = commentService.findMyLikes(articleId, user.getUserId());
		for (Comment comment : comments) {
			a: for (Long like : likes) {
				if(comment.getCommentId().equals(like)) {
					comment.setLike(true);
					break a;
				}
			}
		}
		model.addAttribute("user", user);
		model.addAttribute("article", article);
		model.addAttribute("comments", comments);
		return "article/detail";
	}

	@RequestMapping("/collect")
	@ResponseBody
	public SysResult collectArticle(Long articleId, HttpServletRequest request) {
		User user = (User) request.getAttribute("myUser");
		articleService.collectArticle(articleId, user.getUserId());
		return SysResult.success();
	}

	@RequestMapping("/edit")
	public String collectionArticle(Long articleId, HttpServletRequest request) {
		User user = (User) request.getAttribute("myUser");
		Article article = articleService.findArticleByArtId(articleId, user.getUserId());
		if (article == null) return "redirect:/other/notice";
		request.setAttribute("article", article);
		request.setAttribute("user", user);
		return "article/edit";
	}

	@RequestMapping("/collectCheck")
	@ResponseBody
	public SysResult collectCheck(Long articleId, HttpServletRequest request) {
		User user = (User) request.getAttribute("myUser");
		if(user == null) return SysResult.success(false);
		return SysResult.success(articleService.collectCheck(articleId, user.getUserId()));
	}
	
	@RequestMapping("/weekHot")
	@ResponseBody
	public SysResult findWeekArts() {
		return SysResult.success(articleService.findWeekArts());
	}
	
	@RequestMapping("/upload")
	@ResponseBody
	public SysResult uploadArtPic(MultipartFile file, HttpServletRequest request) throws IOException {
		String ticket = CookieUtil.getCookieValue(request, "MY_TICKET");
		if(StringUtils.isEmpty(ticket)) {
			return SysResult.failure("请登录");
		}
		String fileName = file.getOriginalFilename();
		byte[] imgBytes = file.getBytes();
		String url = imageService.uploadArtPic(imgBytes, fileName);
		if(url == null) {
			return SysResult.failure("上传失败");
		}
		return SysResult.success(url);
	}
}
