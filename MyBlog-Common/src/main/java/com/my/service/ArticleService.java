package com.my.service;

import java.util.List;
import java.util.Map;

import com.my.pojo.Article;

public interface ArticleService {

	List<Article> findAllPages(Integer pageNum, Integer colNum);


	void uploadArt(Article article, Long userId);


	Article getArticleInfoById(Long articleId);


	Map<String, Integer> findTotalCount(Integer columnNum);


	List<Article> findMyArticleByUserId(Integer pageNum, Long userId);
	
}
