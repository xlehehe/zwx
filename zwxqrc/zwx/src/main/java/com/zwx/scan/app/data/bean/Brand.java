package com.zwx.scan.app.data.bean;

import java.io.Serializable;

/**
 * 餐饮品牌实体类
 * @author HanJian
 * @date 2018年8月24日
 */
public class Brand implements Serializable {

    
    /**
     * ID
     */
    private Long id;
    
    /**
     * 品牌名称
     */
    private String name;

    private String logo;
    private String story;

    private Integer status;
    private Long defaultMemberType;

    private Company company;
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


    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }


    public String getStory() {
        return story;
    }

    public void setStory(String story) {
        this.story = story;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Long getDefaultMemberType() {
        return defaultMemberType;
    }

    public void setDefaultMemberType(Long defaultMemberType) {
        this.defaultMemberType = defaultMemberType;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }
}
