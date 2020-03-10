package com.my.service;

import com.my.pojo.User;
import com.my.vo.SysResult;

public interface UserService {

	User findUserByEmail(String email);

	User findUserByUsername(String username);

	SysResult saveUser(User user);

	String login(String email, String pass);

	User findUserByUserId(Long userId);

	void updatePicture(Long userId, String url);

	boolean editPwd(Long userId, String pwdNow, String pwdNew);

}
