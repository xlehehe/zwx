package com.zwx.scan.app.data.bean;

import java.io.Serializable;

/**
 * 餐饮公司实体类
 * @author HanJian
 * @date 2018年8月24日
 */
public class Company implements Serializable {

    
    /**
     * ID
     */
    private Long id;
    
    /**
     * 公司名称
     */
    private String name;
    
    /**
     * 可用状态
     */
    private Integer status;

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

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
    
}
