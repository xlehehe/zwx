package com.zwx.instalment.app.data.bean;

/**
 * Created by lizhilong on 2018/7/11.
 */
public class BaseResponse<T> {
	private int code;
	private String message;
	private T result;

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}


	public T getResult() {
		return result;
	}

	public void setResult(T result) {
		this.result = result;
	}

	@Override
	public String toString() {
		return "httpResponse{" +
				"code=" + code +
				", message='" + message + '\'' +
				", result=" + result +
				'}';
	}


}
