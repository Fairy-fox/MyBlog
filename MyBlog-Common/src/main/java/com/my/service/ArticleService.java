package com.my.service;

import java.util.List;
import java.util.Map;

import com.my.pojo.Article;

public interface ArticleService {

	List<Article> findAllPages(Integer pageNum, Integer colNum);


	void uploadArt(Article article, Long userId);


	Article getArticleInfoById(Long articleId);


	Map<String, Integer> findTotalCount(Integer columnNum);


	Map<String, Object> findMyArticleByUserId(Integer pageNum, Long userId);


	void collectArticle(Long articleId, Long userId);


	Map<String, Object> findMyCollectionByUserId(Integer pageNum, Long userId);


	Article findArticleByArtId(Long articleId, Long userId);


	Boolean collectCheck(Long articleId, Long userId);


	List<Article> findWeekArts();


	void decrCommentNum(Long articleId);
	
}
