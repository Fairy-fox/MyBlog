package com.my.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.dubbo.config.annotation.Service;
import com.my.mapper.ArticleMapper;
import com.my.mapper.CommentLikeMapper;
import com.my.mapper.CommentMapper;
import com.my.pojo.Comment;

@Service
public class CommentServiceImpl implements CommentService {

	@Autowired
	CommentMapper commentMapper;
	
	@Autowired
	ArticleMapper articleMapper;
	
	@Autowired
	CommentLikeMapper clMapper;
	
	@Override
	public List<Comment> getCommentsByArticleId(Long articleId) {
		return commentMapper.findCommentsByArticleId(articleId);
	}

	@Override
	@Transactional
	public void saveComment(Comment comment) {
		comment.setAgreed(0).setSelected(false).setCreatedTime(new Date()).setUpdatedTime(comment.getCreatedTime());
		commentMapper.insert(comment);
		articleMapper.addComment(comment.getArticleId());
	}

	@Override
	public Comment getCommentByCommentId(Long commentId) {
		return commentMapper.selectById(commentId);
	}

	@Override
	public void deleteCommentByCommentId(Long commentId) {
		commentMapper.deleteById(commentId);
	}

	@Override
	public int updateComment(String content, Long articleId, Long commentId) {
		Comment comment = new Comment();
		comment.setContent(content).setArticleId(articleId).setCommentId(commentId).setUpdatedTime(new Date());
		return commentMapper.updateById(comment);
	}

	@Override
	public List<Comment> findMyCommentByUserId(Long userId) {
		return commentMapper.selectMyComments(userId);
	}

	@Override
	public List<Long> findMyLikes(Long articleId, Long userId) {
		return clMapper.findMyLikesByArtIdAndUId(articleId, userId);
	}

	
}
