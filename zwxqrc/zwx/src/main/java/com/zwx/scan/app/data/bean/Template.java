package com.zwx.scan.app.data.bean;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 
 * @author 史俊杰
 * @Title: Template.java
 * @Description:模板实体类
 * @date:2019年4月11日 下午4:12:19
 */
public class Template implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
     * 模板主键
     */
    private Long templateId;
    
    /**
     * 模板类别(1:老虎机;2:砸金蛋;3.集赞)
     */
    private Integer templateSort;
    
    /**
     * 一级分类
     */
    private Long firstCategory;
    
    
    /**
     * 二级分类
     */
    private Long secondCategory;
    
    
    /**
     * 名称
     */
    private String name;
    
    
    /**
     * 创建人
     */
    private Long createUser;
    
    
    /**
     * 创建时间
     */
//    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private String createTime;
    
    
  //  @ApiModelProperty(value="模板图片集合",name="templateChilds")
    List<TemplateChild> templateChilds;


	public Long getTemplateId() {
		return templateId;
	}


	public void setTemplateId(Long templateId) {
		this.templateId = templateId;
	}


	public Integer getTemplateSort() {
		return templateSort;
	}


	public void setTemplateSort(Integer templateSort) {
		this.templateSort = templateSort;
	}


	public Long getFirstCategory() {
		return firstCategory;
	}


	public void setFirstCategory(Long firstCategory) {
		this.firstCategory = firstCategory;
	}


	public Long getSecondCategory() {
		return secondCategory;
	}


	public void setSecondCategory(Long secondCategory) {
		this.secondCategory = secondCategory;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public Long getCreateUser() {
		return createUser;
	}


	public void setCreateUser(Long createUser) {
		this.createUser = createUser;
	}


	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public List<TemplateChild> getTemplateChilds() {
		return templateChilds;
	}


	public void setTemplateChilds(List<TemplateChild> templateChilds) {
		this.templateChilds = templateChilds;
	}
    
	
    
    

}
