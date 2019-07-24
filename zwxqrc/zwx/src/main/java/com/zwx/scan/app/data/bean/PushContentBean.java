package com.zwx.scan.app.data.bean;

import java.io.Serializable;
import java.util.List;

/**
 * author : lizhilong
 * time   : 2019/03/27
 * desc   : 推送内容
 * version: 1.0
 **/
public class PushContentBean implements Serializable {

    private List<CompMemberType> data;

    public List<CompMemberType> getData() {
        return data;
    }

    public void setData(List<CompMemberType> data) {
        this.data = data;
    }


}
