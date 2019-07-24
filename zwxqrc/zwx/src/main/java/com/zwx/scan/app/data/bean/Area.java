package com.zwx.scan.app.data.bean;

import java.io.Serializable;

/**
 * 地区实体类 <br/>
 * 省-市-区县
 * @author HanJian
 * @date 2018年8月24日
 */
public class Area implements Serializable {

    private static final long serialVersionUID = -5282436317520628515L;
    
    /**
     * ID
     */
    private Long id;
    
    /**
     * 地区名称
     */
    private String name;
    
    /**
     * 上级地区
     */
    private Area parent;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Area getParent() {
        return parent;
    }

    public void setParent(Area parent) {
        this.parent = parent;
    }

}
