package com.my.vo;

import java.io.Serializable;
import java.util.List;

import com.my.pojo.Article;
import com.my.pojo.Comment;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class ArticlePage implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2454630573899506364L;
	private Article article;
	private List<Comment> comments;
}
