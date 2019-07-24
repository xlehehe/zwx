package com.zwx.scan.app.data.bean;

import java.io.Serializable;

/**
 * 字典分类实体类
 * @author HanJian
 * @date 2018年8月24日
 */
public class DirectoryCategory implements Serializable {

    private static final long serialVersionUID = 7982297652859813081L;
    
    /**
     * ID
     */
    private Long id;
    
    /**
     * 分类名称
     */
    private String name;

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

}
