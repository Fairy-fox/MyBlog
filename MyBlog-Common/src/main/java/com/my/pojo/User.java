package com.my.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;
import lombok.experimental.Accessors;

@TableName("user")
@Data
@Accessors(chain = true)
public class User extends BasePojo{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4686608465810524190L;
	@TableId(type = IdType.AUTO)
	private Long userId;
	private String name;
	private String password;
	private String location;
	private Long kissed;
	private String email;
	private Character gender;
	private String signature;
	private String picture;
}
