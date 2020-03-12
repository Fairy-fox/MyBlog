package com.my.service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.my.mapper.ArticleMapper;
import com.my.mapper.CommentLikeMapper;
import com.my.mapper.CommentMapper;
import com.my.mapper.MessageMapper;
import com.my.mapper.UserSignInMapper;
import com.my.pojo.Comment;
import com.my.pojo.CommentLike;
import com.my.pojo.Message;
import com.my.pojo.UserSignin;

@Service
public class CommentServiceImpl implements CommentService {

	@Autowired
	CommentMapper commentMapper;

	@Autowired
	ArticleMapper articleMapper;

	@Autowired
	UserSignInMapper signMapper;
	
	@Autowired
	CommentLikeMapper clMapper;

	@Autowired
	MessageMapper messageMapper;
	
	@Override
	public List<Comment> getCommentsByArticleId(Long articleId) {
		return commentMapper.findCommentsByArticleId(articleId);
	}

	@Override
	@Transactional
	public Long saveComment(Comment comment, String title, Long toUserId) {
		comment.setAgreed(0).setSelected(false).setCreatedTime(new Date()).setUpdatedTime(comment.getCreatedTime());
		commentMapper.insert(comment);
		articleMapper.addComment(comment.getArticleId());
		Long commentId = comment.getCommentId();
		Message msg = new Message();
		msg.setArticleId(comment.getArticleId()).setArticleName(title).setCommentId(comment.getCommentId()).setContent(comment.getContent()).setCreatedTime(new Date());
		msg.setFromUserId(comment.getUserId()).setToUserId(toUserId).setFromUserName(comment.getName()).setViewed(0);
		messageMapper.insert(msg);
		return commentId;
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

	@Override
	public void doUserLike(Boolean flag, Long commentId, Long userId) {
		Comment comment = commentMapper.selectById(commentId);
		if(flag) {
			CommentLike cl = new CommentLike();
			cl.setArticleId(comment.getArticleId()).setCommentId(commentId).setUserId(userId).setCreatedTime(new Date());
			clMapper.insert(cl);
			Comment commentUpdate = new Comment();
			commentUpdate.setCommentId(commentId).setAgreed(comment.getAgreed() + 1);
			commentMapper.updateById(commentUpdate);
		} else {
			QueryWrapper<CommentLike> wrapper = new QueryWrapper<>();
			wrapper.eq("comment_id", commentId).eq("user_id", userId);
			clMapper.delete(wrapper);
			Comment commentUpdate = new Comment();
			commentUpdate.setCommentId(commentId).setAgreed(comment.getAgreed() - 1);
			commentMapper.updateById(commentUpdate);
		}
	}

	@Override
	public void selectComment(Long commentId) {
		Comment comment = new Comment();
		comment.setCommentId(commentId).setSelected(true);
		commentMapper.updateById(comment);
	}

	@Override
	public Integer findSignin(Long userId) {
		QueryWrapper<UserSignin> wrapper = new QueryWrapper<>();
		wrapper.eq("user_id", userId);
		UserSignin record = signMapper.selectOne(wrapper);
		if(record != null && new SimpleDateFormat("yyyy-MM-dd").format(new Date()).equals(new SimpleDateFormat("yyyy-MM-dd").format(record.getLatestTime()))) {
			return -1;
		}
		if(record == null) {
			return 0;
		}
		return record.getContinueSign();
	}

	@Override
	public Integer doSigninByUserId(Long userId) {
		UserSignin record = signMapper.selectById(userId);
		if(record == null) {
			UserSignin entity = new UserSignin();
			entity.setContinueSign(0).setLatestTime(new Date()).setUserId(userId);
			signMapper.insert(entity);
		}
		Integer times = findSignin(userId);
		int kissed = 0;
		if(times < 5) {
			kissed = 5;
		} else if(times < 15) {
			kissed = 10;
		} else if(times < 30) {
			kissed = 15;
		} else {
			kissed = 30;
		};
		if(times == -1) {
			return 0;
		}
		signMapper.signin(userId);
		return kissed;
	}

	@Override
	public List<Message> getMessagesByUserId(Long userId) {
		return messageMapper.getMessagesByUserId(userId);
	}

	@Override
	public void deleteMsg(Long userId, Long messageId) {
		if(messageId==null) {
			QueryWrapper<Message> wrapper = new QueryWrapper<>();
			wrapper.eq("to_user_id", userId);
			messageMapper.delete(wrapper);
		} else {
			QueryWrapper<Message> wrapper = new QueryWrapper<>();
			wrapper.eq("to_user_id", userId).eq("message_id", messageId);
			messageMapper.delete(wrapper);
		}
	}

	@Override
	public Integer queryMessageByUserId(Long userId) {
		QueryWrapper<Message> wrapper = new QueryWrapper<>();
		wrapper.eq("to_user_id", userId);
		return messageMapper.selectCount(wrapper);
	}
}
