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
import com.my.pojo.Article;

@Service
public class ArticleServiceImpl implements ArticleService {

	@Autowired
	private ArticleMapper articleMapper;

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
		article.setUserId(userId);
		article.setCommentsNum(0).setCreatedTime(new Date()).setUpdatedTime(article.getCreatedTime());
		article.setElited(false).setStared(false).setToped(false);
		articleMapper.insert(article);
	}

	@Override
	public Article getArticleInfoById(Long articleId) {
		return articleMapper.selectArticleById(articleId);
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
	public List<Article> findMyArticleByUserId(Integer pageNum, Long userId) {
		if(pageNum == null) pageNum = 1;
		IPage<Article> ipage = new Page<>(pageNum, pageConfig.getPageCount());
		QueryWrapper<Article> queryWrapper = new QueryWrapper<>();
		queryWrapper.orderByDesc("updated");
		IPage<Article> resultPage = articleMapper.selectPage(ipage, queryWrapper);
		List<Article> itemList = resultPage.getRecords();
		return itemList;
	}

}
