package com.my.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;
import lombok.experimental.Accessors;

@TableName("article")
@Data
@Accessors(chain = true)
public class Article extends BasePojo{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6572010895278824199L;
	@TableId(type = IdType.AUTO)
	private Long articleId;
	private Long userId;
	private String content;
	private Integer commentsNum;
	private Boolean stared;
	private Boolean toped;
	private Boolean elited;
	private Integer columnId;
	private String title;
	private Integer wanted;
	private Integer needed;
	@TableField(exist = false)
	private String name;
	@TableField(exist = false)
	private String columnName;
	@TableField(exist = false)
	private Long total;
	private Long viewed;
}
