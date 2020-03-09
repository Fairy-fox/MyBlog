package com.my.pojo;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@TableName("article_collection")
@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class Collection implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 2017308099844153775L;
	@TableId(type = IdType.AUTO)
	Long collectionId;
	Long userId;
	Long articleId;
	Date createdTime;
	String title;
}
