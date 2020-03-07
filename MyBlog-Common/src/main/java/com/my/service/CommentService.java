package com.my.service;

import java.util.List;

import com.my.pojo.Article;
import com.my.pojo.Comment;

public interface CommentService {

	List<Comment> getCommentsByArticleId(Long articleId);

	void saveComment(Comment comment);

	Comment getCommentByCommentId(Long commentId);

	void deleteCommentByCommentId(Long commentId);

	int updateComment(String content, Long articleId, Long commentId);

	List<Comment> findMyCommentByUserId(Long userId);

}
