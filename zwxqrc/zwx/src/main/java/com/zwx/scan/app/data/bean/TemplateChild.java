package com.zwx.scan.app.data.bean;

import java.io.Serializable;

/**
 * 模板子表
 * @author 史俊杰
 * @Title: TemplateChild.java
 * @Description: 模板子表实体类
 * @date:2019年4月11日 下午4:45:27
 */
public class TemplateChild implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	* 主键
	*/
	private Long id;
	
	/**
	* 模板id
	*/
	private Long parentId;
	
	/**
	* 图片类型
	*/
	private Integer imgType;
	
	/**
	* 图片地址
	*/
	private String imgUrl;
	
	/**
	* 图片name
	*/
	private String name;

	private Boolean isChecked;


	public Boolean getChecked() {
		return isChecked;
	}

	public void setChecked(Boolean checked) {
		isChecked = checked;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getParentId() {
		return parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

	public Integer getImgType() {
		return imgType;
	}

	public void setImgType(Integer imgType) {
		this.imgType = imgType;
	}

	public String getImgUrl() {
		return imgUrl;
	}

	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	

}
