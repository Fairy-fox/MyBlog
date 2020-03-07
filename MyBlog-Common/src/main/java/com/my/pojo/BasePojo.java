package com.my.pojo;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class BasePojo implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 339172506760837478L;
	private Date createdTime;
	private Date updatedTime;
}
