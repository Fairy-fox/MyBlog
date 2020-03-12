package com.my.pojo;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;
import lombok.experimental.Accessors;

@TableName("message")
@Data
@Accessors(chain = true)
public class Message implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4208918814361647358L;
	@TableId(type = IdType.AUTO)
	private Long messageId;
	private Long toUserId;
	private Long fromUserId;
	private String content;
	private Long articleId;
	private Long commentId;
	private Date createdTime;
	private Integer viewed;
	@TableField(exist = false)
	private String fromUserName;
	@TableField(exist = false)
	private String articleName;
}
