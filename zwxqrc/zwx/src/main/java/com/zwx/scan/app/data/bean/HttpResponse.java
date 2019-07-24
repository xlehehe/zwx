package com.zwx.scan.app.data.bean;

/**
 * Created by anylife.zlb@gmail.com on 2016/7/11.
 */
public class HttpResponse<T> {
	private int code;
	private String message;
	private T result;
//	private String result;

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

//	public String getResult() {
//		return result;
//	}
//
//	public void setResult(String result) {
//		this.result = result;
//	}

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

	/*public class T{
		String result;
	}*/

}
