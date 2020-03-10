package com.my.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.my.pojo.CommentLike;

@Mapper
public interface CommentLikeMapper extends BaseMapper<CommentLike>{

	void addComment(Long articleId);

	List<Long> findMyLikesByArtIdAndUId(Long articleId, Long userId);


}
