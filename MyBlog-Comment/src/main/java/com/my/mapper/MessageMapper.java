package com.my.mapper;

import java.util.List;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.my.pojo.Message;

public interface MessageMapper extends BaseMapper<Message>{

	List<Message> getMessagesByUserId(Long userId);

}
