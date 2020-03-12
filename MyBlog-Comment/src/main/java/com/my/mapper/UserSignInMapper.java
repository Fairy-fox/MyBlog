package com.my.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.my.pojo.UserSignin;

public interface UserSignInMapper extends BaseMapper<UserSignin>{

	void signin(Long userId);

}
