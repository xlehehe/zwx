package com.zwx.scan.app.data.bean;

import java.io.Serializable;
import java.util.List;

/**
 * author : lizhilong
 * time   : 2019/04/26
 * desc   : 红包查询详情分页
 * version: 1.0
 **/
public class MemberRedEnvelopeDetailBean implements Serializable {
    private List<RedEnvelopeDetail> data;

    public List<RedEnvelopeDetail> getData() {
        return data;
    }

    public void setData(List<RedEnvelopeDetail> data) {
        this.data = data;
    }
}
