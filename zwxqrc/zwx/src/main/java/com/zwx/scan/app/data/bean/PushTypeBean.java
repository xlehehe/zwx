package com.zwx.scan.app.data.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * author : lizhilong
 * time   : 2019/03/27
 * desc   :  推送类型实体类
 * version: 1.0
 **/
public class PushTypeBean implements Serializable {

    private ArrayList<DirectoryData> data;


    public ArrayList<DirectoryData> getData() {
        return data;
    }

    public void setData(ArrayList<DirectoryData> data) {
        this.data = data;
    }
}
