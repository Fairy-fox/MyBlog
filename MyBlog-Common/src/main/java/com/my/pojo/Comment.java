package com.my.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@TableName("comment")
@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class Comment extends BasePojo{

	/**
	 * 
	 */
	private static final long serialVersionUID = -7458818338102951596L;
	@TableId(type = IdType.AUTO)
	private Long commentId;
	private Long articleId;
	private String content;
	private Long userId;
	@TableField(exist = false)
	private String name;
	private Integer agreed;
	private Boolean selected;
	@TableField(exist = false)
	private String title;
}
