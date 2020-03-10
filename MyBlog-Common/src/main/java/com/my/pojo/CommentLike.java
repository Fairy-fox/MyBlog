package com.my.pojo;


import java.util.Date;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;
import lombok.experimental.Accessors;

@TableName("comment_like")
@Data
@Accessors(chain = true)
public class CommentLike {

	@TableId(type = IdType.AUTO)
	private Long likeId;
	private Long userId;
	private Long CommentId;
	private Date createdTime;
	private Long articleId;
}
