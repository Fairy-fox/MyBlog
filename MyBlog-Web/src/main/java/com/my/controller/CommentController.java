package com.my.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.dubbo.config.annotation.Reference;
import com.my.pojo.Comment;
import com.my.pojo.User;
import com.my.service.ArticleService;
import com.my.service.CommentService;
import com.my.util.CookieUtil;
import com.my.util.JedisPoolUtil;
import com.my.util.ObjectMapperUtil;
import com.my.vo.SysResult;

@Controller
@RequestMapping("/comment")
public class CommentController {
	
	@Reference(check = false)
	CommentService commentService;

	@Reference(check = false)
	ArticleService articleService;
	
	@Autowired
	JedisPoolUtil jedisPoolUtil;
	
	@PostMapping("/reply")
	public String saveComment(Comment comment, Long articleId, HttpServletRequest request, HttpServletResponse response) {
		String userInfo = CookieUtil.getCookieValue(request, "MY_TICKET");
		System.out.println(userInfo);
		if(userInfo == null || userInfo.equals("")) {
			CookieUtil.deleteCookie(response, "MY_TICKET", "myblog.com", "/");
			return "/user/login";
		}
		User user = ObjectMapperUtil.toObj(jedisPoolUtil.getJedisCluster().get(userInfo), User.class);
		comment.setName(user.getName()).setUserId(user.getUserId()).setArticleId(articleId);
		commentService.saveComment(comment);
		return "redirect:/article/detail?articleId=" + articleId;
	}
	
	@PostMapping("/editComment")
	@ResponseBody
	public SysResult saveEditedComment(String content, Long articleId, Long commentId) {
		int result = commentService.updateComment(content, articleId, commentId);
		if(result != 1) return SysResult.failure("修改失败，可能回复已经不存在了");
		return SysResult.success();
	}
	
	@RequestMapping("/edit")
	public String editComment(Long commentId, Model model) {
		Comment comment = commentService.getCommentByCommentId(commentId);
		model.addAttribute("comment", comment);
		return "comment/edit";
	}
	
	
	@RequestMapping("/delete")
	public String deleteComment(Long commentId, Long articleId) {
		commentService.deleteCommentByCommentId(commentId);
		articleService.decrCommentNum(articleId);
		return "redirect:/article/detail?articleId=" + articleId;
	}
	
	@PostMapping("/changeLike")
	@ResponseBody
	public SysResult changeLike(Boolean flag, Long commentId, HttpServletRequest request) {
		if(flag == null || commentId == null) return SysResult.failure("缺少参数");
		User user = (User) request.getAttribute("myUser");
		commentService.doUserLike(flag, commentId, user.getUserId());
		return SysResult.success();
	}
	
	@GetMapping("/doSelect")
	@ResponseBody
	public SysResult selectComment(Long commentId) {
		commentService.selectComment(commentId);
		return SysResult.success();
	}
}
