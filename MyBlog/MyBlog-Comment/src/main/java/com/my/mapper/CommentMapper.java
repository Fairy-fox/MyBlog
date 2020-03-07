package com.my.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.my.pojo.Comment;

public interface CommentMapper extends BaseMapper<Comment>{

	List<Comment> findCommentsByArticleId(Long articleId);

	List<Comment> selectMyComments(Long userId);
}
