package com.example.dianli.common;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


@Data
@ApiModel(value = "统一返回类")
public class R {

	@ApiModelProperty(value = "返回码")
	private int code;

	@ApiModelProperty(value = "返回信息")
	private String message;

	@ApiModelProperty(value = "返回类型")
	private String type;

	@ApiModelProperty(value = "是否成功")
	private Boolean success;

	@ApiModelProperty(value = "返回数据")
	private Object data;

	public static R success(String message) {
		R r = new R();
		r.setCode(200);
		r.setMessage(message);
		r.setSuccess(true);
		r.setType("success");
		r.setData(null);
		return r;
	}

	public static R success(String message, Object data) {
		R r = success(message);
		r.setData(data);
		return r;
	}


	public static R error(String message) {
		R r = success(message);
		r.setSuccess(false);
		r.setType("error");
		return r;
	}

	public static R warning(String message) {
		R r = error(message);
		r.setType("warning");
		return r;
	}

	public static R fatal(String message) {
		R r = error(message);
		r.setCode(500);
		return r;
	}
}
