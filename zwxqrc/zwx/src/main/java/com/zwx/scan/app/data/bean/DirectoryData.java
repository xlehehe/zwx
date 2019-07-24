package com.zwx.scan.app.data.bean;

import java.io.Serializable;

/**
 * 字典数据项目实体类
 * @author HanJian
 * @date 2018年8月24日
 */
public class DirectoryData implements Serializable {

    
    /**
     * ID
     */
    private Long id;
    
    /**
     * 字典数据项目键
     */
    private String key;
    
    /**
     * 字典数据项目值
     */
    private String value;
    
    /**
     * 字典分类
     */
//    private DirectoryCategory cat;
    
    /**
     * 本分类排序号
     */
    private Integer seq;
    
    /**
     * 可用状态（1可用，0禁用）
     */
    private Integer status;
    

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }


    public Integer getSeq() {
        return seq;
    }

    public void setSeq(Integer seq) {
        this.seq = seq;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

}
