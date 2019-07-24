package com.zwx.scan.app.data.bean;

import java.io.Serializable;

/**
 * 角色实体类
 * @author HanJian
 */
public class Role implements Serializable {

	private static final long serialVersionUID = 5249180836336107133L;
	
	/**
	 * ID
	 */
	private Long id;
	
	/**
	 * 角色名称
	 */
	private String name;
	
	/**
	 * 排序号
	 */
	private Integer seq;
	
	/**
	 * 角色描述
	 */
	private String description;
	
	/**
	 * 角色状态 - 1：激活，0：禁用
	 */
	private Integer status;
	
	
	/**
	 * 角色类型 - 1系统，2餐企，3经销商，4账务，5客服
	 */
	private Integer type;

	private boolean isChecked;





    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

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

    public Integer getSeq() {
        return seq;
    }

    public void setSeq(Integer seq) {
        this.seq = seq;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }


}
