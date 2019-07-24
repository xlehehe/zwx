package com.zwx.scan.app.feature.modulemore;

import com.zwx.scan.app.data.bean.CateBean;
import com.zwx.scan.app.data.bean.CommonImageBean;
import com.zwx.scan.app.feature.member.CommonConstantBean;

import java.io.Serializable;
import java.util.List;

/**
 * author : lizhilong
 * time   : 2019/05/24
 * desc   :  模块功能
 * version: 1.0
 **/
public class ModuleBean implements Serializable {

    private int propertyType;//属性类型 0、1、2：未开始、进行中、已完成
    private String id;
    private String name;

    //模块名称
    public List<CommonImageBean> data;

    public int getPropertyType() {
        return propertyType;
    }

    public void setPropertyType(int propertyType) {
        this.propertyType = propertyType;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<CommonImageBean> getData() {
        return data;
    }

    public void setData(List<CommonImageBean> data) {
        this.data = data;
    }
}
