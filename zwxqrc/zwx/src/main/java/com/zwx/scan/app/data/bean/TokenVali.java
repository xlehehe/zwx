package com.zwx.scan.app.data.bean;

import java.io.Serializable;

/**
 * author : lizhilong
 * time   : 2018/10/17
 * desc   :
 * version: 1.0
 **/
public class TokenVali implements Serializable {

    private String id;
    private String name;
    private Long weight;

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

    public Long getWeight() {
        return weight;
    }

    public void setWeight(Long weight) {
        this.weight = weight;
    }
}
