package com.my.mapper;

import java.util.List;

import com.my.pojo.Comment;

public interface CommentMapper {

	List<Comment> findCommentsByArticleId(Long articleId);
}
