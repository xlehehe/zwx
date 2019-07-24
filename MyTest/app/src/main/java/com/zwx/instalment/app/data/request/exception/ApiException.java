package com.zwx.instalment.app.data.request.exception;

import com.zwx.instalment.app.data.bean.BaseResponse;

/** 
 * 
 * @author  lizhilong
 * @date   2018/3/20
 * @version 1.0
 */ 
public class ApiException extends Exception {
  private int code;
  private String message;

  public ApiException(Throwable throwable, int code) {
    super(throwable);
    this.code = code;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  @Override
  public String getMessage() {
    return message;
  }

  public int getCode() {
    return code;
  }

  public BaseResponse baseResult;

  public ApiException(String detailMessage) {
    super(detailMessage);
    setMessage(detailMessage);
  }

  public ApiException(BaseResponse detailMessage) {
    super(detailMessage.getMessage());
//      super(detailMessage.getMessage()+detailMessage.getMsg());
    this.baseResult = detailMessage;
  }
}
