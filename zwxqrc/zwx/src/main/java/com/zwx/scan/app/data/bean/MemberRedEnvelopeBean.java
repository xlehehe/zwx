package com.zwx.scan.app.data.bean;

import java.io.Serializable;
import java.util.List;

/**
 * author : lizhilong
 * time   : 2019/04/26
 * desc   :  红包查询列表分页数据
 * version: 1.0
 **/
public class MemberRedEnvelopeBean implements Serializable {
    private List<MemberRedEnvelope> data;

    public List<MemberRedEnvelope> getData() {
        return data;
    }

    public void setData(List<MemberRedEnvelope> data) {
        this.data = data;
    }
}
