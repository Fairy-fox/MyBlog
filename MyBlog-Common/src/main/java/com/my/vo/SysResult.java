package com.my.vo;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class SysResult implements Serializable{

	private static final long serialVersionUID = 5233707927992965815L;
	private Integer status;//200表示成功，201表示失败
	private String msg;
	private Object data;

	public static SysResult success() {
		return new SysResult(200, "调用成功", null);
	}

	public static SysResult success(Object object) {
		return new SysResult(200, "调用成功", object);
	}

	public static SysResult success(String message, Object object) {
		return new SysResult(200, message, object);
	}

	public static SysResult failure() {
		return new SysResult(201, "调用失败", null);
	}

	public static SysResult failure(String msg) {
		return new SysResult(201, msg, null);
	}

	public static SysResult failure(String message, Object object) {
		return new SysResult(201, message, object);
	}
}

