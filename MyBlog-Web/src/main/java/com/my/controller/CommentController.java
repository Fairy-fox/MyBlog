package com.my.controller;

import java.util.List;

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
import com.my.pojo.Message;
import com.my.pojo.User;
import com.my.service.ArticleService;
import com.my.service.CommentService;
import com.my.service.UserService;
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
	
	@Reference(check = false)
	UserService userService;
	
	@Autowired
	JedisPoolUtil jedisPoolUtil;
	
	@PostMapping("/reply")
	@ResponseBody
	public SysResult saveComment(Comment comment, Long articleId, String title, Long toUserId, HttpServletRequest request, HttpServletResponse response) {
		String userInfo = CookieUtil.getCookieValue(request, "MY_TICKET");
		System.out.println(userInfo);
		if(userInfo == null || userInfo.equals("")) {
			CookieUtil.deleteCookie(response, "MY_TICKET", "myblog.com", "/");
			return SysResult.failure();
		}
		User user = ObjectMapperUtil.toObj(jedisPoolUtil.getJedisCluster().get(userInfo), User.class);
		comment.setName(user.getName()).setUserId(user.getUserId()).setArticleId(articleId);
		commentService.saveComment(comment, title, toUserId);
		return SysResult.success();
	}
	
	@PostMapping("/replyToUser")
	@ResponseBody
	public SysResult saveCommentToUser(Comment comment, Long articleId, String title, Long toUserId, HttpServletRequest request, HttpServletResponse response) {
		String userInfo = CookieUtil.getCookieValue(request, "MY_TICKET");
		System.out.println(userInfo);
		if(userInfo == null || userInfo.equals("")) {
			CookieUtil.deleteCookie(response, "MY_TICKET", "myblog.com", "/");
			return SysResult.failure();
		}
		User user = ObjectMapperUtil.toObj(jedisPoolUtil.getJedisCluster().get(userInfo), User.class);
		comment.setName(user.getName()).setUserId(user.getUserId()).setArticleId(articleId);
		commentService.saveComment(comment, title, toUserId);
		return SysResult.success();
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
	
	@GetMapping("/querySignin")
	@ResponseBody
	public SysResult querySignin(HttpServletRequest request) {
		User user = (User)request.getAttribute("myUser");
		return SysResult.success(commentService.findSignin(user.getUserId()));
	}
	
	@GetMapping("/doSignin")
	@ResponseBody
	public SysResult doSignin(HttpServletRequest request) {
		User user = (User)request.getAttribute("myUser");
		int times = commentService.doSigninByUserId(user.getUserId());
		userService.signin(user.getUserId(), times);
		return SysResult.success(times);
	}
	
	@GetMapping("/queryMsg")
	@ResponseBody
	public SysResult queryMessage(HttpServletRequest request) {
		User user = (User)request.getAttribute("myUser");
		List<Message> msgs = commentService.getMessagesByUserId(user.getUserId());
		return SysResult.success(msgs);
	}
	
	@GetMapping("/deleteMsg")
	@ResponseBody
	public SysResult deleteMessage(HttpServletRequest request, Long messageId) {
		User user = (User)request.getAttribute("myUser");
		commentService.deleteMsg(user.getUserId(), messageId);
		return SysResult.success();
	}
	
	@GetMapping("/queryMsgExist")
	@ResponseBody
	public SysResult queryMessage(Long userId) {
		if(userId == null) {
			return SysResult.failure();
		}
		return SysResult.success(commentService.queryMessageByUserId(userId));
	}
}
