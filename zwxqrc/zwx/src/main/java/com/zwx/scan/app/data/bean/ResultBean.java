package com.zwx.scan.app.data.bean;

/**
 * author : lizhilong
 * time   : 2018/10/17
 * desc   :
 * version: 1.0
 **/
public class ResultBean {

    private String code;
    private Object result;
    private String message;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }


    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
