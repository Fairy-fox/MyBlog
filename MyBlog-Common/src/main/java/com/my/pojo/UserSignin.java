package com.my.pojo;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;
import lombok.experimental.Accessors;

@TableName("user_signin")
@Data
@Accessors(chain = true)
public class UserSignin implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6648108049385940019L;
	@TableId(type = IdType.AUTO)
	private Long signId;
	private Long userId;
	private Date latestTime;
	private Integer continueSign;
}
