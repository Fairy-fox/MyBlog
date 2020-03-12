package com.my.service;

import java.util.List;

import com.my.pojo.Article;
import com.my.pojo.Comment;
import com.my.pojo.Message;

public interface CommentService {

	List<Comment> getCommentsByArticleId(Long articleId);

	Long saveComment(Comment comment, String title, Long toUserId);

	Comment getCommentByCommentId(Long commentId);

	void deleteCommentByCommentId(Long commentId);

	int updateComment(String content, Long articleId, Long commentId);

	List<Comment> findMyCommentByUserId(Long userId);

	List<Long> findMyLikes(Long articleId, Long userId);

	void doUserLike(Boolean flag, Long commentId, Long userId);

	void selectComment(Long commentId);

	Integer findSignin(Long userId);

	Integer doSigninByUserId(Long userId);

	List<Message> getMessagesByUserId(Long userId);

	void deleteMsg(Long userId, Long messageId);

	Integer queryMessageByUserId(Long userId);

}
