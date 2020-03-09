package com.my.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.my.config.PaginationConfig;
import com.my.mapper.ArticleMapper;
import com.my.mapper.CollectionMapper;
import com.my.pojo.Article;
import com.my.pojo.Collection;

@Service
public class ArticleServiceImpl implements ArticleService {

	@Autowired
	private ArticleMapper articleMapper;

	@Autowired
	private CollectionMapper collectionMapper;

	@Autowired 
	private PaginationConfig pageConfig;

	@Override
	public List<Article> findAllPages(Integer pageNum, Integer colNum) {
		int pageSize = pageConfig.getPageCount();
		Integer startIndex = (pageNum - 1) * pageSize;
		List<Article> list = articleMapper.findNormalArticleByColumnId(colNum, startIndex, pageSize);
		list.addAll(findTopedArticle());
		return list;
	}

	public List<Article> findTopedArticle() {
		return articleMapper.findTopArticleByColumnId();
	}
	@Override
	public void uploadArt(Article article, Long userId) {
		QueryWrapper<Article> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("article_id", article.getArticleId()).eq("user_id", userId);
		Article origin = articleMapper.selectOne(queryWrapper);
		System.out.println(origin);
		if(origin == null) {
			article.setUserId(userId);
			article.setCommentsNum(0).setCreatedTime(new Date()).setUpdatedTime(article.getCreatedTime());
			article.setElited(false).setStared(false).setToped(false);
			articleMapper.insert(article);
		} else {
			origin.setColumnId(article.getColumnId()).setTitle(article.getTitle()).setContent(article.getContent()).setNeeded(article.getNeeded()).setUpdatedTime(new Date());
			articleMapper.updateById(origin);
		}
	}

	@Override
	public Article getArticleInfoById(Long articleId) {
		Article article = articleMapper.selectArticleById(articleId);
		article.setViewed(article.getViewed()+1);
		articleMapper.updateById(article);
		return article;
	}

	@Override
	public Map<String, Integer> findTotalCount(Integer columnNum) {
		QueryWrapper<Article> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("toped", false);
		if(columnNum != null && columnNum != 0) queryWrapper.eq("column_id", columnNum);
		Map<String, Integer> pageData = new HashMap<>();
		pageData.put("total", articleMapper.selectCount(queryWrapper));
		pageData.put("size", pageConfig.getPageCount());
		return pageData;
	}

	@Override
	public Map<String, Object> findMyArticleByUserId(Integer pageNum, Long userId) {
		if(pageNum == null) pageNum = 1;
		IPage<Article> ipage = new Page<>(pageNum, pageConfig.getPageCount());
		QueryWrapper<Article> queryWrapper = new QueryWrapper<>();
		queryWrapper.orderByDesc("updated_time").eq("user_id", userId);
		IPage<Article> resultPage = articleMapper.selectPage(ipage, queryWrapper);
		List<Article> articleList = resultPage.getRecords();
		int total = (int) resultPage.getTotal();
		Map<String, Object> m = new HashMap<>();
		m.put("articleList", articleList);
		m.put("total", total);
		return m;
	}

	@Override
	public void collectArticle(Long articleId, Long userId) {
		QueryWrapper<Collection> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("article_id", articleId).eq("user_id", userId);
		Collection collect = collectionMapper.selectOne(queryWrapper);
		if(collect == null) {
			Collection collection = new Collection();
			collection.setArticleId(articleId).setUserId(userId).setCreatedTime(new Date()).setTitle(articleMapper.selectById(articleId).getTitle());
			collectionMapper.insert(collection);
		} else {
			collectionMapper.deleteById(collect.getCollectionId());
		}
	}

	@Override
	public Map<String, Object> findMyCollectionByUserId(Integer pageNum, Long userId) {
		if(pageNum == null) pageNum = 1;
		IPage<Collection> ipage = new Page<>(pageNum, pageConfig.getPageCount());
		QueryWrapper<Collection> queryWrapper = new QueryWrapper<>();
		queryWrapper.orderByDesc("created_time");
		IPage<Collection> resultPage = collectionMapper.selectPage(ipage, queryWrapper);
		List<Collection> articleList = resultPage.getRecords();
		int total = (int) resultPage.getTotal();
		Map<String, Object> m = new HashMap<>();
		m.put("articleList", articleList);
		m.put("total", total);

		return m;
	}

	@Override
	public Article findArticleByArtId(Long articleId, Long userId) {
		QueryWrapper<Article> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("article_id", articleId).eq("user_id", userId);
		return articleMapper.selectOne(queryWrapper);
	}

	@Override
	public Boolean collectCheck(Long articleId, Long userId) {
		QueryWrapper<Collection> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("article_id", articleId).eq("user_id", userId);
		Collection collect = collectionMapper.selectOne(queryWrapper);
		return collect==null ? false : true;
	}

}
