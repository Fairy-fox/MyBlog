package com.my.service;

import java.util.Date;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.my.mapper.UserMapper;
import com.my.pojo.User;
import com.my.util.JedisPoolUtil;
import com.my.vo.SysResult;

import redis.clients.jedis.JedisCluster;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	UserMapper userMapper;

	@Autowired
	JedisPoolUtil jedisPoolUtil;

	@Override
	public User findUserByEmail(String email) {
		QueryWrapper<User> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("email", email);
		User user = userMapper.selectOne(queryWrapper);
		return user;
	}

	@Override
	public User findUserByUsername(String username) {
		QueryWrapper<User> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("name", username);
		User user = userMapper.selectOne(queryWrapper);
		return user;
	}

	@Override
	public SysResult saveUser(User user) {
		System.out.println(user);
		User userDB = userMapper.selectById(user.getUserId());
		System.out.println(userDB);
		if(userDB == null) {
			if(null != findUserByEmail(user.getEmail())) {
				return SysResult.failure("有重名email");
			}
			if(null != findUserByUsername(user.getName())) {
				return SysResult.failure("有重名昵称");
			}
			if(user.getEmail().equals("") || user.getName().equals("") || user.getPassword().equals("")) {
				return SysResult.failure("请填写完毕后注册");
			}
			String md5Password = DigestUtils.md5DigestAsHex(user.getPassword().getBytes());
			user.setPassword(md5Password);
			user.setCreatedTime(new Date());
			user.setUpdatedTime(user.getCreatedTime());
			user.setKissed(0L);
			userMapper.insert(user);
			return SysResult.success();
		} else {
			userMapper.updateById(user);
			return SysResult.success();
		}
	}

	@Override
	public String login(String email, String pass) {
		if(email == null || email.equals("")) return null;
		if(pass == null || pass.equals("")) return null;
		String md5Password = DigestUtils.md5DigestAsHex(pass.getBytes());
		User user = new User();
		user.setPassword(md5Password);
		user.setEmail(email);
		System.out.println(user);
		QueryWrapper<User> queryWrapper = new QueryWrapper<>(user);
		User userDB = userMapper.selectOne(queryWrapper);
		if(null == userDB) {
			return null;
		};
		String key = UUID.randomUUID().toString();
		userDB.setPassword("123456");
		email = userDB.getEmail().charAt(0) + "xxxx@xx.xx";
		userDB.setEmail(email);
		ObjectMapper mapper = new ObjectMapper();
		String userJSON = null;
		try {
			userJSON = mapper.writeValueAsString(userDB);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		JedisCluster cluster = jedisPoolUtil.getJedisCluster();
		cluster.setex(key, 60*60*8, userJSON);
		return key;
	}

	@Override
	public User findUserByUserId(Long userId) {
		QueryWrapper<User> wrapper = new QueryWrapper<>();
		wrapper.eq("user_id", userId);
		return userMapper.selectOne(wrapper);
	}

	@Override
	public void updatePicture(Long userId, String url) {
		User user = new User();
		user.setUserId(userId).setPicture(url);
		userMapper.updateById(user);
	}

}
