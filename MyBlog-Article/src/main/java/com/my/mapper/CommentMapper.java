package com.my.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.my.pojo.Comment;

@Mapper
public interface CommentMapper {

	List<Comment> findCommentsByArticleId(Long articleId);
}
