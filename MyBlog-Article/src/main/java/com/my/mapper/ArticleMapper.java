package com.my.mapper;

import java.util.List;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.my.pojo.Article;

public interface ArticleMapper extends BaseMapper<Article>{

	List<Article> findNormalArticleByColumnId(Integer colNum, Integer start, Integer end);

	List<Article> findTopArticleByColumnId();

	Article selectArticleById(Long articleId);
}
